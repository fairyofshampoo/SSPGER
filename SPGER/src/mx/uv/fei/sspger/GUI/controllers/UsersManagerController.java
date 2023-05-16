package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;


public class UsersManagerController implements Initializable {

    @FXML
    private Button btnAddUser;
    
    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgSearchBar;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    private TextField txtSearchBar;
    
    @FXML
    void addUserButtonClicked(ActionEvent event){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UserRegister.fxml");
        } catch (IOException ex) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void homeClicked(MouseEvent mouseEvent){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/HomeProfessor.fxml");
        } catch (IOException ex) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
    } 
    
    public void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());
        imgSearchBar.setImage(ImagesSetter.getSearchBarImage());
        
    }
    
}

