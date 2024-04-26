package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Semester;

public class CourseCardForStudentController {

    @FXML
    private AnchorPane apCourseCard;

    @FXML
    private Label lblCourseTitle;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblBlock;

    @FXML
    private Label lblSemester;

    @FXML
    private Label lblState;

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public void setCourseData(Course course) {
        lblCourseTitle.setText(course.getName());
        lblNrc.setText("NRC: " + course.getNrc());
        lblSection.setText("Sección: " + course.getSection());
        lblBlock.setText("Bloque: " + course.getBlock());
        lblSemester.setText("Periodo: " + getSemesterString(course.getSemesterId()));
        lblState.setText("Estado: " + course.getState());
    }

    private String getSemesterString(int semesterId) {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = new Semester();

        try {
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException sqlException) {
            Logger.getLogger(CourseCardForStudentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);

        return semesterDate;
    }

}
