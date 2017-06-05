/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;
import java.util.LinkedList;
import java.util.Date;

/**
 *
 * @author Lucie
 */
public class DataAnalysis {
    
    private Timestamp NightBegin;
    private Timestamp SleepBegin;
    private Timestamp ParadoxalSleepBegin;
    private Timestamp ParadoxalSleepEnd;
    
    private String Username;
    
    private Connection conn;
    
    
    private PreparedStatement calculateAvgCycleStatement;
    private PreparedStatement calculateAvgParadoxStatement;
    private PreparedStatement sumLucidDreamStatement;
    private PreparedStatement getLastAnalysisDateStatement;      
    
    private PreparedStatement updateUserStatement;
    
    public DataAnalysis(){
        try { 
                        
            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvÃ©...");
            //CrÃ©ation d'une connexion sur la base de donnÃ©e
            this.conn = DriverManager.getConnection("jdbc:mysql://nas-caranton.dynv6.net:995/p2inception", "lucie", "r@xt9Wkba9z4N$9g");
            System.out.println("Connexion Ã©tablie...");
            
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error !");
            System.exit(0);
         }
    }
    
    public void analyse(String username){
        
        //try{
            //call getInfosKnimeResult
        // parcourir la liste et récupérer les dates de début et fin = remplir des variables
        // insert line in Analysis à chaque fois qu'on a rplacé le dates = set date à null après chq inertion
        Query_DB query = new Query_DB();
        LinkedList<AnalysedMesure> list = query.collectInfosKnimeResult(username);
        //lo hacer para el user que we use = add a parameter cause else it's gonna get fucked up in counting
        DataInsertion insertData = new DataInsertion();
        
        NightBegin = list.get(0).getDate();
        int j = 0;
        int k = 0;
        int l = 0;
        boolean night_begin = true;
        for(int i = 0; i<list.size(); i++){
            String cluster = list.get(i).getCluster();
            String user = list.get(i).getUsername();
            int cycle = query.getLastCycle(user);
            String lastPhase = query.getLastPhase(user);
            
            if(cluster.equals( "cluster_2")){
                j++;
                k = 0;
                l = 0;
                if(j==1){
                    NightBegin = list.get(i).getDate();
                    if(night_begin){
                        cycle = 0;
                        night_begin = false;
                    }
                }
                if(j==4){
                    cycle++;
                    insertData.addAnalysis(user, NightBegin, cycle, "wake");
                }
            }else if(cluster.equals( "cluster_1")){
                j = 0;
                k++;
                l = 0;
                if(k==1){
                   SleepBegin = list.get(i).getDate();
                }
                if(k==4){
                    insertData.setAnalysisEnd(SleepBegin,NightBegin, user);
                    if(lastPhase.equals("paradoxal")) cycle++;
                    insertData.addAnalysis(user, SleepBegin, cycle, "preparadoxal");
                }
            }else if(cluster.equals( "cluster_0")){
                j = 0;
                k = 0;
                l++;
                if(l==1){
                    ParadoxalSleepBegin = list.get(i).getDate();
                }
                if(l==4){
                    insertData.setAnalysisEnd(ParadoxalSleepBegin, SleepBegin, user) ;
                    insertData.addAnalysis(user, ParadoxalSleepBegin, cycle, "paradoxal");
                }
            }
            
            /*for(int j = 0; j < 4; j++){
                
                String cluster = list.get(i).getCluster();
                if(cluster.equals( "cluster_2")){
                    j++;
                    if(j==1){
                        ParadoxalSleepBegin = list.get(i).getDate();
                    }
                    if(j==4){
                        //parcourir pour déterminer paradoxalsleepend
                        for(int k=0; k < 4; k++){
                            String cluster2 = list.get(i).getCluster();
                            if(cluster2.equals( "cluster_1" )){
                                k++;
                                if(k==1)ParadoxalSleepEnd = list.get(i).getDate();
                            
                            }else{
                                k = 0;
                            }
                            i++;
                        }
                        //trouver cycle
                        String user = list.get(i).getUsername();
                        int cycle = query.getLastCycle(user);
                        cycle++;
                        
                        insertData.addAnalysis(user, SleepBegin, ParadoxalSleepBegin, cycle, "wake");
                        insertData.addAnalysis(user, SleepBegin, ParadoxalSleepBegin, cycle, "preparadoxal");
                        insertData.addAnalysis(user, ParadoxalSleepBegin, ParadoxalSleepEnd, cycle, "paradoxal");
                        
                        //insertion des dates = 2 insertions car 2 'phases'
                        SleepBegin = ParadoxalSleepEnd;
                        
                        if(i==(list.size()-1)) j=5;
                        
                        
                    }
                }else {
                    j = 0;
                }
                i++;
            }*/
            
            
        }
        int lastCycle = query.getLastCycle(username);
        String lastPhase = query.getLastPhase(username);
        Timestamp endOfAnalysis = list.get((list.size()-1)).getDate();
        insertData.setAnalysisEnd2(endOfAnalysis,lastCycle,lastPhase,username);
        
        //}catch(SQLException ex){
            //ex.printStackTrace(System.err);
        //}
        
    }
    
    public void updateUser(String user){
        
        try{
            //faire les calculs
            calculateAvgCycleStatement = this.conn.prepareStatement("select sum(timestampdiff(second, DateBegin, DateEnd)) as avgCycle from Analysis where Username = ? group by Cycle;");
            calculateAvgParadoxStatement = this.conn.prepareStatement("select avg (timestampdiff(second, DateBegin, DateEnd)) as avgParadox from Analysis where Username = ? and Phase = 'paradoxal';");
            sumLucidDreamStatement = this.conn.prepareStatement("select sum(Phase) as lucidDream from Analysis where Username =  ? and Phase = 'paradoxal';");
            getLastAnalysisDateStatement = this.conn.prepareStatement("select max(Date) as lastAnalysis from Mesure where Username = ?;");
            
            this.calculateAvgCycleStatement.setString(1, user);
            this.calculateAvgParadoxStatement.setString(1, user);
            this.sumLucidDreamStatement.setString(1, user);
            this.getLastAnalysisDateStatement.setString(1, user);
            
            //avgCycle
            LinkedList cycleLengthList = new LinkedList();
            long avgCycle = 0;
            ResultSet rsCycle = calculateAvgCycleStatement.executeQuery();
            while(rsCycle.next()){
                cycleLengthList.add(rsCycle.getLong("avgCycle"));
            }
            
            for(int i = 0; i < cycleLengthList.size(); i++){
                avgCycle += (long)cycleLengthList.get(i);
            }
            
            //avgCycle = avgCycle / cycleLengthList.size();
            Timestamp avgCycleCalculated = new Timestamp( 0,0,0,0,0, (int)(avgCycle/ cycleLengthList.size()),0);
            
            //avgParadox
            ResultSet rsParadox = calculateAvgParadoxStatement.executeQuery();
            rsParadox.next();
            Timestamp avgParadox = new Timestamp(0,0,0,0,0,(int)rsParadox.getLong("avgParadox"),0);
            
            //lucid dreams
            ResultSet rsLucid = sumLucidDreamStatement.executeQuery();
            rsLucid.next();
            int lucidDream = rsLucid.getInt("lucidDream");
            
            //last analysis
            ResultSet rsLastAna = getLastAnalysisDateStatement.executeQuery();
            rsLastAna.next();
            //Timestamp lastAnalysis = new Timestamp( (int) rsLastAna.getInt("lastAnalysis"));
            Timestamp lastAnalysis = rsLastAna.getTimestamp("lastAnalysis");
            
            //insérer
            updateUserStatement = this.conn.prepareStatement("update User set AvgCycle = ?, AvgParadox = ?, LucidDream = ?, LastAnalysis = ?  where Name = ?");
            updateUserStatement.setTimestamp(1,avgCycleCalculated);
            updateUserStatement.setTimestamp(2,avgParadox);
            updateUserStatement.setInt(3,lucidDream);
            updateUserStatement.setTimestamp(4, lastAnalysis);
            updateUserStatement.setString(5, user);
            
            updateUserStatement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }
    }
    
    public static void main(String[] args){
        final DataAnalysis data_analysis = new DataAnalysis();
        data_analysis.analyse("WhiteRat1");
        data_analysis.updateUser("WhiteRat1");
    }
            
}
