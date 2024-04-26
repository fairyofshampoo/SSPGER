package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.CheckBox;

public class TableAcademicBodyMember {

    String email;
    String name;
    CheckBox chkBoxMember;
    CheckBox chkBoxResponsible;
    String responsible;

    public TableAcademicBodyMember() {

    }

    public CheckBox getCheckBoxMember() {
        return chkBoxMember;
    }

    public void setCheckBoxMember(CheckBox checkBoxMember) {
        this.chkBoxMember = checkBoxMember;
    }

    public CheckBox getCheckBoxResponsible() {
        return chkBoxResponsible;
    }

    public void setCheckBoxResponsible(CheckBox checkBoxResponsible) {
        this.chkBoxResponsible = checkBoxResponsible;
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

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
