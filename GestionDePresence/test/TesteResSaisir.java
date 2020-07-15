
import bd.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ServiceRespSaisir;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yuxuan
 */
public class TesteResSaisir {
    
            public static void main(String[] args) throws ParseException, Exception {
        /*-----Création d'une format de date-----*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm:ss");
        /*----- Ouverture de la session et de la transaction -----*/
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();

  
    
    // Tester de saisir dans table creneau
    //Cours cours, Enseignant enseignant, Groupe groupe, Date datecre, Long dureecre, Date heuredeb, String typeactivitecre, Set etreprersenters
//    Cours c = null;
//    Enseignant e = null;
//    Groupe g = null;
//    Set<Etreprersenter> etre =null;
//    Creneau c1 = new Creneau (c,e,g,df.parse("06-02-2020"),3L,dfh.parse("09:00:00"),"projet",etre);
//    session.save(c1);
//    t.commit();
//    Query query =session.createQuery("from Creneau c where c.codecre= (select max(c.codecre) from Creneau)");
//    List<Creneau> ids = query.list();
//    for(Creneau i : ids){
//    // Etrepresenceid
//    //int codecre, int numetudiant
//    EtreprersenterId etreID = new EtreprersenterId(i.getCodecre(),1);
//    //EtreprersenterId id, Creneau creneau, Etudiant etudiant
//    Etudiant etu = (Etudiant)session.load(Etudiant.class,1);
//    Etreprersenter etre = new Etreprersenter(etreID,i,etu);
//    session.save(etre);
//    t.commit();}

      // Tester pour afficher les creneaus crées par un responsable 
//      ArrayList<Creneau> l=serviceRespSaisir.listeCreneau(session, 1, "2020-03-14");
//      for (Creneau cre : l){
//          System.out.println(cre.getCodecre());
//      }
      
      //Tester pour afficher des groupes de formations 
        //afficher les formations d'un responsable
//        Query query = session.createQuery("from Enseignant e where e.codee = :code ");
//        query.setParameter("code", 1);
//        List<Enseignant> es = query.list();
//        for(Enseignant e: es){
//            Set<Formation> lf=e.getFormations();
//            List<Formation>  listf = (List<Formation>)lf;
//            for(Formation f:listf){
//                System.out.print(f.getNomf());
//            }
//            
//        }
        
        // afficher les group de ces formations
//        Query query1 = session.createQuery("from Etudiant etu where etu.formation.codef = :cdf");
//        query1.setParameter("cdf", 1);
//        List<Etudiant> letu = query1.list();
//        Set<Groupe> grps = new HashSet<Groupe>();
//        for(Etudiant etu : letu){
//            //int idetu = etu.getNumetudiant();
//             Query query2 = session.createQuery("select distinct a.groupe from Appartenirgroupe a where a.etudiant = :etudiant ");
//             query2.setParameter("etudiant", etu);
//             List<Groupe> apgrps = query2.list();
//             for(Groupe aps : apgrps){
//                 grps.add(aps);
//             }
//        }
//        for (Groupe g :grps){
//            System.out.println(g.getCodeg());}  
        
        // supprimer un creneau 
//        Query query = session.createQuery("from Creneau");
//        List<Creneau> lCre = query.list();
//        Creneau cresup = (Creneau)session.load(Creneau.class,55);
//        lCre.remove(cresup);
//        t.commit();
//          Enseignant respon = (Enseignant)session.load(Enseignant.class,1);
//          Creneau cresup = (Creneau)session.load(Creneau.class,55);
//          String sqlsup = "delete from Etreprersenter etre  where e. ";
//          respon.getCreneaus().remove(cresup);
//          t.commit();
//        String nom = "projet";
        
//        String heur = "14:00";
//        String dur = "3";
//        int grp = 1 ;
//        serviceRespSaisir.ajoutCreneau(session,nom,nom,grp,date,heur,dur,1);   
//        t.commit();
//           String date = "2020-03-14";
//           Set<Creneau> lc=SaisirPres.droitPresence(session,1,"2020-03-14");
//           for (Creneau c:lc){
//               System.out.println(c.getCodecre());
//           }
           
//           Etudiant etu = (Etudiant)session.load(Etudiant.class,1);
//           Formation forma = etu.getFormation();
//           //Trouver responsable de cette formation 
//           Set<Enseignant> ens = forma.getEnseignants();
//           for (Enseignant en : ens){
//               System.out.println(en.getNome());
//           }
//           
//           Query query = session.createQuery("select a.groupe from Appartenirgroupe a where a.etudiant = :etu");
//           query.setParameter("etu", etu);
//           List<Groupe> grps= query.list();
//           for (Groupe g:grps){
//               System.out.println(g.getLibelleg());
//           }
//           
//           Enseignant ensei = (Enseignant)session.load(Enseignant.class,1);
//           Groupe grp1 = (Groupe)session.load(Groupe.class,2);

//                   Query query1 = session.createQuery("from Creneau c where c.enseignant = :en and c.datecre = :datecre and c.groupe = :grp and c.typeactivitecre in(:projet,:conf)");
//                   query1.setParameter("en", ensei);
//                   query1.setParameter("grp",grp1);
//                   query1.setParameter("datecre",df.parse(date));
//                   query1.setParameter("projet", "projet");
//                   query1.setParameter("conf","conference");
//                   List<Creneau> cres = query1.list();
//                   for(Creneau c:cres){
//                       System.out.println(c.getCodecre());
//                       
//                   }
//                   System.out.println("end");
//                int id =1;
//                String datec = "2020-03-14";
//                Set<Creneau> cccc= SaisirPres.droitPresence(session,id,datec);
//                for (Creneau cc: cccc){
//                    System.out.println(cc.getCodecre());
//                }
//                Etudiant etu = (Etudiant)session.load(Etudiant.class, 1);
//                Creneau cr = (Creneau)session.load(Creneau.class,55);
//                EtreprersenterId etreId = new EtreprersenterId(55,1);
//                Etreprersenter etre = new Etreprersenter(etreId,cr,etu,"PRESENCE");
//                Iterator<Etreprersenter> iterator = cr.getEtreprersenters().iterator();
//                while(iterator.hasNext()){
//                System.out.println(iterator.next().getEtudiant().getNumetudiant());
//                }
                
//                 // tester pour update 
//                serviceRespSaisir.upCreneau(session,60,"projetup","projet",3,"2020-03-14","18:00:00","3");
//                t.commit();

//            Etudiant etu = (Etudiant)session.load(Etudiant.class, 1);
//            Set<Ficheindividuelle> fichiers = etu.getFicheindividuelles();
//            int estexiste = 0;
//                for(Ficheindividuelle f :fichiers){
//                    System.out.println(f.getAnneefiche());
//                    System.out.println(f.getMoisfiche());
//                    if("2020".equals(f.getAnneefiche()) && "03".equals(f.getMoisfiche()))
//                    {
//                       estexiste = estexiste+1;}
//                    
//                }
//                
//            System.out.println(estexiste);
//           Etudiant etu = (Etudiant)session.load(Etudiant.class, 1);
//           Query query = session.createQuery("from Ficheindividuelle f where f.etudiant = :etudiant and f.anneefiche = :annee ");
//           query.setParameter("etudiant",etu);
//           query.setParameter("annee","2020");
//           List<Ficheindividuelle> fichiers = query.list(); 
//           for (Ficheindividuelle f:fichiers){
//               System.out.println(f.getUrlfic().substring(26));
//           }
//            

          //exmainerCreneau(Session session,String date, String heure,String dur, int idGrp, int idRespon)
          
//            System.out.println(serviceRespSaisir.exmainerCreneau(session, "2020-03-14", "10:00:00", "10:30:00", 1, 1));
            
//            Set<Etudiant> etus = serviceRespSaisir.lEtudiant(session, 1);
//            for(Etudiant e : etus){
//                System.out.println(e.getNometu());
//            }
            String moisannee = "2020-03";
            String annne = moisannee.substring(0,4);
            String mois = moisannee.substring(5,7);
            System.out.print(mois);
            System.out.print(annne);
            
            Etudiant etu = (Etudiant)session.load(Etudiant.class, 1);
            Ficheindividuelle f = ServiceRespSaisir.lficher_etu(etu, mois, annne);
            System.out.println(f.getUrlfic());
      
}
}
