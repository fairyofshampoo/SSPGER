package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class HomeStudentController implements Initializable {
    @FXML
    private AnchorPane coursesCard;

    @FXML
    private HBox coursesCardLayout;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgMyWork;

    @FXML
    private ImageView imgProjects;

    @FXML
    private Label lblSeeMyWork;

    @FXML
    void displayMyWorkView(MouseEvent event) {

    }

    @FXML
    void displayProjectsPostulation(MouseEvent event) {

    }
    
    private void setToolTips(){
        Tooltip tltpMyWork = new Tooltip("Mi trabajo recepcional");
        Tooltip tltpProjects = new Tooltip("Anteproyectos");
        Tooltip tltpHome = new Tooltip("Página principal");
        
        Tooltip.install(imgMyWork, tltpMyWork);
        Tooltip.install(imgProjects, tltpProjects);
        Tooltip.install(imgHome, tltpHome);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setToolTips();
    }    
    
}
