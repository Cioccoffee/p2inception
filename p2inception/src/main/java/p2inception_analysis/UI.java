/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.ui.RefineryUtilities;
 
/**
 *
 * @author SHU Yuting
 */
public class UI extends JFrame implements ActionListener,ItemListener{
	private final int LARGEUR_FENETRE = 885;
	private final int HAUTEUR_FENETRE = 730;
    
	private JButton myButtonCherche;
	private JButton myButtonEffacer;
	private JButton buttonArduino;
	private JButton buttonAnalyse;
    
	private JComboBox boxDate;
    
	private JPanel panneauPaint;
    
	private JPanel bg_Graph1;
	private JPanel bg_Graph2;
	private JPanel bg_Graph3;
    
	private JLabel imageTemp;
	private JLabel imagePouls;
	private JLabel imageMVT;
	private JLabel Info1;
	private JLabel Info2;
	private JLabel Info3;
    
	private JTextField textUsername; //panneau de recherche
	private JTextField textName; //panneau d'acquisition
    
	private Query_DB queryDB;
    
	private String user;
	private String avgCycle;
	private String avgParadox;

        private Timestamp dateTS;
        private String date;
	private Courbe courbe;

    
	public UI() throws IOException{
    	setTitle("Interface");
    	setLayout(null);
    setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE);
    // Pour placer la fenêtre au centre de l'écran
    setLocationRelativeTo(null); // centrer la fenêtre sur l'écran
    // Pour empêcher le redimensionnement de la fenêtre
    setResizable(false);
    // Pour permettre la fermeture de la fenêtre lors de l'appui sur la croix rouge
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   	 
    	queryDB = new Query_DB();
   	 
    	//test
    	/**LinkedList listdonnee = new LinkedList();
    	listdonnee.add(37.0);
    	listdonnee.add(37.8);
    	listdonnee.add(36.5);
    	LinkedList listtime = new LinkedList();
    	listtime.add("11:12;50");
    	listtime.add("11:13:30");
    	listtime.add("11:15:20");
    	courbe = new Courbe(listdonnee,listtime,"user","2017-05-31","temperature","temperature-time");*/
  	 
   	 
   	 
    	/**
     	* Mon panneau 1 : panneauUser pour la recherche de résultats
     	*/
    	JPanel panneauUser = new JPanel();
    	panneauUser.setBounds(10,40,580,70);
    	panneauUser.setLayout(null);
    	panneauUser.setBackground(Color.green);
   	 
    	JLabel affTest = new JLabel();
    	affTest.setFont(new Font("Dialog",2,16));
    	affTest.setText("Nom d'utilisateur :");
    	affTest.setBounds(10, 15, 180, 40);
    	affTest.setBackground(Color.white);
    	panneauUser.add(affTest);
   	 
    	textUsername = new JTextField();
    	textUsername.setBounds(150,15,200,40);
    	textUsername.setBackground(Color.white);
    	panneauUser.add(textUsername);
   	 
    	myButtonCherche = new JButton("Chercher");
    	myButtonCherche.setBounds(380, 5, 90, 60);
    	myButtonCherche.addActionListener(this);
    	panneauUser.add(myButtonCherche);
 
    	myButtonEffacer = new JButton("Effacer");
    	myButtonEffacer.setBounds(480, 5, 90, 60);
    	myButtonEffacer.addActionListener(this);
    	panneauUser.add(myButtonEffacer);    
   	 
    	/**
     	* Mon panneau 2 : panneauInfo (résumé de l'utilisateur)
     	*/
    	JPanel panneauInfo  = new JPanel();
    	panneauInfo.setBounds(10,120,580,100);
    	panneauInfo.setLayout(null);
    	panneauInfo.setBackground(Color.YELLOW);
   	 
    	JLabel textInfo1 = new JLabel();
    	textInfo1.setBounds(10, 10, 200, 20);
    	textInfo1.setText("Nom:" );
    	textInfo1.setBackground(Color.white);
    	panneauInfo.add(textInfo1);
   	 
    	JLabel textInfo2 = new JLabel();
    	textInfo2.setBounds(10, 40, 200, 20);
    	textInfo2.setText("Moyenne cycle:" );
    	textInfo2.setBackground(Color.white);
    	panneauInfo.add(textInfo2);
   	 
    	JLabel textInfo3 = new JLabel();
    	textInfo3.setBounds(10, 70, 200, 20);
    	textInfo3.setText("Moyenne paradox:" );
    	textInfo3.setBackground(Color.white);
    	panneauInfo.add(textInfo3);
  	 
    	Info1 = new JLabel();
    	Info1.setBounds(150, 10, 200, 20);
    	Info1.setBackground(Color.white);
    	panneauInfo.add(Info1);
   	 
    	Info2 = new JLabel();
    	Info2.setBounds(150, 40, 200, 20);
    	Info2.setBackground(Color.white);
    	panneauInfo.add(Info2);
   	 
    	Info3 = new JLabel();
    	Info3.setBounds(150, 70, 200, 20);
    	Info3.setBackground(Color.white);
    	panneauInfo.add(Info3);
   	 
   	 
   	 
    	/**panneauPaint = new JPanel(true){
        	public void paint(Graphics g){
            	super.paint(g);
            	g.setColor(Color.BLACK);
            	g.setFont(new Font("Dialog",1,18));
            	g.drawString("user", 150, 10);
        	}
    	};
    	panneauPaint.setLayout(null);
    	panneauPaint.setBounds(10,120,580,100);
    	panneauInfo.add(panneauPaint);*/
   	 
 	 
   	 
  	 
   	 
    	/**
     	* Mon panneau 3
     	*/
    	JPanel panneauAnalyse = new JPanel();
    	panneauAnalyse.setLayout(null);
    	panneauAnalyse.setBounds(10,230,580,360);
    	panneauAnalyse.setBackground(Color.LIGHT_GRAY);
   	 
    	JPanel bg_Analyse = new JPanel();
    	bg_Analyse.setLayout(null);
    	bg_Analyse.setBounds(150,10,250,40);
    	bg_Analyse.setBackground(Color.white);
    	panneauAnalyse.add(bg_Analyse);
   	 
    	JLabel textAnalyse = new JLabel();
    	textAnalyse.setFont(new Font("Dialog",2,22));
    	textAnalyse.setText("ANALYSE");
    	textAnalyse.setBounds(75,0,150,40);
    	bg_Analyse.add(textAnalyse);
   	 
    	JLabel textDate = new JLabel("Date :");
    	textDate.setFont(new Font("Dialog",2,14));
    	textDate.setBounds(150,50,50,50);
    	panneauAnalyse.add(textDate);
   	 
    	boxDate = new JComboBox();
    	boxDate.setBounds(200,60,200,30);
    	panneauAnalyse.add(boxDate);
   	 
    	JPanel bg_Graph = new JPanel();
    	bg_Graph.setLayout(null);
    	bg_Graph.setBounds(20,100,540,240);
    	bg_Graph.setBackground(Color.white);
    	panneauAnalyse.add(bg_Graph);
   	 
   	 
    	/**
     	* Mon panneau 4
     	*/
    	JPanel panneauGraphe = new JPanel();
    	panneauGraphe.setLayout(null);
    	panneauGraphe.setBounds(600,10,270,580);
    	panneauGraphe.setBackground(Color.orange);
   	 
    	/**JLabel textTemp = new JLabel("Température");
    	textTemp.setBounds(90, 10, 80, 20);
    	panneauGraphe.add(textTemp);
   	 
    	JLabel textPouls = new JLabel("Pouls");
    	textPouls.setBounds(100, 200, 80, 20);
    	panneauGraphe.add(textPouls);
   	 
    	JLabel textMVT = new JLabel("Mouvement");
    	textMVT.setBounds(90,390,80,20);
    	panneauGraphe.add(textMVT);*/
          	 
    	bg_Graph1 = new JPanel();
    	bg_Graph1.setLayout(null);
    	bg_Graph1.setBounds(10,10,250,180);
    	bg_Graph1.setBackground(Color.white);
    	panneauGraphe.add(bg_Graph1);
   	 
    	//test
    	/**JLabel image = new JLabel(new ImageIcon("c:user2017-05-31temperature-time.jpg"));
    	image.setBounds(0,0,250,180);
    	bg_Graph1.add(image);*/
   	 
   	 
    	bg_Graph2 = new JPanel();
    	bg_Graph2.setLayout(null);
    	bg_Graph2.setBounds(10,200,250,180);
    	bg_Graph2.setBackground(Color.white);
    	panneauGraphe.add(bg_Graph2);
   	 
    	bg_Graph3 = new JPanel();
    	bg_Graph3.setLayout(null);
    	bg_Graph3.setBounds(10,390,250,180);
    	bg_Graph3.setBackground(Color.white);
    	panneauGraphe.add(bg_Graph3);
   	 
   	 
   	 
    	/**
     	* Mon panneauUtilise
     	*/
    	JPanel panneauUtilise = new JPanel();
    	panneauUtilise.setBounds(0,100,LARGEUR_FENETRE,HAUTEUR_FENETRE-100);
    	panneauUtilise.setLayout(null);
    	panneauUtilise.setBackground(Color.PINK);
    	panneauUtilise.add(panneauUser);
    	panneauUtilise.add(panneauInfo);
    	panneauUtilise.add(panneauAnalyse);  	 
    	panneauUtilise.add(panneauGraphe);
   	 
    	JLabel textCherche = new JLabel("Chercher votre resultats:");
    	textCherche.setFont(new Font("Dialog",2,20));
    	textCherche.setBounds(10,10,300,20);
    	panneauUtilise.add(textCherche);
   	 
    	/**
     	* Mon panneau 4 : lancer l'acquisition
     	*/
    	JPanel panneauLancer = new JPanel();
    	panneauLancer.setLayout(null);
    	panneauLancer.setBackground(Color.LIGHT_GRAY);
    	panneauLancer.setBounds(0,0,885,100);
 
    	JLabel textLancer = new JLabel("Utiliser notre systeme:");
    	textLancer.setFont(new Font("Dialog",2,20));
    	textLancer.setBounds(10,10,300,20);
    	panneauLancer.add(textLancer);
   	 
    	JLabel textEntrer = new JLabel("Entrer votre nom,svp:");
    	textEntrer.setBounds(40,55,200,20);
    	panneauLancer.add(textEntrer);
   	 
    	textName = new JTextField();
    	textName.setBounds(180, 50, 180, 30);
    	panneauLancer.add(textName);
   	 
    	buttonArduino = new JButton("lancer l'acquisition Arduino");
    	buttonArduino.setBounds(400, 50, 200, 30);
    	buttonArduino.addActionListener(this);
    	panneauLancer.add(buttonArduino);
   	 
    	buttonAnalyse = new JButton("lancer l'analyse des donnees");
    	buttonAnalyse.setBounds(620, 50, 200, 30);
    	buttonAnalyse.addActionListener(this);
    	panneauLancer.add(buttonAnalyse);
   	 
    	/**
     	* Mon panneau Global
     	*/
    	JPanel panneauGlobal = new JPanel();
    	panneauGlobal.setBounds(0, 0, LARGEUR_FENETRE,HAUTEUR_FENETRE);
    	panneauGlobal.setLayout(null);
    	panneauGlobal.setBackground(Color.white);
    	panneauGlobal.add(panneauUtilise);
    	panneauGlobal.add(panneauLancer);
   	 
    	setContentPane(panneauGlobal);

    	boxDate.addItemListener(this);
 
    	setVisible(true);
	}
    
	public void actionPerformed(ActionEvent e){
   	 
   	 
    	//BOUTON Chercher
    	if(e.getSource() == myButtonCherche){
        	user = textUsername.getText();
                //boxDate.addItem("2001-01-01 21:30:00");
                //LinkedList<Date> dateList = new LinkedList<Date>();
                LinkedList<Timestamp> dateList = new LinkedList<>();
        	if(queryDB.getUser().contains(user)){
                    Time avgCycleA = queryDB.getAvgCycle(user);
                    Time avgParadoxA = queryDB.getAvgParadox(user);
                    DateFormat df =  new SimpleDateFormat("HH:mm:ss");
                    avgCycle = df.format(avgCycleA);
                    avgParadox = df.format(avgParadoxA);
                    Info1.setText(user);
                    Info2.setText(avgCycle);
                    Info3.setText(avgParadox);
                
                    
        	}
                
                
                //JComboBox
                DateFormat df2 =  new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
                
                dateList = queryDB.getListDate(user);
                for(int i=0; i<dateList.size() ;i++){
                    boxDate.addItem(df2.format( new Date(dateList.get(i).getTime()) ) );
                }
                
                repaint();
       	 
       	 
    	//BOUTON Effacer
    	}else if(e.getSource() == myButtonEffacer){
            effacer();
       	 
    	//BOUTON Acquisition Arduino
    	}else if(e.getSource() == buttonArduino){
                user = textName.getText();
        	if( !(queryDB.getUser().contains(user)) ){
            	DataInsertion data_insert = new DataInsertion();
            	data_insert.addUser(user);
        	}
        	DataRecuperation data_recuperation = new DataRecuperation();
        	data_recuperation.launchAcquisition(user);
   	 
    	//BOUTON Analyse
    	}else if(e.getSource() == buttonAnalyse){
        	user = textName.getText();
        	DataAnalysis data_analysis = new DataAnalysis();
        	data_analysis.analyse(user);
        	data_analysis.updateUser(user);
    	}
      	 
	}
    
	public void effacer(){
            textUsername.setText(null);
            user = null;
            avgCycle = null;
            avgParadox = null;
            Info1.setText(user);
            Info2.setText(avgCycle);
            Info3.setText(avgParadox);
            boxDate.removeAllItems();
            bg_Graph1.removeAll();
            bg_Graph2.removeAll();
            bg_Graph3.removeAll();
            repaint();
	}
        
        
        
	public void tracerCourbeTemp() throws IOException{
            LinkedList listTemp = queryDB.getTemp(user,dateTS);
            LinkedList listTime = queryDB.getTime(user,dateTS);
            Courbe courbeTemp = new Courbe(listTemp,listTime,user,date,"Temperature","Temperature-time");
   	 
            String name = user + date + "temperature-time";
            imageTemp = new JLabel(new ImageIcon("c:"+ name + ".jpg"));
            imageTemp.setBounds(0,0,250,180);
            bg_Graph1.add(imageTemp);
	}
    
	public void tracerCourbePouls() throws IOException{
    	LinkedList listPouls = queryDB.getPulse(user,dateTS);
    	LinkedList listTime = queryDB.getTime(user,dateTS);
    	Courbe courbePouls = new Courbe(listPouls,listTime,user,date,"Pouls","Pouls-time");
   	
    	String name = user + date + "Pouls-time";
    	imagePouls = new JLabel(new ImageIcon("c:"+ name + ".jpg"));
    	imagePouls.setBounds(0,0,250,180);
    	bg_Graph2.add(imagePouls);
	}
    
	public void tracerCourbeMVT(){
    	LinkedList listTime = queryDB.getTime(user,dateTS);
    	LinkedList<MesureMovement> listMVT = queryDB.getMovement(user,dateTS);
    	LinkedList listMaxAcc = new LinkedList();
    	LinkedList listMaxGyr = new LinkedList();
    	LinkedList listAvgAcc = new LinkedList();
    	LinkedList listAvgGyr = new LinkedList();
    	Iterator it = listMVT.iterator();
    	int i = 0;
    	while(it.hasNext()){
        	listMaxAcc.add(listMVT.get(i).getMaxAcc());
        	listMaxGyr.add(listMVT.get(i).getMaxGyr());
        	listAvgAcc.add(listMVT.get(i).getAvgAcc());
        	listAvgGyr.add(listMVT.get(i).getAvgGyr());
        	i++;
    	}
    	Courbe courbeMVT = new Courbe(listMaxAcc,listMaxGyr,listAvgAcc,listAvgGyr,listTime,
            	user,date,"mouvement","MaxAcc","MaxGyr","AvgAcc","AvgGyr","mouvement-time");
    	String name = user + date + "mouvement-time";
    	imageMVT = new JLabel(new ImageIcon("c:"+ name + ".jpg"));
    	imageMVT.setBounds(0,0,250,180);
    	bg_Graph3.add(imageMVT);
	}
    
	public void tracerCourbeAnalyse(){
    
	}
        
        public void itemStateChanged(ItemEvent e){  
            if(e.getStateChange() == ItemEvent.SELECTED){  
                date = (String)e.getItem();
                dateTS = Timestamp.valueOf(date);
                //dateTS = new Timestamp((new Date(date)).getTime());
                try {
                    
                    tracerCourbeTemp();
                    tracerCourbePouls();
                    tracerCourbeMVT();
                    repaint();
                } catch (IOException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
        
    public static void main (String args[]) throws IOException {
        UI ui = new UI();   	 
    }
    
}
 
