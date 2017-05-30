/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author SHU Yuting
 */
public class UI extends JFrame implements ActionListener{
    private final int LARGEUR_FENETRE = 885; 
    private final int HAUTEUR_FENETRE = 630;
    private JButton myButtonCherche;
    private JButton myButtonEffacer;
    private JComboBox boxDate;
    
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
        
        
        /**
         * Mon panneau 1
         */
        JPanel panneauUser = new JPanel();
        panneauUser.setBounds(10,10,580,70);
        panneauUser.setLayout(null);
        panneauUser.setBackground(Color.green);
        
        JLabel affTest = new JLabel();
        affTest.setFont(new Font("Dialog",2,16));
        affTest.setText("Nom d'utilisateur :");
        affTest.setBounds(10, 15, 180, 40);
        affTest.setBackground(Color.white);
        panneauUser.add(affTest);
        
        JTextField textUsername = new JTextField();
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
        panneauInfo.setBounds(10,90,580,100);
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
        panneauAnalyse.setBounds(10,200,580,390);
        panneauAnalyse.setBackground(Color.LIGHT_GRAY);
        
        JPanel bg_Analyse = new JPanel();
        bg_Analyse.setLayout(null);
        bg_Analyse.setBounds(150,20,250,40);
        bg_Analyse.setBackground(Color.white);
        panneauAnalyse.add(bg_Analyse);
        
        JLabel textAnalyse = new JLabel();
        textAnalyse.setFont(new Font("Dialog",2,22));
        textAnalyse.setText("ANALYSE");
        textAnalyse.setBounds(75,0,150,40);
        bg_Analyse.add(textAnalyse);
        
        JLabel textDate = new JLabel("Date :");
        textDate.setFont(new Font("Dialog",2,14));
        textDate.setBounds(150,60,50,50);
        panneauAnalyse.add(textDate);
        
        boxDate = new JComboBox();
        boxDate.setBounds(200,70,200,30);
        panneauAnalyse.add(boxDate);
        
        JPanel bg_Graph = new JPanel();
        bg_Graph.setLayout(null);
        bg_Graph.setBounds(20,120,540,250);
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
         * Mon panneau Global
         */
        JPanel panneauGlobal = new JPanel();
        panneauGlobal.setBounds(0, 0, LARGEUR_FENETRE,HAUTEUR_FENETRE);
        panneauGlobal.setLayout(null);
        panneauGlobal.setBackground(Color.white);
        panneauGlobal.add(panneauUser);
        panneauGlobal.add(panneauInfo);
        panneauGlobal.add(panneauAnalyse);       
        panneauGlobal.add(panneauGraphe);
        
        setContentPane(panneauGlobal);
        
        setVisible(true);
        
        myButtonCherche.addActionListener(this);
        myButtonEffacer.addActionListener(this);
        boxDate.addActionListener(this);     
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == myButtonCherche){
            
        }else if(e.getSource() == myButtonEffacer){
        
        }else if(e.getSource() == boxDate){
        }

    }
    
    
    
    public static void main (String args[]) {
	new UI();        
    }
    
}
