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


    
    public DataInsertion(){
        try {

            //Enregistrement de la classe du driver par le driverManager
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver trouvé...");

            //Création d'une connexion sur la base de donnée
            this.conn = DriverManager.getConnection("jdbc:mysql://nas-caranton.dynv6.net:995/p2inception", "lucie","r@xt9Wkba9z4N$9g");
            //this.conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/" + bd, compte, motDePasse);
            System.out.println("Connexion établie...");
            
            // Prepared Statement
            String insertMesureLine ="INSERT INTO Mesure (Date, Pulse, Temp1, Temp2, MaxAcc, MaxGyr, AvgAcc, AvgGyr, Username) VALUES (?,?,?,?,?,?,?,?,?);"; //(o°^°o)

            String insertUsersLine ="INSERT INTO Users (Name) VALUES (?);";

            String insertAnalysisLine = "INSERT INTO Analysis (UserName, DateBegin, DateEnd, Cycle, Phase) VALUES (?,?,?,?,?);";

            String analyseTemp1Out = "update Mesure set Temp=Temp1 where (Temp2>=39 or Temp2<=36.5) and Temp1>=36.5 and Temp1<=39 and where Date = ?;";
            String analyseTemp2Out ="update Mesure set Temp=Temp2 where (Temp1>=39 or Temp1<=36.5) and Temp2>=36.5 and Temp2<=39 and where Date = ?;";
            String analyseTempBothIn ="update Mesure set Temp=(Temp1+Temp2)/2.0 where Temp2>=36.5 and Temp2<=39 and Temp1>=36.5 and Temp1<=39 and where Date = ?;";
            
            this.insertMesureStatement = this.conn.prepareStatement(insertMesureLine);
            this.insertUsersStatement = this.conn.prepareStatement(insertUsersLine);
            this.insertAnalysisStatement = this.conn.prepareStatement(insertAnalysisLine);
            
            this.analyseTemp1OutStatement = this.conn.prepareStatement(analyseTemp1Out);
            this.analyseTemp2OutStatement = this.conn.prepareStatement(analyseTemp1Out);
            this.analyseTempBothInStatement = this.conn.prepareStatement(analyseTemp1Out);

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
            this.insertMesureStatement.executeUpdate();
            this.analyseTemp1OutStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTemp2OutStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTempBothInStatement.setTimestamp(1, new Timestamp(date.getTime())  );
            this.analyseTemp1OutStatement.execute();
            this.analyseTemp2OutStatement.execute();
            this.analyseTempBothInStatement.execute();
            return 1;
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

    public int addAnalysis(String username, Timestamp dateBegin, Timestamp dateEnd, int cycle, String phase) {
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
        }
    
    
    public static void main(String[] args){
        
        
        DataInsertion data_insert = new DataInsertion();
        //data_insert.addMesure(new Date(), 64, 37.3,38.5,(float)200.7,(float)608.92,(float)66.7,(float)259.8);
        
        //this.analyseTemp1OutStatement.setTimestamp(new Timestamp(this.date.getTime()));
        //this.analyseTemp1OutStatement.executeUpdate();
        
        Query_DB query = new Query_DB();
        String infos = query.getInfoMesureAll();
        System.out.println(infos);
        
        
    }
    
    
    
    
    
}
    
    

       



