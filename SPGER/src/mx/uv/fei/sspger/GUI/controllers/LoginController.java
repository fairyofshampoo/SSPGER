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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.UserDAO;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.User;
import mx.uv.fei.sspger.logic.UserSession;
import mx.uv.fei.sspger.logic.UserTypes;


public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgLogin;

    @FXML
    private Label lblWrongEMail;

    @FXML
    private Label lblWrongPassword;

    @FXML
    private TextField txtEMail;

    @FXML
    private PasswordField txtPassword;
    
    private final int ERROR = -1;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    static UserSession userSession = UserSession.getInstance();

    @FXML
    void logIn(MouseEvent event) {
        if(verifyFields()){
            UserDAO userDAO = new UserDAO();
            try {
                int userExistence = userDAO.login(txtEMail.getText(), txtPassword.getText());
                continueLogin(userExistence == 1);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage (
                "Error de conexión a la base de datos",
                Status.FATAL));
            }
            
        }
    }
    
    private void continueLogin(boolean isLoginCorrect){
        if(isLoginCorrect){
            displayView();
        } else {
            DialogGenerator.getDialog(new AlertMessage (
         "Datos de acceso incorrectos.",
         Status.ERROR));
        }
    }
    
    @FXML
    void typingEMail(KeyEvent event) {
        lblWrongEMail.setVisible(false);
    }

    @FXML
    void typingPassword(KeyEvent event) {
        lblWrongPassword.setVisible(false);
    }
    
    private boolean verifyFields(){
        boolean validation = true;
        User user = new User();
        try{
            user.setEMail(txtEMail.getText());
        }
        catch(IllegalArgumentException eMailException){
            lblWrongEMail.setVisible(true);
            lblWrongEMail.setText("Correo inválido");
            validation = false;
        }
        finally{
            try{
                user.setPassword(txtPassword.getText());
            }
            catch(IllegalArgumentException passwordException){
                lblWrongPassword.setVisible(true);
                lblWrongPassword.setText("Contraseña inválida");
                validation = false;
            }
        }
        
        return validation;
    }
    
    private int getUserId(String email, String userType){
        int idUser = ERROR;
        UserDAO userDAO = new UserDAO();
        if(userType.equals(STUDENT_TYPE.getDisplayName())){
            try {
                idUser = userDAO.isStudent(email);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos",Status.ERROR));
            }
        }else{
            try {
                idUser = userDAO.isProfessor(email);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos",Status.ERROR));
            }
        }
        return idUser;
        
    }
    
    private String determinateUserType(String email){
        String userType = "";
        try {
            UserDAO userDAO = new UserDAO();
            if(userDAO.isStudent(email) != ERROR){
                userType = STUDENT_TYPE.getDisplayName();
            } else{
                userType = PROFESSOR_TYPE.getDisplayName();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos",Status.ERROR));
        }
        
        return userType;
    }
    
    private int getProfessorPrivileges(int idProfessor){
        UserDAO userDAO = new UserDAO();
        int privileges = ERROR;
        try {
            privileges = userDAO.professorPrivileges(idProfessor);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos",Status.ERROR));
        }
        return privileges;
    }
    
    private void displayImages(){
        imgLogin.setImage(ImagesSetter.getLoginImage());
    }
    
    private void displayView(){
        userSession.setUserType(determinateUserType(txtEMail.getText()));
        int idUser = getUserId(txtEMail.getText(), userSession.getUserType());
        userSession.setUserId(idUser);
        if(userSession.getUserType().equals(PROFESSOR_TYPE.getDisplayName())){
            userSession.setPrivileges(getProfessorPrivileges(idUser));
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/HomeProfessor.fxml");
        } else {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/HomeStudent.fxml");
        }
    }
    private void isAnyUserAvailable(){
        try {
            UserDAO userDAO = new UserDAO();
            if(!userDAO.usersAvailables()){
                disableComponents();
                DialogGenerator.getDialog(new AlertMessage ("No hay usuarios registrados para iniciar sesión",Status.WARNING));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos",Status.ERROR));
            disableComponents();
        }
    }
    private void disableComponents(){
        txtEMail.setDisable(true);
        txtPassword.setDisable(true);
        btnLogin.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        isAnyUserAvailable();
    }    
    
}
