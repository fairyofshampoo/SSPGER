package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.Status;

public class HomeStudentController implements Initializable {
    @FXML
    private AnchorPane coursesCard;

    @FXML
    private HBox coursesCardLayout;
    @FXML
    private Label lblSeeMyWork;
    @FXML
    private Pane pnNavigationBarStudent;

    @FXML
    void displayMyWorkView(MouseEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNavigationBar();
    }
    
    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();
        
            pnNavigationBarStudent.getChildren().add(pnNavigationBarParent);
        } catch (IOException ex) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, ex);
            showFXMLFileFailedAlert();
        }
    }
    
    private void showFXMLFileFailedAlert(){
        DialogGenerator.getDialog(new AlertMessage ("Archivo FXML corrupto.",Status.FATAL));
    }
    
}
