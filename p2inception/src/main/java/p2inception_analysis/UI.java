/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.text.*;
import java.util.*;

/**
 *
 * @author SHU Yuting
 */
public class UI extends JFrame implements ActionListener{
    private final int LARGEUR_FENETRE = 885; 
    private final int HAUTEUR_FENETRE = 730;
    private JButton myButtonCherche;
    private JButton myButtonEffacer;
    private JComboBox boxDate;
    private JPanel panneauPaint;
    private JTextField textUsername;
    private Query_DB queryDB;
    private String user;
    private String avgCycle;
    private String avgParadox;
    private String[] allDate;
    
    public UI(){
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
        
        /**
         * Mon panneau 1
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
        panneauUser.add(myButtonCherche);
 
        myButtonEffacer = new JButton("Effacer");
        myButtonEffacer.setBounds(480, 5, 90, 60);
        panneauUser.add(myButtonEffacer);    
        
        /**
         * Mon panneau 2
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
        /**if(queryDB.getUser().contains(user)){
            DateFormat df =  new SimpleDateFormat("DD/MM/YYYY HH:mm:ss");
            allDate[0]=null;
            for(int i=1;i<=queryDB.getListDate(user).size();i++){
                allDate[i] = df.format(queryDB.getListDate(user).get(i-1));//i ou i-1
            }
        }*/
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
        
        JLabel textTemp = new JLabel("Température");
        textTemp.setBounds(90, 10, 80, 20);
        panneauGraphe.add(textTemp);
        
        JLabel textPouls = new JLabel("Pouls");
        textPouls.setBounds(100, 200, 80, 20);
        panneauGraphe.add(textPouls);
        
        JLabel textMVT = new JLabel("Mouvement");
        textMVT.setBounds(90,390,80,20);
        panneauGraphe.add(textMVT);
               
        JPanel bg_Graph1 = new JPanel();
        bg_Graph1.setLayout(null);
        bg_Graph1.setBounds(10,10,250,180);
        bg_Graph1.setBackground(Color.white);
        panneauGraphe.add(bg_Graph1);
        
        JPanel bg_Graph2 = new JPanel();
        bg_Graph2.setLayout(null);
        bg_Graph2.setBounds(10,200,250,180);
        bg_Graph2.setBackground(Color.white);
        panneauGraphe.add(bg_Graph2);
        
        JPanel bg_Graph3 = new JPanel();
        bg_Graph3.setLayout(null);
        bg_Graph3.setBounds(10,390,250,180);
        bg_Graph3.setBackground(Color.white);
        panneauGraphe.add(bg_Graph3);
        
        /**
         * Mon panneau paint
         */
        panneauPaint = new JPanel(){
            public void paint(Graphics g){
                g.drawString(user,150,10);
                g.drawString(avgCycle, 150, 40);
                g.drawString(avgParadox, 150, 70);
            }
        };
        panneauInfo.add(panneauPaint);
        
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
         * Mon panneau lancer
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
        
        JTextField textName = new JTextField();
        textName.setBounds(180, 50, 180, 30);
        panneauLancer.add(textName);
        
        JButton buttonArduino = new JButton("lancer l'acquisition Arduino");
        buttonArduino.setBounds(400, 50, 200, 30);
        panneauLancer.add(buttonArduino);
        
        JButton buttonAnalyse = new JButton("lancer l'analyse des donnees");
        buttonAnalyse.setBounds(620, 50, 200, 30);
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
        
        
        
        myButtonCherche.addActionListener(this);
        myButtonEffacer.addActionListener(this);
        //boxDate.addActionListener(this); 

        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == myButtonCherche){
            user = textUsername.getText();
            if(queryDB.getUser().contains(user)){
                Time avgCycleA = queryDB.getMoyenCycle(user);
                Time avgParadoxA = queryDB.getMoyenParadox(user);
                DateFormat df =  new SimpleDateFormat("HH:mm:ss");
                avgCycle = df.format(avgCycleA);
                avgParadox = df.format(avgParadoxA);
                panneauPaint.repaint();
            }
            
        }else if(e.getSource() == myButtonEffacer){
            effacer();
        }
    }
    
    public void effacer(){
        textUsername.setText(null);
        user = null;
        avgCycle = null;
        avgParadox = null;
    
    }
    
    
    public static void main (String args[]) {
	new UI();        
    }
    
}
