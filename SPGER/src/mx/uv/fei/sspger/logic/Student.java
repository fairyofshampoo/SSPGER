/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

/**
 *
 * @author miche
 */
public class Student {
    private String institutionalEMail;
    private String name;
    private String lastName;
    private String registrationTag;
    
    public Student(){
        
    }
    public String getInstitutionalEMail(){
        return institutionalEMail;
    }
    public void setInstitutionalEMail(String institutionalEMail){
        this.institutionalEMail = institutionalEMail;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getlastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getRegistrationTag(){
        return registrationTag;
    }
    public void setRegistrationTag(String registrationTag){
        this.registrationTag = registrationTag;
    }
}
