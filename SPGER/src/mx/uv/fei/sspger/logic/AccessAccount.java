/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

/**
 *
 * @author miche
 */
public class AccessAccount {
    private String institutionalEMail;
    private String password;
    private int privileges;
    
    public AccessAccount(){
        
    }
    public String getInstitutionalEMail(){
        return institutionalEMail;
    }
    public void setInstitutionalEMail(String email){
        this.institutionalEMail = email;
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
}
