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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.DAO.AccessAccountDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.UserTypes;
import mx.uv.fei.sspger.logic.HonorificTitles;
import mx.uv.fei.sspger.logic.Professor;
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
    void acceptButtonClick(ActionEvent event){
        if(!isEmptyField()){
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
        if("Estudiante".equals(cbxUserType.getValue())){
            cbxHonorificTitle.setDisable(true);
        }else{
            cbxHonorificTitle.setDisable(false);
        }
    }
    
    private void professorRegister(){
        Professor professor = new Professor();
        AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
        
        try{
            professor.setEMail(txtEMail.getText());
            professor.setName(txtName.getText());
            professor.setLastName(txtLastName.getText());
            professor.setPassword(txtPassword.getText());
            professor.setPersonalNumber(txtIdUser.getText());
            professor.setHonorificTitle(cbxHonorificTitle.getValue());
                        
            AccessAccount accessAccount = new AccessAccount();
            accessAccount.setEMail(txtEMail.getText());
            accessAccount.setPassword(txtPassword.getText());
            accessAccount.setUserStatus(1);
                        
            if(accessAccountDAO.addAccessAccount(accessAccount) == 1){
                ProfessorDAO professorDAO = new ProfessorDAO();
                if(professorDAO.addProfessor(professor) == 1){
                    JOptionPane.showMessageDialog(null, "Profesor Registrado con éxito",
                    "Registro exitoso", JOptionPane.INFORMATION_MESSAGE); 
                }
            }
        } catch (SQLException ex){
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void studentRegister(){
        try {
            Student student = new Student();
            AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
            student.setEMail(txtEMail.getText());
            student.setName(txtName.getText());
            student.setLastName(txtLastName.getText());
            student.setPassword(txtPassword.getText());
            student.setRegistrationTag(txtIdUser.getText());
                        
            AccessAccount accessAccount = new AccessAccount();
            accessAccount.setEMail(txtEMail.getText());
            accessAccount.setPassword(txtPassword.getText());
                        
            if(accessAccountDAO.addAccessAccount(accessAccount) == 1){
                StudentDAO studentDAO = new StudentDAO();
                if(studentDAO.addStudent(student) == 1){
                    JOptionPane.showMessageDialog(null, "Estudiante Registrado con éxito",
                            "Registro exitoso", JOptionPane.INFORMATION_MESSAGE); 
                }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/sspger/GUI/UsersManager.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage myStage = (Stage) this.btnCancel.getScene().getWindow();
            myStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserTypeComboBox();
        setHonorificTitleComboBox();
    }

}
