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
    
    public JFreeChart lineChart;
    public ChartPanel chartPanel;
    private static final int WIDTH = 250;    /* Width of the image */
    private static final int HEIGHT = 180;   /* Height of the image */ 

    
    //name = name of diagramme
    public Courbe(LinkedList listDonnee,LinkedList listTime,String username,String date,String ordonnee,String name) throws IOException{
        
        lineChart = ChartFactory.createLineChart("","Time",ordonnee, createDataset(name,listDonnee,listTime),
        PlotOrientation.VERTICAL,true,true,false);
        String nameJPEG = username + date + ordonnee + name;
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
