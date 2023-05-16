package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.CheckBox;


public class LGACTable {
    private String nameLGAC;
    private CheckBox cbkLGAC;

    public LGACTable(String nameLGAC, CheckBox cbxLGAC) {
        this.nameLGAC = nameLGAC;
        this.cbkLGAC = cbxLGAC;
    }

    public String getNameLGAC() {
        return nameLGAC;
    }

    public void setNameLGAC(String nameLGAC) {
        this.nameLGAC = nameLGAC;
    }

    public CheckBox getCbkLGAC() {
        return cbkLGAC;
    }

    public void setCbkLGAC(CheckBox cbkLGAC) {
        this.cbkLGAC = cbkLGAC;
    }
    
    
}
