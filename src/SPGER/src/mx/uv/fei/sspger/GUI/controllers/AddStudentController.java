package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import mx.uv.fei.sspger.logic.Student;

public class AddStudentController implements Initializable {

    private static String courseId;

    @FXML
    private TableColumn<TableStudentsPerCourse, CheckBox> tblCCheckBox;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCRegistrationTag;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCStudentName;

    @FXML
    private TableView<TableStudentsPerCourse> tblVwStudents;

    ObservableList<TableStudentsPerCourse> list = FXCollections.observableArrayList();

    public static void setCourseId(String courseId) {
        AddStudentController.courseId = courseId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setStudentTable();
    }

    @FXML
    void addStudent(ActionEvent event) {
        try {
            CourseDAO courseDao = new CourseDAO();
            List<EnrollToCourse> studentsToEnroll = getEnrolledStudentList();

            for (EnrollToCourse studentToEnroll : studentsToEnroll) {
                courseDao.enrollStudentToCourse(studentToEnroll);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        Stage stage = (Stage) tblVwStudents.getScene().getWindow();
        stage.close();
    }

    @FXML
    void cancelAddition(ActionEvent event) {
        Stage stage = (Stage) tblVwStudents.getScene().getWindow();
        stage.close();
    }

    private void setStudentTable() {
        StudentDAO studentDao = new StudentDAO();
        List<Student> availableStudents = new ArrayList<>();

        try {
            availableStudents = studentDao.getAvailableStudentsNotInCourse();
        } catch (SQLException sqlException) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        for (int quantityOfStudents = 0; quantityOfStudents < availableStudents.size(); quantityOfStudents++) {
            CheckBox checkBoxTable = new CheckBox("" + (availableStudents.get(quantityOfStudents).getId()));
            String studentFullName = availableStudents.get(quantityOfStudents).getName() + " "
                    + availableStudents.get(quantityOfStudents).getLastName();

            list.add(new TableStudentsPerCourse(
                    availableStudents.get(quantityOfStudents).getRegistrationTag(),
                    studentFullName, checkBoxTable));
        }

        tblVwStudents.setItems(list);
        tblCRegistrationTag.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("registrationTag"));
        tblCStudentName.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentName"));
        tblCCheckBox.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, CheckBox>("checkBox"));
    }

    private List<EnrollToCourse> getEnrolledStudentList() throws SQLException {
        List<EnrollToCourse> studentsToEnroll = new ArrayList<>();

        for (int counter = 0; counter < tblVwStudents.getItems().size(); counter++) {

            if (tblVwStudents.getItems().get(counter).getCheckBox().isSelected()) {
                EnrollToCourse enrollToCourse = new EnrollToCourse();
                int studentId = Integer.parseInt(tblVwStudents.getItems().get(counter).getCheckBox().getText());
                enrollToCourse.setStudentId(studentId);
                enrollToCourse.setCourseId(courseId);

                studentsToEnroll.add(enrollToCourse);
            }
        }
        return studentsToEnroll;
    }
}
