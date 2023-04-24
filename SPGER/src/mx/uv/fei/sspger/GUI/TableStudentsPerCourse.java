package mx.uv.fei.sspger.GUI;


import javafx.scene.control.CheckBox;


public class TableStudentsPerCourse {
    
    String registrationTag;
    String studentName;
    CheckBox checkBox;

    public TableStudentsPerCourse(String registrationTag, String studentName, CheckBox checkBox) {
        this.registrationTag = registrationTag;
        this.studentName = studentName;
        this.checkBox = checkBox;
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
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
