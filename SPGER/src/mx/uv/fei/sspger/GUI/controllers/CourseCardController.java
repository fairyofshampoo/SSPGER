package mx.uv.fei.sspger.GUI.controllers;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;


public class CourseCardController {
    
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private String courseId;
    
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
        
    }
    
    public void setCourseData(Course course){
        courseId = course.getCourseId();
        lblCourseTitle.setText(course.getName());
        lblNrc.setText("NRC: " + course.getNrc());
        lblSection.setText("Sección: " + course.getSection());
        lblBlock.setText("Bloque: " + course.getBlock());
        lblSemester.setText("Periodo: " + getSemesterString(course.getSemesterId()));
    }

    private String getSemesterString(int semesterId) {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = new Semester();
        
        try{
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException SqlException){
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema.",
                Status.FATAL));
        }
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);
        
        return semesterDate;
    }
    
}
