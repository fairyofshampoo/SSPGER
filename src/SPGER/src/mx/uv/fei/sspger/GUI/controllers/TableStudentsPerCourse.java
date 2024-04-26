package mx.uv.fei.sspger.GUI.controllers;

import javafx.scene.control.CheckBox;

public class TableStudentsPerCourse {

    String registrationTag;
    String studentName;
    String studentEmail;
    CheckBox chkBox;

    public TableStudentsPerCourse(String registrationTag, String studentName, CheckBox checkBox) {
        this.registrationTag = registrationTag;
        this.studentName = studentName;
        this.chkBox = checkBox;
    }

    public TableStudentsPerCourse(String registrationTag, String studentName, String studentEmail) {
        this.registrationTag = registrationTag;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public String getRegistrationTag() {
        return registrationTag;
    }

    public void setRegistrationTag(String registrationTag) {
        this.registrationTag = registrationTag;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public CheckBox getCheckBox() {
        return chkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.chkBox = checkBox;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
}
