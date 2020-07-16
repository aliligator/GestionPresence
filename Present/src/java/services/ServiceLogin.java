/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bd.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author yuxuan
 */
public class ServiceLogin {
    /**
     * méthode pour examiner l'email saisi est exist ou pas.
     * @param email
     * @param mdp
     * 
     */

    public static int existEmailEnseig (Session session,String email, String mdp){
       // récuperer les infos. des enseignants, etudiants et gestionnairescolarites
       Query query = session.createQuery("from Enseignant");
       List<Enseignant> lenseignant = query.list();
       
       //créer list pour email 
       List<String> lmailensei = new ArrayList<String>();
       for (Enseignant en : lenseignant){
           lmailensei.add(en.getMaile());}
       
       
       // vérifier si l'email exist dans la liste
       if(lmailensei.contains(email))
       {
           // vérifier la correspondance entre email et mot de passe
           Query query1 = session.createQuery("from Enseignant e where e.maile = :email and e.mdpe = :mdp");
           query1.setParameter("email",email);
           query1.setParameter("mdp",mdp);
           List<Enseignant> le = query1.list();
           if(!le.isEmpty())
           {
               int id = le.get(0).getCodee();
               return id;
           }
           else
           {
               return -1;
           }  
       }
       else
       {
           return 0;
       }
       
       
    }
    
    public static int existEmailEtudiant (Session session,String email, String mdp){
       
       
       // récuperer les infos. des etudiants
       Query query = session.createQuery("from Etudiant");
       List<Etudiant> letudiant = query.list();
       
       //créer list pour email 
       List<String> lmailetu = new ArrayList<String>();
       for (Etudiant etu : letudiant){
           lmailetu.add(etu.getMailetu());}
       
       // vérifier si l'email exist dans les trois listes d'email
       if (lmailetu.contains(email))
       {
           Query query1 = session.createQuery("from Etudiant etu where etu.mailetu = :email and etu.mdpetu = :mdp");
           query1.setParameter("email",email);
           query1.setParameter("mdp",mdp);
           List<Etudiant> le = query1.list();
           if(!le.isEmpty()){
               int id = le.get(0).getNumetudiant();
               return id;
           }else{
               return -1;
           }
           
       }else{
           return 0;
       }
       
       
    }
    
    public static int existEmailGScolarite (Session session,String email, String mdp){
       // récuperer les infos. des etudiants
       Query query = session.createQuery("from Gestionnairescolarite");
       List<Gestionnairescolarite> lgs = query.list();
       
       //créer list pour email 
       List<String> lmailgs = new ArrayList<String>();
       for (Gestionnairescolarite gs : lgs){
           lmailgs.add(gs.getMailgs());}
       
       // vérifier si l'email exist dans les trois listes d'email
       if(lmailgs.contains(email))
       {
           Query query1 = session.createQuery("from Gestionnairescolarite gs where gs.mailgs = :email and gs.mdpgs = :mdp");
           query1.setParameter("email",email);
           query1.setParameter("mdp",mdp);
           List<Gestionnairescolarite> le = query1.list();
           if(!le.isEmpty()){
               int id = le.get(0).getCodegs();
               return id ;
           }else{
               return -1;
           }
           
       }else{
           return 0;
       }
 
       
    }
    
        public static boolean estResponsable(Session session,int idEnseignant)
    {
      
        
        Enseignant en = (Enseignant)session.load(Enseignant.class,idEnseignant);
        if(en.getFormations().size()!=0){
           return true; 
        }else{
            return false;
        }
  
    }
    

}
