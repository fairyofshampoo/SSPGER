package mx.uv.fei.sspger.GUI.controllers;


import javafx.scene.control.CheckBox;


public class TableAcademicBodyMember {
    String email;
    String name;
    CheckBox checkBoxMember;
    CheckBox checkBoxResponsible;
    
    public TableAcademicBodyMember(){
        
    }

    public CheckBox getCheckBoxMember() {
        return checkBoxMember;
    }

    public void setCheckBoxMember(CheckBox checkBoxMember) {
        this.checkBoxMember = checkBoxMember;
    }

    public CheckBox getCheckBoxResponsible() {
        return checkBoxResponsible;
    }

    public void setCheckBoxResponsible(CheckBox checkBoxResponsible) {
        this.checkBoxResponsible = checkBoxResponsible;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
