
import bd.HibernateUtil;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class TestFaireAppel {
    public static void main(String[] args) {
        /*-----Création d'une format de date-----*/
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        /*----- Ouverture de la session et de la transaction -----*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        Transaction t = session.beginTransaction();
        /*-----Les enregistrements des données-----*/
        
//        ArrayList<Etudiant> lst=services.serviceEtudiant.getAllEtudiants(session);
//        for (Etudiant e :lst ) {
//            System.out.println(e);
//        }
//        Formation f =null;
//            for (Etudiant e : services.serviceEtudiant.getEtudiants(lst, f)) {
//            System.out.println(e);
//        }

    
 
    }
}
