/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;
import java.util.LinkedList;

/**
 *
 * @author Lucie
 */
public class DataAnalysis {
    
    private Timestamp SleepBegin;
    private Timestamp ParadoxalSleepBegin;
    private Timestamp ParadoxalSleepEnd;
    
    private String Username;
    
    private Connection conn;
    
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
    
    public void analyse(){
        
        //call getInfosKnimeResult
        // parcourir la liste et récupérer les dates de début et fin = remplir des variables
        // insert line in Analysis à chaque fois qu'on a rplacé le dates = set date à null après chq inertion
        Query_DB query = new Query_DB();
        LinkedList<AnalysedMesure> list = query.collectInfosKnimeResult();
        DataInsertion insertData = new DataInsertion();
        
        for(int i = 0; i<list.size(); i++){
            
            SleepBegin = list.get(0).getDate();
            
            for(int j = 0; j < 4; j++){
                
                String cluster = list.get(i).getCluster();
                if(cluster == "cluster_1"){
                    j++;
                    if(j==1){
                        ParadoxalSleepBegin = list.get(i).getDate();
                    }
                    if(j==4){
                        //parcourir pour déterminer paradoxalsleepend
                        for(int k=0; k < 4; k++){
                            String cluster2 = list.get(i).getCluster();
                            if(cluster2 == "cluster_0"){
                                k++;
                                if(k==1)ParadoxalSleepEnd = list.get(i).getDate();
                            
                            }else{
                                k = 0;
                            }
                        }
                        //trouver cycle
                        insertData.addAnalysis(list.get(i).getUsername(), SleepBegin, ParadoxalSleepBegin, cycle, "preparadoxal");
                        insertData.addAnalysis(list.get(i).getUsername(), ParadoxalSleepBegin, ParadoxalSleepEnd, cycle, "paradoxal");
                        
                        //insertion des dates = 2 insertions car 2 'phases'
                        SleepBegin = ParadoxalSleepEnd;
                        
                        if(i==(list.size()-1)) j=5;
                        
                    }
                }else {
                    j = 0;
                }
                
            }
            
            
        }
        
        
    }
            
}
