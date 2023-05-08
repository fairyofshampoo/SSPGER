package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mx.uv.fei.sspger.GUI.MainApplication;
import mx.uv.fei.sspger.logic.Student;

public class StudentCardForProfessorController{
    private Student student;
    
    @FXML
    private ImageView imgStudent;

    @FXML
    private Label lblStudentEMail;

    @FXML
    private Label lblStudentName;

    @FXML
    void seeStudentDetails(ActionEvent event) {
        
        ViewProjectForProfessorController.student = student;
        try {
            MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ViewProjectForProfessor");
        } catch (IOException ex) {
            Logger.getLogger(StudentCardForProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setStudentData (Student student){
        this.student = student;
        imgStudent.setImage(ImagesSetter.getUserIconImage());
        lblStudentName.setText(student.getName() + " " + student.getLastName());
        lblStudentEMail.setText(student.getEMail());
    }    
    
}
