package mx.uv.fei.sspger.GUI.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;


public class AcademicBodyCardController{
    @FXML
    private Label lblKeyData;
    
    @FXML
    private Label lblAcademicBodyName;
    
    @FXML
    private Label lblStatus;
    
    private String AcademicBodyKey;
    private final int ACTIVE_STATUS = 1;
    
    public void setAcademicBody(AcademicBody academicBody){
        lblAcademicBodyName.setText(academicBody.getName());
        lblKeyData.setText(academicBody.getKey());
        
        if(academicBody.getStatus() == ACTIVE_STATUS){
            lblStatus.setText("ACTIVO");
        }else{
            lblStatus.setText("INACTIVO");
        }
        
        AcademicBodyKey = academicBody.getKey();
    }
    
    @FXML
    void openAcademicBodyRegisteredSaved(MouseEvent event) {
        AcademicBodyRegisterSavedController.setAcademicBodyKey(AcademicBodyKey);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegisterSaved.fxml");
    }
}
