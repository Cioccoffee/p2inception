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
        panneauUser.setBounds(50,30,700,60);
        panneauUser.setLayout(null);
        panneauUser.setBackground(Color.green);
        
        JLabel affTest = new JLabel();
        affTest.setText("Nom d'utilisateur");
        affTest.setBounds(10, 10, 200, 40);
        affTest.setBackground(Color.white);
        panneauUser.add(affTest);
        
        JTextField textUsername = new JTextField();
        textUsername.setBounds(200,10,150,40);
        textUsername.setBackground(Color.white);
        panneauUser.add(textUsername);
        
        JButton myButtonCherche = new JButton("Chercher");
        myButtonCherche.setBounds(550, 0, 100, 60);
        myButtonCherche.setBackground(Color.white);
        myButtonCherche.setForeground(Color.black);
        panneauUser.add(myButtonCherche);
        
        /**
         * Mon panneau Global
         */
        JPanel panneauGlobal = new JPanel();
        panneauGlobal.setBounds(0, 0, LARGEUR_FENETRE,HAUTEUR_FENETRE);
        panneauGlobal.setLayout(null);
        panneauGlobal.setBackground(Color.white);
        panneauGlobal.add(panneauUser);
        
        setContentPane(panneauGlobal);
        
        setVisible(true);
        
        
        
    }
    
    public static void main (String args[]) {
	new UI();
    }
    
}
