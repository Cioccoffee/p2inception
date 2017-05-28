/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.*;

/**
 *
 * @author Lucie
 */
public class AnalysedMesure {
    
    Timestamp date;
    String cluster;
    String username;
    
    public AnalysedMesure(Timestamp date, String cluster, String username){
        this.date = date;
        this.cluster = cluster;
        this.username = username;
    }
    
    public Timestamp getDate(){
        return date;
    }
    
    public String getCluster(){
        return cluster;
    }
    
    public String getUsername(){
        return Username;
    }
}
