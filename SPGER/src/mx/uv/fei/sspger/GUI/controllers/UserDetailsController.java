package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.UserTypes;

public class UserDetailsController implements Initializable {
    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblEMail;

    @FXML
    private Label lblHonorificTitle;

    @FXML
    private Label lblIdUser;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblName;

    @FXML
    private Label lblUserType;
    
    @FXML
    private Label lblPrincipalHonorificTitle;
    
    public static int idUser;
    public static String userType;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    
    @FXML
    void goBack(MouseEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
    }
    
    private void setUserData(){
        if(userType.equals(STUDENT_TYPE.getDisplayName())){
            try {
                lblPrincipalHonorificTitle.setVisible(false);
                lblHonorificTitle.setVisible(false);
                StudentDAO studentDao = new StudentDAO();
                Student student = studentDao.getStudent(idUser);
                lblName.setText(student.getName());
                lblIdUser.setText(student.getRegistrationTag());
                lblLastName.setText(student.getLastName());
                lblEMail.setText(student.getEMail());
            } catch (SQLException ex) {
                Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                ProfessorDAO professorDao = new ProfessorDAO();
                Professor professor = professorDao.getProfessor(idUser);
                lblName.setText(professor.getName());
                lblIdUser.setText(professor.getPersonalNumber());
                lblLastName.setText(professor.getLastName());
                lblHonorificTitle.setText(professor.getHonorificTitle());
                lblEMail.setText(professor.getEMail());
            } catch (SQLException ex) {
                Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUserType.setText(userType);
        setUserData();
    }    
    
}