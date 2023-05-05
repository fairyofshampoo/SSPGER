package mx.uv.fei.sspger.GUI.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mx.uv.fei.sspger.logic.Student;

public class StudentCardForProfessorController{
    
    @FXML
    private ImageView imgStudent;

    @FXML
    private Label lblStudentEMail;

    @FXML
    private Label lblStudentName;

    @FXML
    void seeStudentDetails(ActionEvent event) {

    }
    
    public void setStudentData (Student student){
        imgStudent.setImage(ImagesSetter.getUserIconImage());
        lblStudentName.setText(student.getName() + " " + student.getLastName());
        lblStudentEMail.setText(student.getEMail());
    }    
    
}
