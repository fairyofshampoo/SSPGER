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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
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
    private Label lblInvalidEMail;

    @FXML
    private Label lblInvalidHonorificTitle;

    @FXML
    private Label lblInvalidIdUser;

    @FXML
    private Label lblInvalidLastName;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblInvalidPassword;

    @FXML
    private Label lblInvalidUserType;

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
    void acceptButtonClick(ActionEvent event){
        if(validateFields()){
            if(FieldValidation.isPasswordValid(txtPassword.getText())){
                if("Estudiante".equals(cbxUserType.getValue())){
                    studentRegister();
                } else{
                    professorRegister();
                }
            }
        }
    }
    
    @FXML
    void userTypeChoiceSelector(MouseEvent event){
        lblInvalidUserType.setVisible(false);
        if("Estudiante".equals(cbxUserType.getValue())){
            cbxHonorificTitle.setDisable(true);
        }else{
            cbxHonorificTitle.setDisable(false);
        }
    }
    
        
    @FXML
    void cancelButtonClick(ActionEvent event){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
        } catch (IOException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void clickHonorificTitle(MouseEvent event) {
        lblInvalidHonorificTitle.setVisible(false);
    }

    @FXML
    void typingEMail(KeyEvent event) {
        lblInvalidEMail.setVisible(false);
    }

    @FXML
    void typingIdUser(KeyEvent event) {
        lblInvalidIdUser.setVisible(false);
    }

    @FXML
    void typingLastName(KeyEvent event) {
        lblInvalidLastName.setVisible(false);
    }

    @FXML
    void typingName(KeyEvent event) {
        lblInvalidName.setVisible(false);
    }

    @FXML
    void typingPassword(KeyEvent event) {
        lblInvalidPassword.setVisible(false);
    }
    
    private void professorRegister(){
        if(cbxHonorificTitle.getSelectionModel().isEmpty()){
            lblInvalidHonorificTitle.setVisible(true);
        }
        else{
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
                        
                if(professorDAO.addProfessorTransaction(professor) == 2){
                    DialogGenerator.getDialog(new AlertMessage (
                    "Profesor registrado con éxito",
                    Status.SUCCESS));
                    clearView();
                }
            } catch (SQLException ex){
                Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                        
            if(accessAccountDAO.addStudentTransaction(student) == 2){
                DialogGenerator.getDialog(new AlertMessage (
                "Estudiante registrado con éxito",
                Status.SUCCESS));
                
                clearView();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void clearView(){
        txtName.clear();
        txtLastName.clear();
        txtEMail.clear();
        txtPassword.clear();
        txtIdUser.clear();
        cbxUserType.getSelectionModel().clearSelection();
        cbxHonorificTitle.getSelectionModel().clearSelection();
        cbxHonorificTitle.setDisable(false);
        
        
    }
    
    private boolean validateFields() {
        boolean validation = true;
        if(!FieldValidation.isEMailValid(txtEMail.getText())){
            validation = false;
            lblInvalidEMail.setVisible(true);
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtName)){
            validation = false;
            lblInvalidName.setVisible(true);
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtLastName)){
            validation = false;
            lblInvalidLastName.setVisible(true);
        }
        if(cbxUserType.getSelectionModel().isEmpty()){
            validation = false;
            lblInvalidUserType.setVisible(true);
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtIdUser)){
            validation = false;
            lblInvalidIdUser.setVisible(true);
        }
        if(!FieldValidation.isPasswordValid(txtPassword.getText())){
            validation = false;
            lblInvalidPassword.setVisible(true);
        }
        return validation;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserTypeComboBox();
        setHonorificTitleComboBox();
    }

}