/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2inception_analysis;

/**
 *
 * @author SHU Yuting
 */
public class MesureMovement {
    //MaxAcc float(24), MaxGyr float(24), AvgAcc float(24), AvgGyr 
    private float MaxAcc;
    private float MaxGyr;
    private float AvgAcc;
    private float AvgGyr;
    
    public MesureMovement(float MaxAcc,float MaxGyr,float AvgAcc,float AvgGyr){
        this.MaxAcc = MaxAcc;
        this.MaxGyr = MaxGyr;
        this.AvgAcc = AvgAcc;
        this.AvgGyr = AvgGyr;
    }
    
    public float getMaxAcc(){
        return MaxAcc;
    }
    
    public float getMaxGyr(){
        return MaxGyr;
    }
    public float getAvgAcc(){
        return AvgAcc;
    }
    public float getAvgGyr(){
        return AvgGyr;
    }
}
