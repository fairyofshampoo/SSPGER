package mx.uv.fei.sspger.GUI;

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
import javax.swing.JOptionPane;
import mx.uv.fei.sspger.logic.AccessAccount;
import mx.uv.fei.sspger.logic.DAO.AccessAccountDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.UserTypes;
import mx.uv.fei.sspger.logic.HonorificTitles;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;


public class UserModifierController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

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
    void saveButtonClick(ActionEvent event) {
        if(!isEmptyField()){
            if(isPasswordValid()){
                if("Estudiante".equals(cbxUserType.getValue())){
                    Student student = new Student();
                    AccessAccountDAO accessAccountDAO = new AccessAccountDAO();
                    try{
                        student.setEMail(txtEMail.getText());
                        student.setName(txtName.getText());
                        student.setLastName(txtLastName.getText());
                        student.setPassword(txtPassword.getText());
                        student.setRegistrationTag(txtIdUser.getText());
                        
                        AccessAccount accessAccount = new AccessAccount();
                        accessAccount.setEMail(txtEMail.getText());
                        accessAccount.setPassword(txtPassword.getText());
                        
                        if(accessAccountDAO.updateAccessAccount(txtEMail.getText(), accessAccount) == 1){
                            StudentDAO studentDAO = new StudentDAO();
                            if(studentDAO.updateStudent(txtEMail.getText(), student) == 1){
                                JOptionPane.showMessageDialog(null, "Estudiante modificado con éxito",
                                        "Registro exitoso", JOptionPane.INFORMATION_MESSAGE); 
                            }
                        }
                    } catch (SQLException SQLException){
                        
                    }
                } else{
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
                        
                        if(accessAccountDAO.updateAccessAccount(txtEMail.getText(), accessAccount) == 1){
                            ProfessorDAO professorDAO = new ProfessorDAO();
                            if(professorDAO.updateProfessor(txtEMail.getText(), professor) == 1){
                                JOptionPane.showMessageDialog(null, "Profesor modificado con éxito",
                                        "Registro exitoso", JOptionPane.INFORMATION_MESSAGE); 
                            }
                        }
                    } catch (SQLException SQLException){
                        
                    }
                }
                
            }
        }
    }
    
    private boolean isEmptyField() {
        return cbxUserType.getValue() == null
        || txtEMail.getText().isEmpty() || txtName.getText().isEmpty()
        || txtIdUser.getText().isEmpty() || txtLastName.getText().isEmpty()
        || txtPassword.getText().isEmpty();
    }
    
    private boolean isPasswordValid() {
        String password = txtPassword.getText();
        boolean validPassword = false;
    
        if (password.length() >= 8) {
            validPassword = true;
        }
    
        if (password.matches(".*\\d+.*")) {
            validPassword = true;
        }
        
        if (password.matches(".*[a-zA-Z].*") || password.matches(".*\\d.*")) {
            validPassword = true;
        }
        return validPassword;
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


