package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class FieldValidation {
    
    public static final int PASSWORD_ACCEPTABLE_LENGTH =8;
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d\\W]+$";
    
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
    
    public static boolean isNullOrEmptyTxtField(TextField textField) {
       return textField == null || textField.getText().trim().isEmpty();
    }
    
    public static boolean isChoiceBoxSelected(ChoiceBox<String> choiceBox) {
        return choiceBox.getValue() != null && !choiceBox.getValue().isEmpty();
    }
    
}
