package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class ProjectsAcademicBodyViewController implements Initializable {
    
    @FXML
    private Button btnGenerateReport;

    @FXML
    private ChoiceBox<String> cbxProjectsFilter;

    @FXML
    private ImageView imgAcademicBody;

    @FXML
    private ImageView imgCourses;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgProjects;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    void generateReport(MouseEvent event) {

    }

    @FXML
    void homeClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
