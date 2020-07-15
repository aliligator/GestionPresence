
import bd.HibernateUtil;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import org.hibernate.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shinu
 */
public class TestLogin {
        public static void main(String[] args) {
        /*-----Création d'une format de date-----*/
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        /*----- Ouverture de la session et de la transaction -----*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

// Tester de la fonction existEmail
//        String e = "ravatfranck@ut-capitole.fr";
//        String md = "ravat123";
//        System.out.println(LoginTest.existEmailEnseig(e, md));
        
    // Tester de la fonction estResponsable
      int i = 2;
      //System.out.println(LoginTest.estResponsable(i));
    }
    
}
