
import bd.HibernateUtil;
import java.io.File;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.gestionfichier.gestionpdf.FicheIndividuel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yeping
 */
public class TestCreerFiche {

    public static void main(String[] args) throws Exception {
        FicheIndividuel fi = new FicheIndividuel(348, new Date(2020, 4, 1), System.getProperty("user.dir") + File.separator + "web");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        fi.creerFiche(session);
        t.commit();
        HibernateUtil.getSessionFactory().close();
    }
}
