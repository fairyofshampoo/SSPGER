package mx.uv.fei.sspger.GUI.controllers;


import javafx.scene.control.TextField;


public class FieldValidation {
    
    public static final int PASSWORD_ACCEPTABLE_LENGTH =8;
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]+$";
    public static final String EMAIL_REGEX = "^(?=.{1,256}$)[^\\s@]+@(?:uv\\.mx|estudiantes\\.uv\\.mx|gmail\\.com|hotmail\\.com|outlook\\.com|edu\\.mx)$";
    
    public static boolean isPasswordValid(String password) {
        boolean validPassword = false;
    
        if (password.length() >= PASSWORD_ACCEPTABLE_LENGTH) {
            validPassword = true;
        }
    
        if (password.matches(PASSWORD_REGEX)) {
            validPassword = true;
        }
        
        return validPassword;
    }
    public static boolean isEMailValid(String email){
        boolean validEMail = false;
        if(email.matches(EMAIL_REGEX)){
            validEMail = true;
        }
        return validEMail;
    }
    
    public static boolean isNullOrEmptyTxtField(TextField textField) {
       return textField == null || textField.getText().trim().isEmpty();
    }
    
}
