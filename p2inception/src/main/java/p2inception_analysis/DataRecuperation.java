/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Date;
import java.io.*;


import fr.insalyon.p2i2.javaarduino.util.Console;
    
import fr.insalyon.p2i2.javaarduino.usb.ArduinoUsbChannel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import jssc.SerialPortException;
import jssc.SerialNativeInterface;

/**
 *
 * @author Lucie
 */
public class DataRecuperation {
    
    //attributs = data to collect
    //private String Subject_Name ;
    private Date date ;
    private int pulse;
    private double temp1;
    private double temp2;
    private float avgAcc;
    private float avgGyr;
    private float maxAcc;
    private float maxGyr;
    
    
    
    // connexion avec arduino
 //package fr.insalyon.p2i2.javaarduino;


    

    
    //public static void main( String[] args )
            public void launchAcquisition(String user)
    {
        final Console console = new Console();
        
        console.log( "DEBUT du programme TestArduino !.." );
        
        final DataRecuperation dataRecup = new DataRecuperation();
        
        String port = null;
        
        do {
        
            console.log( "RECHERCHE d'un port disponible..." );
            port = ArduinoUsbChannel.getOneComPort();
            
            if (port == null) {
                console.log( "Aucun port disponible!" );
                console.log( "Nouvel essai dans 5s" );
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    // Ignorer l'Exception
                }
            }

        } while (port == null);
        
        port = "COM3";
        
        console.println("Connection au Port " + port);
        try {

            final ArduinoUsbChannel vcpChannel = new ArduinoUsbChannel(port);
            DataInsertion data_insert = new DataInsertion();
            Thread readingThread = new Thread(new Runnable() {

                public void run() {
                    
                    BufferedReader vcpInput = new BufferedReader(new InputStreamReader(vcpChannel.getReader()));
                    String line ;
                    try {
                        //PrintWriter writer = new PrintWriter( new OutputStreamWriter(new FileOutputStream("SavedData\\"+(new Date()).getTime()+".csv")));
                        while((line = vcpInput.readLine())!= null){
                            dataRecup.read(line);

                            
                            data_insert.addMesure(dataRecup.date, dataRecup.pulse, dataRecup.temp1, dataRecup.temp2, dataRecup.maxAcc, dataRecup.maxGyr, dataRecup.avgAcc, dataRecup.avgGyr, user);
                            data_insert.setTemp(dataRecup.date);
                            System.out.println("data : " + dataRecup.date +" "+ dataRecup.pulse +" "+dataRecup.temp1+" "+dataRecup.temp2+" "+dataRecup.maxAcc+" "+dataRecup.maxGyr+" "+dataRecup.avgAcc+" "+dataRecup.avgGyr );

                        }
                    
                    
                    
                    
                    } catch(IOException ex){
                        ex.printStackTrace(System.err);
                    }
                    
                    System.out.println("data : " + dataRecup.date +" "+ dataRecup.pulse +" "+dataRecup.temp1+" "+dataRecup.temp2+" "+dataRecup.maxAcc+" "+dataRecup.maxGyr+" "+dataRecup.avgAcc+" "+dataRecup.avgGyr );

                }
                
            });
            
            readingThread.start();
            
            vcpChannel.open();
            
            boolean exit = false;
            
            while (!exit) {
            
                String line = console.readLine("Envoyer une ligne (ou 'fin') > ");
            
                if (line.length() == 0) {
                    continue;
                }
                
                if ("fin".equals(line)) {
                    exit = true;
                    continue;
                }
                
                vcpChannel.getWriter().write(line.getBytes("UTF-8"));
                vcpChannel.getWriter().write('\n');
            
            }
            
            vcpChannel.close();
            
            readingThread.interrupt();
            
            try {
                readingThread.join(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
            
              
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        } catch (SerialPortException ex) {
            ex.printStackTrace(System.err);
        }
       
        
    
    }
    


    
    //recuperation des données et set de la date
    public void read(String line){


        if(line!=null){
            String[] data = line.split(":");
            //System.out.println(data[0]);
            int t1 = Integer.parseInt(data[0]);
            
            temp1 = t1/100.0;
            int t2 = Integer.parseInt(data[1]);
            temp2 = t2/100.0;
            maxAcc = Float.parseFloat(data[2]);
            maxGyr = Float.parseFloat(data[3]);
            avgAcc = Float.parseFloat(data[4]);
            avgGyr = Float.parseFloat(data[5]);
            pulse = Integer.parseInt(data[6]);
            date = new Date(); //faire un format
            //Subject_Name = "";

        }


    }
    
    

    
}
    
    //établissement connexion avec remote DB
    //enregistrement direct des tuples
    //SI DB ne peut pas être atteinte, création fichier output et écriture dedans
    //ajout du nom du fichier non envoyé dans une liste attribut qu'on ouvre à chaque XXX
    

