/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miche
 */
public class AccessAccount {
    private String email;
    private String password;
    private int privileges;
    
    public AccessAccount(){
        
    }
    public String getEMail(){
        return email;
    }
    public void setEMail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public int getPrivileges(){
        return privileges;
    }
    public void setPrivileges(int privileges){
        this.privileges = privileges;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final AccessAccount other = (AccessAccount)object;
        return (this.email == null ? other.email == null: this.email.equals(other.email));
    }
}
   
