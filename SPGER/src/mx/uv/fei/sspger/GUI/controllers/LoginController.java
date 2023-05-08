package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;


public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private ImageView imgLogin;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    private Label lblWrongEMail;

    @FXML
    private Label lblWrongPassword;

    @FXML
    private TextField txtEMail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void logIn(ActionEvent event) {
        String password = txtPassword.getText();
        String email = txtEMail.getText();
    }
    
    @FXML
    void typingEMail(KeyEvent event) {
        lblWrongEMail.setVisible(false);
    }

    @FXML
    void typingPassword(KeyEvent event) {
        lblWrongPassword.setVisible(false);
    }
    
    void isEmptyField(){
        if (FieldValidation.isNullOrEmptyTxtField(txtEMail)){
            lblWrongEMail.setVisible(true);
            lblWrongEMail.setText("Campo de correo vacío");
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtPassword)){
            lblWrongPassword.setVisible(true);
            lblWrongPassword.setText("Campo de contraseña vacío");
        }
    }
    
    void determinateUserType(){
    
    }
    
    void emailVerification(){
        
    }
    void wrongPassword(){
        
    }
    
    private void displayImages(){
        
        imgLogin.setImage(ImagesSetter.getLoginImage());
        
    }
    private void isAnyUserAvailable(){
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
    }    
    
}
