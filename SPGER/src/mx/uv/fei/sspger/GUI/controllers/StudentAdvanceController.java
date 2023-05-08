package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class StudentAdvanceController implements Initializable {
    private int assignmentId;
    private int submisionId;
    
    @FXML
    private ImageView imgBackButton;

    @FXML
    private Label lblAssignmentTitle;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblDeliveryDate;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    @FXML
    void goBack(MouseEvent event) {

    }

    @FXML
    void mouseEnteredBackButtonArea(MouseEvent event) {

    }

    @FXML
    void mouseExitedBackButtonArea(MouseEvent event) {

    } 
    
}
