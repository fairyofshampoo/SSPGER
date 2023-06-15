package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mx.uv.fei.sspger.logic.Project;


public class AvailableProjectCardController implements Initializable{
    @FXML
    private AnchorPane apProjectCard;
    
    @FXML
    private Label lblDuration;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblSpaces;
        
    public void setAvailableProjectData(Project project){
        lblProjectName.setText(project.getName());
        lblModality.setText(project.getModality());
        lblSpaces.setText(Integer.toString(project.getSpaces()));
        lblDuration.setText(Integer.toString(project.getDuration()) + " meses");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
