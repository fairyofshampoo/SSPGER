package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;


public class AcademicBodyCardController implements Initializable {
    @FXML
    private Label lblKeyData;
    
    @FXML
    private Label lblAcademicBodyName;
    
    @FXML
    private VBox vBoxAcademicBodyCard;
    
    @FXML
    private AnchorPane apAcademicBodyCard;
    
    private String AcademicBodyKey;
    
    public void setAcademicBody(AcademicBody academicBody){
        lblAcademicBodyName.setText(academicBody.getName());
        lblKeyData.setText(academicBody.getKey());
        AcademicBodyKey = academicBody.getKey();
    }
    
    @FXML
    void openAcademicBodyRegisteredSaved(MouseEvent event) {
        AcademicBodyRegisterSavedController.setAcademicBodyKey(AcademicBodyKey);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegisterSaved.fxml");

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
