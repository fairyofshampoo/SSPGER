package mx.uv.fei.sspger.GUI.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;


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
    
    public static int idUser;
    
    public void setUserStudentData(Student student){
        lblFullNameUser.setText(student.getName() + student.getLastName());
        lblEMail.setText(student.getEMail());
        lblUserType.setText("Estudiante");
        lblTagOrNumber.setText(student.getRegistrationTag());
    }
    
    public void setUserProfessorData(Professor professor){
        lblFullNameUser.setText(professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName());
        lblEMail.setText(professor.getEMail());
        lblUserType.setText("Profesor");
        lblTagOrNumber.setText(professor.getPersonalNumber());
    }
    
    @FXML
    void selectUser(MouseEvent event) {

    }
    
}
