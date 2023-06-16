package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class StudentAssignedProjectCardController implements Initializable {
    @FXML
    private Label lblSchoolPeriod;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblStudentStatus;

    @FXML
    private Label lblTotalAssignments;

    @FXML
    private Label lblTotalAssignmentsSended;

    @FXML
    private VBox vCard;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
