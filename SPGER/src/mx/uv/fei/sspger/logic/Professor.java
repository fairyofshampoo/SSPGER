/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

/**
 *
 * @author miche
 */
public class Professor {
    private String honorificTitle;
    private String personalNumber;
    
    public Professor(){
        
    }
    public String getHonorificTitle(){
        return honorificTitle;
    }
    public void setHonorificTitle(String honorificTitle){
        this.honorificTitle = honorificTitle;
    }
    public String getNumeroPersonal(){
        return personalNumber;
    }
    public void setNumeroPersonl(String personalNumber){
        this.personalNumber = personalNumber;
    }
}
