/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import bd.Enseignant;
import bd.Etudiant;
import bd.HibernateUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceLogin;

/**
 *
 * @author yuxuan
 */
public class ServletLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        //récuperer les informations de login 
        String email = request.getParameter("email");
        String mdp = request.getParameter("pass");
        String souvenir = request.getParameter("souvenir");
        String nomMdp = email + "mdp";
        if (souvenir != null) {
            Cookie emailCookie = new Cookie(email, email);
            Cookie mdpCookie = new Cookie(nomMdp, mdp);
            emailCookie.setMaxAge(60 * 60 * 24 * 7);
            mdpCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(emailCookie);
            response.addCookie(mdpCookie);
        }

        String message = null;

        //Vérifier les résultat de test de la correspondence de l'email et mdp
        int resEns = ServiceLogin.existEmailEnseig(session, email, mdp);
        int resEtu = ServiceLogin.existEmailEtudiant(session, email, mdp);
        int resGs = ServiceLogin.existEmailGScolarite(session, email, mdp);
        if (resEns == 0 && resEtu == 0 && resGs == 0) {
            message = "Compte n'existe pas";
        }
        if (resEns == -1 || resEtu == -1 || resGs == -1) {
            message = "Mot de passe incorrect";
        }

        if (message != null) {
            request.setAttribute("msg_info", message);
            request.getRequestDispatcher("cx").forward(request, response);
        }
        /**
         * Email et mdp sont bien correspondent et aller à la page suivante
         * correct (etuidiant/enseignant/responsable/gestionnnaire)
         */
        // login c'est un enseignant
        if (resEtu == 0 && resGs == 0) {
            try {
                // vérifier si un responsable ou pas 
                Enseignant ens = (Enseignant) session.load(Enseignant.class, resEns);
                HttpSession sessionHttp = request.getSession(true);
                sessionHttp.setAttribute("idenseig", resEns);
                sessionHttp.setAttribute("nomuser", ens.getPrenome() + " " + ens.getNome());
                sessionHttp.setAttribute("photo", ens.getPhoto());
                boolean estRespon = ServiceLogin.estResponsable(session, resEns);
                if (estRespon) {
                    request.getRequestDispatcher("vuerespon").forward(request, response);
                } else {
                    request.getRequestDispatcher("vueenseig").forward(request, response);
                }
            } catch (Exception ex) {
                request.setAttribute("msg_error", ex.getMessage());
                ex.printStackTrace();
            } finally {
                t.commit();
            }

        } else // login c'est un etudiant
        {
            if (resGs == 0) {
                try {
                    Etudiant etu = (Etudiant) session.load(Etudiant.class, resEtu);
                    HttpSession sessionHttp = request.getSession(true);
                    sessionHttp.setAttribute("idetu", resEtu);
                    sessionHttp.setAttribute("nomuser", etu.getPrenometu() + " " + etu.getNometu());
                    sessionHttp.setAttribute("photo", etu.getPhotoetu());
                    request.getRequestDispatcher("vueetu").forward(request, response);
                } catch (Exception ex) {
                    request.setAttribute("msg_error", ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    t.commit();
                }

            } else// login c'est un gestionnaire 
            {
                try {
                    request.getRequestDispatcher("vuegs").forward(request, response);
                } catch (Exception ex) {
                    request.setAttribute("msg_error", ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    t.commit();
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
