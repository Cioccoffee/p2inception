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
    PreparedStatement infosKnimeResult;
    PreparedStatement getLastCycleStatement;
    PreparedStatement getLastPhaseStatement;
    Statement infoUser;
    PreparedStatement getAvgCycle;
    PreparedStatement getAvgParadox;
    PreparedStatement getTheDate;
            
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
    
    public LinkedList<AnalysedMesure> collectInfosKnimeResult(String username){
        LinkedList<AnalysedMesure> list = new LinkedList<AnalysedMesure>();
        try{
                infosKnimeResult = conn.prepareStatement("select * from MesureKnimeResult where Username = ?;");
                infosKnimeResult.setString(1,username);
                ResultSet rs = infosKnimeResult.executeQuery();
                
                while(rs.next()){
                    Timestamp date = rs.getTimestamp("Date");
                    String user = rs.getString("Username");
                    String cluster =  rs.getString("Cluster");
                    list.add(new AnalysedMesure(date, cluster, user));

                }
                return list;
        }catch(SQLException ex){
            return null;
        }
             
    }
    
    public int getLastCycle(String username){
        int cycle =0;
        try{
            this.getLastCycleStatement = this.conn.prepareStatement("select Cycle from Analysis where Username = ? and DateBegin in (select max(DateBegin) from Analysis where Username = ?);");
            getLastCycleStatement.setString(1,username);
            getLastCycleStatement.setString(2,username);
            ResultSet rs = getLastCycleStatement.executeQuery();
            if(rs.next())cycle = rs.getInt("Cycle");
             
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            //cycle = 0;
        }
        return cycle;
    }
    
    public String getLastPhase(String username){
        String phase = "";
        try{
           this.getLastPhaseStatement = this.conn.prepareStatement("select Phase from Analysis where Username = ? and DateBegin in (select max(DateBegin) from Analysis where Username = ?);");
           getLastPhaseStatement.setString(1,username);
           getLastPhaseStatement.setString(2,username);
           ResultSet rs = getLastPhaseStatement.executeQuery();
           if(rs.next())phase = rs.getString("Phase");
        }catch(SQLException ex){
           phase ="wake";
        }
        return phase;
    }
    
    public LinkedList<String> getUser(){
        LinkedList<String> listNom = new LinkedList<String>();
        try{
            infoUser = conn.createStatement();
            ResultSet rs = infoUser.executeQuery("select Name from User");
            while(rs.next()){
                    String username = rs.getString("Name");
                    listNom.add(username);
                }
        }catch(SQLException ex){
            return null;
        };

        return listNom;
    }
    
     public Time getMoyenCycle(String username){
        Time moyenCycle= null;
        try{
            this.getAvgCycle = this.conn.prepareStatement("select AvgCycle from User where Name = ?;");
            getAvgCycle.setString(1,username);
            ResultSet rs = getAvgCycle.executeQuery();
            moyenCycle = rs.getTime("AvgCycle");

        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }

        return moyenCycle;
    }
     
    public Time getMoyenParadox(String username){
        Time moyenParadox = null;
        try{
            this.getAvgParadox = this.conn.prepareStatement("select AvgParadox from User where Name = ?;");
            getAvgParadox.setString(1,username);
            ResultSet rs = getAvgParadox.executeQuery();
            moyenParadox = rs.getTime("AvgParadox");

        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }
        return moyenParadox;
    }
    
    public LinkedList<Date> getListDate(String user){
        LinkedList<Date> listDate = new LinkedList();
        try{
            getTheDate = conn.prepareStatement("select DateBegin from Analysis where Username = ? group by DateBegin;");
            getTheDate.setString(1, user);
            ResultSet rs = getTheDate.executeQuery();
            while(rs.next()){
                listDate.add(rs.getDate("DateBegin"));
            }
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            listDate = null;
        }
        return listDate;
    }
    
    
}
