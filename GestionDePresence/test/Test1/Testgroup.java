/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test1;

import java.sql.SQLException;
import services.Bdforcours;

/**
 *
 * @author Bonso
 */
public class Testgroup {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
     //   System.out.println(Bdforcours.getCours(1));
        System.out.println(Bdforcours.supprimer(1));
    }

}
