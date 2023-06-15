package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class ProjectManagerController implements Initializable {
    @FXML
    private Button btnAddAssignment;

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Button btnViewDetails;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblCodirectorName;

    @FXML
    private Label lblDirectorName;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblStudentName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
