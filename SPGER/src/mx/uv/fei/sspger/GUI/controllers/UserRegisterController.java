package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.GUI.controllers.AlertMessage;
import mx.uv.fei.sspger.GUI.controllers.DialogGenerator;
import mx.uv.fei.sspger.GUI.controllers.FieldValidation;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.UserTypes;
import mx.uv.fei.sspger.logic.HonorificTitles;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;


public class UserRegisterController implements Initializable {

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<String> cbxHonorificTitle;

    @FXML
    private ChoiceBox<String> cbxUserType;

    @FXML
    private Label lblAddUser;

    @FXML
    private Label lblEMail;

    @FXML
    private Label lblHonorificTitle;

    @FXML
    private Label lblIdUser;

    @FXML
    private Label lblInstruction;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUserType;

    @FXML
    private TextField txtEMail;

    @FXML
    private TextField txtIdUser;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;
    
    @FXML
    private Label lblInvalidEMail;

    @FXML
    private Label lblInvalidHonorificTitle;

    @FXML
    private Label lblInvalidId;

    @FXML
    private Label lblInvalidLastName;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblInvalidPassword;

    @FXML
    private Label lblInvalidUserType;
    
    @FXML
    void acceptButtonClick(ActionEvent event){
        if(!isEmptyField()){
                if("Estudiante".equals(cbxUserType.getValue())){
                    studentRegister();
                } else{
                    professorRegister();
                }
        }
    }
    
    @FXML
    private void disableHonorificTitleSelection(MouseEvent event){
        if("Estudiante".equals(cbxUserType.getValue())){
            cbxHonorificTitle.setDisable(true);
        }else{
            cbxHonorificTitle.setDisable(false);
        }
    }
    
    private void professorRegister(){
        Professor professor = new Professor();
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        try{
            professor.setEMail(txtEMail.getText());
            professor.setName(txtName.getText());
            professor.setLastName(txtLastName.getText());
            professor.setPassword(txtPassword.getText());
            professor.setPersonalNumber(txtIdUser.getText());
            professor.setHonorificTitle(cbxHonorificTitle.getValue());
            professor.setEMail(txtEMail.getText());
            professor.setPassword(txtPassword.getText());
            professor.setStatus(1);
            professor.setIsAdmin(0);
                        
            if(professorDAO.addProfessorTransaction(professor) == 1){
                DialogGenerator.getDialog(new AlertMessage (
                "Profesor registrado con éxito",
                Status.SUCCESS));
            }
        } catch (SQLException ex){
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void studentRegister(){
        try {
            Student student = new Student();
            StudentDAO accessAccountDAO = new StudentDAO();
            student.setEMail(txtEMail.getText());
            student.setName(txtName.getText());
            student.setLastName(txtLastName.getText());
            student.setPassword(txtPassword.getText());
            student.setRegistrationTag(txtIdUser.getText());
            student.setEMail(txtEMail.getText());
            student.setPassword(txtPassword.getText());
                        
            if(accessAccountDAO.addStudentTransaction(student) == 1){
                DialogGenerator.getDialog(new AlertMessage (
                "Estudiante registrado con éxito",
                Status.SUCCESS));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isEmptyField() {
        return !FieldValidation.isChoiceBoxSelected(cbxUserType)
        || FieldValidation.isNullOrEmptyTxtField(txtEMail) || 
        FieldValidation.isNullOrEmptyTxtField(txtName) ||
        FieldValidation.isNullOrEmptyTxtField(txtIdUser) || 
        FieldValidation.isNullOrEmptyTxtField(txtLastName)
        || FieldValidation.isNullOrEmptyTxtField(txtPassword);
    }
    
    
    
    private void setUserTypeComboBox(){
        for (UserTypes userType : UserTypes.values()) {
        cbxUserType.getItems().add(userType.getDisplayName());
        }
    }
    
    private void setHonorificTitleComboBox(){
        for (HonorificTitles honorificTitles : HonorificTitles.values()) {
        cbxHonorificTitle.getItems().add(honorificTitles.getDisplayName());
        }
    }
    
    @FXML
    void cancelButtonClick(ActionEvent event){
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserTypeComboBox();
        setHonorificTitleComboBox();
    }

}