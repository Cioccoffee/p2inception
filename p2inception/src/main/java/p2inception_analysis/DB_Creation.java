/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.sql.DriverManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.util.LinkedList;


/**
 *
 * @author Lucie
 */
public class DB_Creation {
    
    private Connection conn;
    private PreparedStatement createMesureStatement;
    
    public static void main(String[] a){
        DB_Creation creation = new DB_Creation("lucie","r@xt9Wkba9z4N$9g");
    }
 
    public DB_Creation(String login, String password){
        try {

            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvé...");

            //Création d'une connexion sur la base de donnée
            this.conn = DriverManager.getConnection("jdbc:mysql://nas-caranton.dynv6.net:995/p2inception", login, password);
            //this.conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/" + bd, compte, motDePasse);
            System.out.println("Connexion établie...");

            //Liste des instructions de création de table
            LinkedList<String> CreationInstructionsList = new LinkedList<String>();
            //CreationInstructionsList.add("drop table Analysis");
            CreationInstructionsList.add("alter table Analysis add constraint defaultUsername default 'null' for Username;");
            //CreationInstructionsList.add("create table User(Name varchar(20) NOT NULL, AvgCycle Time, AvgParadox Time, LucidDream int(2), LastAnalysis Date, primary key(Name));");
            //CreationInstructionsList.add("create table Analysis(Username varchar(20) NOT NULL default 'null', DateBegin Datetime NOT NULL, DateEnd Datetime, Cycle int(2), Phase varchar(1),WakingDuringPhase boolean, primary key(DateBegin,Username), foreign key (Username) references User(Name));");
            //CreationInstructionsList.add("create table Mesure(Username varchar(20) NOT NULL default 'null', Date Datetime NOT NULL, Pulse int(3), Temp1 double(3,1), Temp2 double(3,1), Temp double(3,1), MaxAcc float(24), MaxGyr float(24), AvgAcc float(24), AvgGyr float(24),  primary key(Date), foreign key (Username) references User(Name));");
            
            boolean tableCreated = false;
            for(int i = 0; i < CreationInstructionsList.size(); i++){
                while(!tableCreated){
                this.createMesureStatement = this.conn.prepareStatement(CreationInstructionsList.get(i));
                tableCreated = this.createMesureStatement.execute();
                }
            }
            
            
            
            //this.insertMesureStatement = this.conn.prepareStatement("INSERT INTO Mesure (valeur,idCapteur,dateMesure) VALUES (?,?,?) ;");
            //this.selectMesuresStatement = this.conn.prepareStatement("SELECT valeur,idCapteur numInventaire,dateMesure FROM Mesure WHERE idCapteur = ? AND dateMesure >= ? AND dateMesure < ? ;");

        } catch (Exception ex) {
            System.out.println("DB pas trouvée");
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
        
        
    }
    
    
}
