/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.io.File;

/**
 *
 * @author yuxuan
 */
public class FileUtils {
   
//    public static void method(String path){
//           File file=new File(path);
//        //获取该路径下的文件和文件夹
//        String[] arr=file.list();
//        //遍历
//        
//        if(arr ==null){
//            System.out.println("meiyouo meiyou meiyou");
//        }else{
//          for(String s:arr){
//              System.out.println(s);
//          }
//        }}
    
    public static boolean delete(String path){
        File file = new File(path);
        if(!file.exists()){
            System.out.println("Il n'y a pas ce fichier");
            return false;
        }
        file.delete();
        return true;
    }
}
