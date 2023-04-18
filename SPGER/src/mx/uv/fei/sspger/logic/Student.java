/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

/**
 *
 * @author miche
 */
public class Student extends User{
    private String registrationTag;
    
    public Student(){
        
    }
 
    public String getRegistrationTag(){
        return registrationTag;
    }
    public void setRegistrationTag(String registrationTag){
        this.registrationTag = registrationTag;
    }
}
