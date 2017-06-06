/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis; 

/**
 *
 * @author Lucie
 */
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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataInsertion {
    private Connection conn;
    private PreparedStatement insertUsersStatement;
    private PreparedStatement insertAnalysisStatement;
    private PreparedStatement insertMesureStatement;
    
    private PreparedStatement analyseTemp1OutStatement;
    private PreparedStatement analyseTemp2OutStatement;
    private PreparedStatement analyseTempBothInStatement;
    private PreparedStatement analyseTempBothOutStatement1;
    private PreparedStatement analyseTempBothOutStatement2;

    private PreparedStatement setAnalysisEndStatement;
    private PreparedStatement setAnalysisEndStatement2;

    
    public DataInsertion(){
        try {

            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvé...");

            //Création d'une connexion sur la base de donnée
            this.conn = DriverManager.getConnection("jdbc:mysql://nas-caranton.dynv6.net:995/p2inception?autoReconnect=true", "lucie","r@xt9Wkba9z4N$9g");
            //this.conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/" + bd, compte, motDePasse);
            
            System.out.println("Connexion établie...");
            
            // Prepared Statement
            String insertMesureLine ="INSERT INTO Mesure (Date, Pulse, Temp1, Temp2, MaxAcc, MaxGyr, AvgAcc, AvgGyr, Username) VALUES (?,?,?,?,?,?,?,?,?);"; //(o°^°o)

            String insertUsersLine ="INSERT INTO User (Name) VALUES (?);";

            String insertAnalysisLine = "INSERT INTO Analysis (UserName, DateBegin, Cycle, Phase) VALUES (?,?,?,?);";

            String analyseTemp1Out = "update Mesure set Temp=Temp1 where (Temp2>=40.0 or Temp2<=35.0) and Temp1>=35.0 and Temp1<=40.0 and  Date = ?;";
            String analyseTemp2Out ="update Mesure set Temp=Temp2 where (Temp1>=40.0 or Temp1<=35.0) and Temp2>=35.0 and Temp2<=40.0 and  Date = ?;";
            String analyseTempBothIn ="update Mesure set Temp=(Temp1+Temp2)/2.0 where Temp2>=35.0 and Temp2<=40.0 and Temp1>=35.0 and Temp1<=40.0 and  Date = ?;";
            String analyseTempBothOut1 ="update Mesure set Temp=Temp1 where (Temp2<=35.0 or Temp2>=40.0) and (Temp1>=40.0 or Temp1<=35.0) and abs(36.5-Temp2)>abs(36.5-Temp1) and  Date = ?;";
            String analyseTempBothOut2 ="update Mesure set Temp=Temp2 where (Temp2<=35.0 or Temp2>=40.0) and (Temp1>=40.0 or Temp1<=35.0) and abs(36.5-Temp1)>abs(36.5-Temp2) and  Date = ?;";
            
            String setAnalysisEndLine ="update Analysis set DateEnd=? where DateBegin=? and Username = ?"; 
            String setAnalysisEndLine2 ="update Analysis set DateEnd=? where Cycle=? and Phase=? and Username = ?";
            
            this.insertMesureStatement = this.conn.prepareStatement(insertMesureLine);
            this.insertUsersStatement = this.conn.prepareStatement(insertUsersLine);
            this.insertAnalysisStatement = this.conn.prepareStatement(insertAnalysisLine);
            
            this.analyseTemp1OutStatement = this.conn.prepareStatement(analyseTemp1Out);
            this.analyseTemp2OutStatement = this.conn.prepareStatement(analyseTemp2Out);
            this.analyseTempBothInStatement = this.conn.prepareStatement(analyseTempBothIn);
            this.setAnalysisEndStatement = this.conn.prepareStatement(setAnalysisEndLine);
            this.setAnalysisEndStatement2 = this.conn.prepareStatement(setAnalysisEndLine2);

        }catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(System.err);
            System.exit(-1);
        }
    }
    
    
    public int addMesure(Date date,int pulse, double temp1, double temp2, float MaxAcc, float MaxGyr, float AvgAcc, float AvgGyr, String Username) {
        try {
            this.insertMesureStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.insertMesureStatement.setInt(2, pulse);
            this.insertMesureStatement.setDouble(3, temp1);
            this.insertMesureStatement.setDouble(4, temp2);
            this.insertMesureStatement.setFloat(5, MaxAcc);
            this.insertMesureStatement.setFloat(6, MaxGyr);
            this.insertMesureStatement.setFloat(7, AvgAcc);
            this.insertMesureStatement.setFloat(8, AvgGyr);
            this.insertMesureStatement.setString(9, Username);
            return this.insertMesureStatement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }

    
    public int addUser(String name) {
            try {
                this.insertUsersStatement.setString(1, name  );
                return this.insertUsersStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                return -1;
            }
        }

    /*public int addAnalysis(String username, Timestamp dateBegin, Timestamp dateEnd, int cycle, String phase) {
            try {
                this.insertAnalysisStatement.setString(1, username  );
                this.insertAnalysisStatement.setTimestamp(2, dateBegin );
                this.insertAnalysisStatement.setTimestamp(3, dateEnd );
                this.insertAnalysisStatement.setInt(4, cycle );
                this.insertAnalysisStatement.setString(5, phase );
                return this.insertAnalysisStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                return -1;
            }
        }*/
    
     public int addAnalysis(String username, Timestamp dateBegin, int cycle, String phase) {
            try {
                this.insertAnalysisStatement.setString(1, username  );
                this.insertAnalysisStatement.setTimestamp(2, dateBegin );
                
                this.insertAnalysisStatement.setInt(3, cycle );
                this.insertAnalysisStatement.setString(4, phase );
                return this.insertAnalysisStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                return -1;
            }
        }
    
    public int setAnalysisEnd(Timestamp end, Timestamp begin, String user){
        try {
                this.setAnalysisEndStatement.setTimestamp(1,end);
                this.setAnalysisEndStatement.setTimestamp(2,begin);
                this.setAnalysisEndStatement.setString(3,user);
                return this.setAnalysisEndStatement.executeUpdate();
                
        } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                return -1;
        }
        
        
    }
    
    public int setAnalysisEnd2(Timestamp end, int cycle, String phase, String user){
        try {
                this.setAnalysisEndStatement2.setTimestamp(1,end);
                this.setAnalysisEndStatement2.setInt(2,cycle);
                this.setAnalysisEndStatement2.setString(3,phase);
                this.setAnalysisEndStatement2.setString(4,user);
                return this.setAnalysisEndStatement.executeUpdate();
                
        } catch (SQLException ex) {
                ex.printStackTrace(System.err);
                return -1;
        }
        
        
    }
    
    
             
    public int setTemp(Date date){
        try {
            
            this.analyseTemp1OutStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTemp2OutStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTempBothInStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTempBothOutStatement1.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTempBothOutStatement2.setTimestamp(1, new Timestamp(date.getTime())  );
            
            this.analyseTemp1OutStatement.executeUpdate();
            this.analyseTemp2OutStatement.executeUpdate();
            this.analyseTempBothOutStatement1.executeUpdate();
            this.analyseTempBothOutStatement2.executeUpdate();
            return this.analyseTempBothInStatement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
            return -1;
        }
    }
    
    
    /*public static void main(String[] args){
        
        
        DataInsertion data_insert = new DataInsertion();
        //data_insert.addMesure(new Date(), 64, 37.3,38.5,(float)200.7,(float)608.92,(float)66.7,(float)259.8);
        
        //this.analyseTemp1OutStatement.setTimestamp(new Timestamp(this.date.getTime()));
        //this.analyseTemp1OutStatement.executeUpdate();
        
        /*Query_DB query = new Query_DB();
        String infos = query.getInfoMesureAll();
        System.out.println(infos);*/
        /*Date dateNow = new Date();
        data_insert.addMesure(dateNow, 63, 32.5, 38, 10, 8, 2, 3, "Cochondinde");
        data_insert.setTemp(dateNow);
                            
    }*/
    
    
    
    
    
}
    
    

       



