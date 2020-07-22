/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Inutile  没用了
 */
package servlets.etudiant;

import bd.HibernateUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author yuxuan
 */
public class ServeletSaisirPres extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, ParseException 
    {
      
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction t = session.beginTransaction();
      
      //récupere les valeurs saisis (typeActivite+Date+Heure)
      String actPres = request.getParameter("typeAct");
      String dateStr = request.getParameter("datePres");
      Long heurePres = Long.parseLong(request.getParameter("heure"));
      Long minutePres =Long.parseLong(request.getParameter("minute"));
      int idEtudiant = (int)request.getSession().getAttribute("idetu");
      
      //Saisir dans base de données
      //String typeActivite, String dateSaisir, Long heure, Long minute,int idEtudiant
      String message = "";
      try{
          //SaisirPres.saisirPres(session,actPres, dateStr,heurePres,minutePres, idEtudiant);
          message = "Votre informations sont bien saisies";
          request.setAttribute("msg_info", message);
          request.getRequestDispatcher("vueetu").forward(request, response);
          
      }catch(Exception ex){
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServeletSaisirPres.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ServeletSaisirPres.class.getName()).log(Level.SEVERE, null, ex);
        }
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
