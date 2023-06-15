package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.GUI.controllers.UserDetailsController;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.UserTypes;


public class UsersCardController{
    @FXML
    private HBox boxUser;
    
    @FXML
    private ImageView imgUserIcon;

    @FXML
    private Label lblEMail;

    @FXML
    private Label lblFullNameUser;

    @FXML
    private Label lblTagOrNumber;

    @FXML
    private Label lblUserType;
    
    @FXML
    private Pane pnUsersCard;
    
    private int idUser;
    private String userType = "Estudiante";
    
    public void setUserStudentData(Student student){
        if(student != null){
            lblFullNameUser.setText(student.getName() + " " + student.getLastName());
            lblEMail.setText(student.getEMail());
            lblUserType.setText("Estudiante");
            lblTagOrNumber.setText(student.getRegistrationTag());
            idUser = student.getId();
            userType = UserTypes.STUDENT.getDisplayName();
        }
    }
    
    public void setUserProfessorData(Professor professor){
        if(professor != null){
            lblFullNameUser.setText(professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName());
            lblEMail.setText(professor.getEMail());
            lblUserType.setText("Profesor");
            lblTagOrNumber.setText(professor.getPersonalNumber());
            idUser = professor.getId();
            userType = UserTypes.PROFESSOR.getDisplayName();
        }
    }
    
    @FXML
    void selectUser(MouseEvent event) {
        UserDetailsController.idUser = idUser;
        UserDetailsController.userType = userType;
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/UserDetails.fxml");
    }
    
    private String getUserEMail(){
        
        return lblEMail.getText();
    }
}
