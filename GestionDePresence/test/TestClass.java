
import bd.Creneau;
import bd.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestClass {

    public static void main(String[] args) throws Exception {
        /*-----Création d'une format de date-----*/
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        /*----- Ouverture de la session et de la transaction -----*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
//        /*-----Les enregistrements des données-----*/
        String sql = "select * from CRENEAU";
        List<Creneau> lstE = session.createSQLQuery(sql)//
                .addEntity(Creneau.class)//
                .list();
        System.out.println(lstE.get(0).getDatecre());

//       
//        String s= "tous,";
//        String[] idsG = s.split(",");
//        for (int i = 0; i < idsG.length; i++) {
//               System.out.println(idsG[i]);
//            
//        }
    }
}
