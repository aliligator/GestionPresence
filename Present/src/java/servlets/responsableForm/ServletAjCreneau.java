/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.responsableForm;

import bd.HibernateUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceRespSaisir;

/**
 * servlet pour réaliser l'ajout d'un créneau par responsable
 *
 * @author yuxuan
 */
public class ServletAjCreneau extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        String message = "";
        //récuperer les informations de nouveau créneau
        String idaction = request.getParameter("idaction");
        String nomajout = request.getParameter("nomajout");
        String typeAct = request.getParameter("typeAct");
        int grpajout = Integer.parseInt(request.getParameter("groupchoi"));
        String dateajout = request.getParameter("dateajout");
        String hajout = request.getParameter("hajout");
        String durajout = request.getParameter("durajout");
        int idRespon = (int) request.getSession().getAttribute("idenseig");
        try {
            if (idaction.isEmpty()) {
                // pour ajouter un créneau
                //examiner  //exmainerCreneau(Session session,String date, String heure,String fin, int idGrp, int idRespon)
                int res = ServiceRespSaisir.exmainerCreneau(session, dateajout, hajout, durajout, grpajout, idRespon);
                if (res == 0) {
                    ServiceRespSaisir.ajoutCreneau(session, nomajout, typeAct, grpajout, dateajout, hajout, durajout, idRespon);
                    message = "Vos informations ont bien été saisies";
                    request.setAttribute("msg_info", message);
                    request.getRequestDispatcher("resact").forward(request, response);
                } else {
                    message = "Il y a un conflit d'horaire pour le créneau que vous avez ajouté";
                    request.setAttribute("msg_info", message);
                    request.getRequestDispatcher("resact").forward(request, response);
                }

            } else {
                // pour modifier un créneau
                ServiceRespSaisir.upCreneau(session, Integer.parseInt(idaction), nomajout, typeAct, grpajout, dateajout, hajout, durajout);
                message = "Les informations du créneau ont bien été changés";
                request.setAttribute("msg_info", message);
                request.getRequestDispatcher("resact").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("msg_error", ex.getMessage());
        }
        t.commit();
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
