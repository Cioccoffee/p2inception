/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author SHU Yuting
 */
public class UI extends JFrame{
    private final int LARGEUR_FENETRE = 800; 
    private final int HAUTEUR_FENETRE = 600;
    
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
        panneauUser.setBounds(50,20,700,70);
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
        
        JButton myButtonCherche = new JButton("Chercher");
        myButtonCherche.setBounds(500, 5, 90, 60);
        panneauUser.add(myButtonCherche);
        
        JButton myButtonEffacer = new JButton("Effacer");
        myButtonEffacer.setBounds(600, 5, 90, 60);
        panneauUser.add(myButtonEffacer);
        
        /**
         * Mon panneau 2
         */
        JPanel panneauInfo  = new JPanel();
        panneauInfo.setBounds(150,100,500,100);
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
        panneauAnalyse.setBounds(30,220,300,330);
        panneauAnalyse.setBackground(Color.LIGHT_GRAY);
        
        JPanel bg_Analyse = new JPanel();
        bg_Analyse.setLayout(null);
        bg_Analyse.setBounds(20,20,250,70);
        bg_Analyse.setBackground(Color.white);
        panneauAnalyse.add(bg_Analyse);
        
        JLabel textAnalyse = new JLabel();
        textAnalyse.setFont(new Font("Dialog",2,26));
        textAnalyse.setText("ANALYSE");
        textAnalyse.setBounds(60,10,200,50);
        bg_Analyse.add(textAnalyse);
        
        JLabel textDate = new JLabel("Date :");
        textDate.setFont(new Font("Dialog",2,14));
        textDate.setBounds(20,100,50,50);
        panneauAnalyse.add(textDate);
        
        JComboBox boxDate = new JComboBox();
        boxDate.setBounds(70,110,200,30);
        panneauAnalyse.add(boxDate);
        
        /**
         * Mon panneau 4
         */
        JPanel panneauGraph = new JPanel();
        panneauGraph.setLayout(null);
        panneauGraph.setBounds(365,220,400,330);
        panneauGraph.setBackground(Color.ORANGE);
        
        JButton buttonTemps = new JButton("Temps");
        buttonTemps.setBounds(20, 40, 80, 60);
        panneauGraph.add(buttonTemps);
        
        JButton buttonPouls = new JButton("Pouls");
        buttonPouls.setBounds(20, 140, 80, 60);
        panneauGraph.add(buttonPouls);
        
        JButton buttonMVT = new JButton("MVT");
        buttonMVT.setBounds(20,240,80,60);
        panneauGraph.add(buttonMVT);
        
        JLabel textGraph = new JLabel("GRAPHIQUE");
        textGraph.setFont(new Font("Dialog",2,18));
        textGraph.setBounds(185,10,150,40);
        panneauGraph.add(textGraph);
        
        JPanel bg_Graph = new JPanel();
        bg_Graph.setLayout(null);
        bg_Graph.setBounds(125,60,250,250);
        bg_Graph.setBackground(Color.white);
        panneauGraph.add(bg_Graph);
        
        
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
        panneauGlobal.add(panneauGraph);
        
        setContentPane(panneauGlobal);
        
        setVisible(true);
        
        
        
    }
    
    public static void main (String args[]) {
	new UI();
    }
    
}
