package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.CheckBox;

public class LgacTable {

    CheckBox cbkLgac;
    String nameLgac;

    public LgacTable(CheckBox cbkLgac, String nameLgac) {
        this.cbkLgac = cbkLgac;
        this.nameLgac = nameLgac;
    }

    public CheckBox getCbkLgac() {
        return cbkLgac;
    }

    public void setCbkLgac(CheckBox cbkLgac) {
        this.cbkLgac = cbkLgac;
    }

    public String getNameLgac() {
        return nameLgac;
    }

    public void setNameLgac(String nameLgac) {
        this.nameLgac = nameLgac;
    }

}
