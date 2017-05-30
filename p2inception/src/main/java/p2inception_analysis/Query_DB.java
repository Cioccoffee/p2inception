/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;


public class Query_DB{
    private Connection conn;
    private Statement stmt;
    private ResultSet res;
    
    Statement infoMesureAll;
    Statement infosKnimeResult;
    PreparedStatement getLastCycleStatement;
            
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
    
    public LinkedList<AnalysedMesure> collectInfosKnimeResult(){
        LinkedList<AnalysedMesure> list = new LinkedList<AnalysedMesure>();
        try{
                infosKnimeResult = conn.createStatement();
                ResultSet rs = infosKnimeResult.executeQuery("select * from MesureKnimeResult;");
                while(rs.next()){
                    Timestamp date = rs.getTimestamp("Date");
                    String username = rs.getString("Username");
                    String cluster =  rs.getString("Cluster");
                    list.add(new AnalysedMesure(date, cluster, username));

                }
                return list;
        }catch(SQLException ex){
            return null;
        }
             
    }
    
    public int getLastCycle(String username){
        int cycle;
        try{
            this.getLastCycleStatement = this.conn.prepareStatement("select Cycle from Analysis where Username = ? and DateBegin in (select max(DateBegin) from Analysis where Username = ?);");
            getLastCycleStatement.setString(1,username);
            getLastCycleStatement.setString(2,username);
            ResultSet rs = getLastCycleStatement.executeQuery();
            cycle = rs.getInt("Cycle");
             
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            cycle = 0;
        }
        return cycle;
    }
}
