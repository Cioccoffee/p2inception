/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;


/**
 *
 * @author SHU Yuting
 */
public class Courbe {
    
    private final JFreeChart lineChart;
    private ChartPanel chartPanel;
    private static final int WIDTH = 250;    /* Width of the image */
    private static final int HEIGHT = 180;   /* Height of the image */ 

    
    //name = name of diagramme
    public Courbe(LinkedList listDonnee,LinkedList listTime,String username,String date,String ordonnee,String name) throws IOException{
        
        lineChart = ChartFactory.createLineChart("","Time",ordonnee, createDataset(name,listDonnee,listTime),
        PlotOrientation.VERTICAL,true,true,false);
        String nameJPEG = username + date + name;
        saveAsFile(lineChart, "c:"+ nameJPEG +".jpg");

    }
    
    
    private DefaultCategoryDataset createDataset(String name,LinkedList listDonnee,LinkedList listTime){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        Iterator it1 = listDonnee.iterator();
        Iterator it2 = listTime.iterator();
        while(it1.hasNext()&&it2.hasNext()){
            double donnee = (double)it1.next();
            String time = (String)it2.next();
            dataset.addValue(donnee, name, time);
        }
        return dataset;
    } 
    
    public Courbe(LinkedList listDonnee1,LinkedList listDonnee2,LinkedList listDonnee3,LinkedList listDonnee4,
            LinkedList listTime,String username,String date,String ordonnee,
            String name1,String name2,String name3,String name4,String name){
        lineChart = ChartFactory.createLineChart("","Time",ordonnee, 
                createDataset(name1,name2,name3,name4,listDonnee1,listDonnee2,listDonnee3,listDonnee4,listTime),
                PlotOrientation.VERTICAL,true,true,false);
        String nameJPEG = username + date + name;
        saveAsFile(lineChart, "c:"+ nameJPEG +".jpg");
    
    }
    
    
    private DefaultCategoryDataset createDataset(String name1,String name2,String name3,String name4,LinkedList listDonnee1,
            LinkedList listDonnee2,LinkedList listDonnee3,LinkedList listDonnee4,LinkedList listTime){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        Iterator it1 = listDonnee1.iterator();
        Iterator it2 = listDonnee2.iterator();
        Iterator it3 = listDonnee3.iterator();
        Iterator it4 = listDonnee4.iterator();
        Iterator itT = listTime.iterator();
        while(itT.hasNext()){
            double donnee1 = (double)it1.next();
            double donnee2 = (double)it2.next();
            double donnee3 = (double)it3.next();
            double donnee4 = (double)it4.next();
            String time = (String)itT.next();
            dataset.addValue(donnee1, name1, time);
            dataset.addValue(donnee2, name2, time);
            dataset.addValue(donnee3, name3, time);
            dataset.addValue(donnee4, name4, time);
        }
        return dataset;
    } 
    
    
     public static void saveAsFile(JFreeChart chart, String outputPath) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            ChartUtilities.writeChartAsJPEG(out, chart, WIDTH , HEIGHT);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
 
}
