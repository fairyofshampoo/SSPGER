package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class ViewCourseController implements Initializable {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static String idCourse;

    public static void setIdCourse(String idCourse) {
        ViewCourseController.idCourse = idCourse;
    }

    @FXML
    private Button btnDisable;

    @FXML
    private Button btnModify;

    @FXML
    private Label lblBlock;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblProfessor;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblSemester;

    @FXML
    private Label lblCourseName;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCEmail;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCRegistrationTag;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCStudentName;

    @FXML
    private TableView<TableStudentsPerCourse> tblVwStudents;

    ObservableList<TableStudentsPerCourse> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
        setTable();
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        SPGER.setRoot("CourseManagement.fxml");
    }

    public void disableCourse(ActionEvent actionEvent) {
        if (isConfirmedExit()) {
            disable();
        }
    }

    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Esta seguro que desea eliminar este curso? El cambio es irreversible.");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void disable() {
        try {
            CourseDAO courseDao = new CourseDAO();
            if (courseDao.disableCourse(idCourse) == 1) {
                DialogGenerator.getDialog(new AlertMessage(
                        "El curso ha sido desactivado con éxito",
                        Status.SUCCESS));
                SPGER.setRoot("CourseManagement.fxml");
            } else {
                DialogGenerator.getDialog(new AlertMessage(
                        "El curso no pudo desactivarse.",
                        Status.ERROR));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewCourseController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
    }

    public void modifyCourse(ActionEvent actionEvent) throws IOException {
        ModifyCourseController.setCourseId(idCourse);
        SPGER.setRoot("ModifyCourse.fxml");
    }

    public void setTable() {
        StudentDAO studentDao = new StudentDAO();
        List<Student> courseStudents = new ArrayList<>();

        try {
            courseStudents = studentDao.getStudentsPerCourse(idCourse);
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        for (int quantityOfStudents = 0; quantityOfStudents < courseStudents.size(); quantityOfStudents++) {
            String studentFullName = courseStudents.get(quantityOfStudents).getName() + " "
                    + courseStudents.get(quantityOfStudents).getLastName();

            list.add(new TableStudentsPerCourse(courseStudents.get(quantityOfStudents).getRegistrationTag(),
                    studentFullName,
                    courseStudents.get(quantityOfStudents).getEMail()));
        }

        tblVwStudents.setItems(list);
        tblCRegistrationTag.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("registrationTag"));
        tblCStudentName.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentName"));
        tblCEmail.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentEmail"));
    }

    public void setGraphicElements() {
        Course course = new Course();
        CourseDAO courseDao = new CourseDAO();

        try {
            course = courseDao.getCourseById(idCourse);
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
            DialogGenerator.getDialog(new AlertMessage(
                    "Error en la conexion al sistema.",
                    Status.FATAL));
        }

        setModifyAndDisableButton(course.getState());
        lblBlock.setText("" + course.getBlock());
        lblSection.setText("" + course.getSection());
        lblCourseName.setText("Curso: " + course.getName());
        lblNrc.setText(course.getNrc());
        lblSemester.setText(getSemesterDate(course.getSemesterId()));
        lblProfessor.setText(getProfessor(course.getCourseId()));
    }

    public String getSemesterDate(int semesterId) {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = new Semester();

        try {
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);

        return semesterDate;
    }

    public String getProfessor(String courseId) {
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = new Professor();

        try {
            professor = professorDao.getProfessorByCourse(this.idCourse);
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        String professorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();

        return professorName;
    }

    private void setModifyAndDisableButton(String state) {
        CourseStates courseState = CourseStates.INACTIVE;

        if (courseState.getCourseState().equals(state)) {
            btnModify.setDisable(true);
            btnDisable.setDisable(true);
        }
    }
}
