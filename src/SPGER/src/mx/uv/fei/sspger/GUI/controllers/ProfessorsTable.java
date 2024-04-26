package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.CheckBox;

public class ProfessorsTable {

    String emailProfessor;
    String nameProfessor;
    CheckBox cbkCodirector;
    CheckBox cbkDirector;

    public ProfessorsTable(String emailProfessor, String nameProfessor, CheckBox cbkCodirector, CheckBox cbkDirector) {
        this.emailProfessor = emailProfessor;
        this.nameProfessor = nameProfessor;
        this.cbkCodirector = cbkCodirector;
        this.cbkDirector = cbkDirector;
    }

    public String getEmailProfessor() {
        return emailProfessor;
    }

    public void setEmailProfessor(String emailProfessor) {
        this.emailProfessor = emailProfessor;
    }

    public String getNameProfessor() {
        return nameProfessor;
    }

    public void setNameProfessor(String nameProfessor) {
        this.nameProfessor = nameProfessor;
    }

    public CheckBox getCbkCodirector() {
        return cbkCodirector;
    }

    public void setCbkCodirector(CheckBox cbkCodirector) {
        this.cbkCodirector = cbkCodirector;
    }

    public CheckBox getCbkDirector() {
        return cbkDirector;
    }

    public void setCbkDirector(CheckBox cbkDirector) {
        this.cbkDirector = cbkDirector;
    }

}
