package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Semester;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseCardController {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private Course courseCard;

    @FXML
    private AnchorPane apCourseCard;

    @FXML
    private Label lblCourseTitle;

    @FXML
    private Label lblSemester;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblBlock;

    @FXML
    private Label lblSection;

    @FXML
    void openProfessorCourseView(MouseEvent event) {
        ProfessorCourseViewController.course = courseCard;
        SPGER.setRoot("ProfessorCourseView.fxml");
    }

    @FXML
    void mouseEnteredCourseArea(MouseEvent event) {
        apCourseCard.setCursor(Cursor.HAND);
    }

    @FXML
    void mouseExitedCourseArea(MouseEvent event) {
        apCourseCard.setCursor(Cursor.DEFAULT);
    }

    public void setCourseData(Course course) {
        courseCard = course;
        lblCourseTitle.setText(course.getName());
        lblNrc.setText("NRC: " + course.getNrc());
        lblSection.setText("Sección: " + course.getSection());
        lblBlock.setText("Bloque: " + course.getBlock());
        lblSemester.setText("Periodo: " + getSemesterString(course.getSemesterId()));
    }

    private String getSemesterString(int semesterId) {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = new Semester();

        try {
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException sqlException) {
            FailAlert.showFailedConnectionAlert();
            Logger.getLogger(CourseCardController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);

        return semesterDate;
    }

}
