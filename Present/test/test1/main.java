/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author alied
 */
public class main {
    
    public static void main(String[] args){
        
        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpresence","gestionnaire","slucropluvo6");
            if ( conn != null)
            {
                System.out.println("réussi");
            }
        } catch (Exception e)
        {
            System.out.println("fail");
        }
        
    }
    
    
    
    
    
}
