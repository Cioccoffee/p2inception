/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;


/**
 *
 * @author SHU Yuting
 */
public class Courbe {
    
    public JFreeChart lineChart;
    
    public Courbe(String ordonnee){
        lineChart = ChartFactory.createLineChart("","Time",ordonnee, createDataset(),
        PlotOrientation.VERTICAL,true,true,false);
        
    
    }
    
    public DefaultCategoryDataset createDataset(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        return dataset;
    } 
    
    
   
}
