package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mx.uv.fei.sspger.logic.Student;


public class StudentPostulatedCardController implements Initializable {
    @FXML
    private CheckBox chkSelect;
    
    @FXML
    private ImageView imgUserIcon;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setStudent(Student student){
        lblName.setText(student.getName() + " " + student.getLastName());
        lblEmail.setText(student.getEMail());
    }
    
    public boolean isSelected(){
        return chkSelect.isSelected();
    }
    
    public String getLblEmail(){
        return lblEmail.getText();
    }
    
    public void setSelection(boolean isSelected){
        chkSelect.setSelected(isSelected);
    }
}
