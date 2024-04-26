package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mx.uv.fei.sspger.logic.Student;

public class StudentPostulatedCardController {

    @FXML
    private CheckBox chkSelect;

    @FXML
    private ImageView imgUserIcon;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    private int idStudent;

    public void setStudent(Student student) {
        lblName.setText(student.getName() + " " + student.getLastName());
        lblEmail.setText(student.getEMail());
        idStudent = student.getId();
    }

    public boolean isSelected() {
        return chkSelect.isSelected();
    }

    public String getLblEmail() {
        return lblEmail.getText();
    }

    public void setSelection(boolean isSelected) {
        chkSelect.setSelected(isSelected);
    }

    public int getIdStudent() {
        return idStudent;
    }
}
