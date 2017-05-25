/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Query_DB{
    private Connection conn;
    private Statement stmt;
    private ResultSet res;
    
    Statement infoMesureAll;
            
    /**
     * Methode pour se connecter Ã  la base ; prend en paramÃ¨tre le login et le mot de passe
     */
    public Query_DB() {
            try { 
                        
                                //Enregistrement de la classe du driver par le driverManager
                                Class.forName("com.mysql.jdbc.Driver");
				System.out.println("Driver trouvÃ©...");
				 //CrÃ©ation d'une connexion sur la base de donnÃ©e
				this.conn = DriverManager.getConnection("jdbc:mysql://nas-caranton.dynv6.net:995/p2inception", "lucie", "r@xt9Wkba9z4N$9g");
                                System.out.println("Connexion Ã©tablie...");
                                
                                
                                
		    }
                    catch(Exception e){
                                    System.out.println(e.getMessage());
                                    System.out.println("Error !");
                                    System.exit(0);
                            }
                
                     
                    
            } 
    
    public String getInfoMesureAll(){
        String infos ="";
        try{
            infoMesureAll = conn.createStatement();
            ResultSet rs = infoMesureAll.executeQuery("select * from Mesure;");
            while(rs.next()){
                infos += rs.getInt("pulse")+rs.getDouble("Temp1")+rs.getDouble("Temp2")+rs.getFloat("maxAcc");
            }
        }catch(SQLException ex){
            infos ="err";
        /*}catch(IOException ex){
            infos = "err";*/
        }
        
        return infos;
    }
             
}
