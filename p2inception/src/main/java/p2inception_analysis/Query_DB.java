/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    PreparedStatement getTemp;
    PreparedStatement getPulse;
            
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
    
    public Time getAvgCycle(String username){
        Time moyenCycle= null;
        try{
            this.getAvgCycle = this.conn.prepareStatement("select AvgCycle from User where Name = ?;");
            getAvgCycle.setString(1,username);
            ResultSet rs = getAvgCycle.executeQuery();
            rs.next();
            moyenCycle = rs.getTime("AvgCycle");

        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }

        return moyenCycle;
    }
     
    public Time getAvgParadox(String username){
        Time moyenParadox = null;
        try{
            this.getAvgParadox = this.conn.prepareStatement("select AvgParadox from User where Name = ?;");
            getAvgParadox.setString(1,username);
            ResultSet rs = getAvgParadox.executeQuery();
            rs.next();
            moyenParadox = rs.getTime("AvgParadox");

        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }
        return moyenParadox;
    }
    
    public LinkedList<Date> getListDate(String user){//listDate java,datebegin sql;
        LinkedList<Date> listDate = new LinkedList();
        try{
            getTheDate = conn.prepareStatement("select DateBegin from Analysis where Username = ?;");
            getTheDate.setString(1, user);
            ResultSet rs = getTheDate.executeQuery();
            while(rs.next()){
                listDate.add(new Date(rs.getTimestamp("DateBegin").getTime()));
            }
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            listDate = null;
        }
        return listDate;
    }
    
    /**
     *
     * @param user
     * @return
     */
    public LinkedList getTemp(String username,Timestamp date){
        LinkedList listTemp = new LinkedList();
        try{
            getTemp = conn.prepareStatement("select Temp from Mesure where Username = ? and Date = ?  and Date >= DATE_ADD(?,INTERVAL 21 HOUR ) and Date <= DATE_ADD(?,INTERVAL 38 HOUR);");
            getTemp.setString(1, username);
            getTemp.setTimestamp(2,date);
            getTemp.setTimestamp(3,date);
            
            ResultSet rs = getTemp.executeQuery();
            while(rs.next()){
                listTemp.add(rs.getDouble("Temp"));
            }

        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            getTemp = null;
        }
        return listTemp;
    } 
    
    public LinkedList getPulse(String username,Timestamp date){
        LinkedList listPulse = new LinkedList();
        try{
            getPulse = conn.prepareStatement("select Pulse from Mesure where Username = ?  and Date >= DATE_ADD(?,INTERVAL 21 HOUR ) and Date <= DATE_ADD(?,INTERVAL 38 HOUR);");
            getPulse.setString(1, username);
            getPulse.setTimestamp(2,date);
            getPulse.setTimestamp(3,date);
            ResultSet rs = getPulse.executeQuery();
            while(rs.next()){
                listPulse.add(rs.getInt("Pulse"));
            }
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            listPulse = null;
        }
        return listPulse;
    }
    
    public LinkedList<MesureMovement> getMovement(String username,Timestamp date){
        LinkedList<MesureMovement> listMovement = new LinkedList();
        try{
            PreparedStatement getMovement = conn.prepareStatement("select MaxAcc, MaxGyr, AvgAcc, AvgGyr from Mesure where Username = ? and Date >= DATE_ADD(?,INTERVAL 21 HOUR ) and Date <= DATE_ADD(?,INTERVAL 38 HOUR);");
            getMovement.setString(1, username);
            getMovement.setTimestamp(2,date);
            getMovement.setTimestamp(3,date);
            ResultSet rs = getMovement.executeQuery();
            while(rs.next()){
                float MaxAcc = rs.getFloat("MacAcc");
                float MaxGyr = rs.getFloat("MaxGyr");
                float AvgAcc = rs.getFloat("AvgAcc");
                float AvgGyr = rs.getFloat("AvgGyr");
                listMovement.add(new MesureMovement(MaxAcc,MaxGyr,AvgAcc,AvgGyr));
            }
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            listMovement = null;
        }
        return listMovement;
    }
    
    public LinkedList<String> getTime(String username,Timestamp date){
        LinkedList<String> listTime = new LinkedList();
        try{
            PreparedStatement getTime = conn.prepareStatement("select Date from Mesure where Username = ? and Date >= DATE_ADD(?,INTERVAL 21 HOUR ) and Date <= DATE_ADD(?,INTERVAL 38 HOUR)");
            getTime.setString(1,username);
            getTime.setTimestamp(2, date);
            getTime.setTimestamp(3, date);
            ResultSet rs = getTime.executeQuery();
            while(rs.next()){
                DateFormat df =  new SimpleDateFormat("HH:mm:ss");
                listTime.add(df.format(rs.getDate("Date")));
            }
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
            listTime = null;
        }
        return listTime;
    
    }
    
}
