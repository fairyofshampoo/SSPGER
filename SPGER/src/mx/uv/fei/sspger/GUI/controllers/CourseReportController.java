package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Student;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.GUI.AlertMessage;
import mx.uv.fei.sspger.GUI.DialogGenerator;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;


public class CourseReportController implements Initializable {
    public static String courseId;
    private int column = 0;
    private int row = 1;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private GridPane gpStudents;

    @FXML
    private Label lblBlock;

    @FXML
    private Label lblGenerationDate;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblSemester;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentDAO studentDao = new StudentDAO();
        CourseDAO courseDao = new CourseDAO();
        
        List<Student> courseStudents = new ArrayList<>(); 
        
        try{
            Course course = courseDao.getCourseByID(courseId);
            courseStudents = studentDao.getStudentsPerCourse(courseId);
            
            setGraphicElements(course);
            
            for (Student student : courseStudents){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentReportCard.fxml"));                
                Pane studentReportCard = fxmlLoader.load();
                StudentReportCardController studentReportCardController = fxmlLoader.getController();
                studentReportCardController.setStudentReportCard(student.getId());
                
                if (column == 4){
                    column = 0;
                    row++;
                }
                
                gpStudents.add(studentReportCard, column++, row);
                GridPane.setMargin(studentReportCard, new Insets(10));
            }
        } catch (SQLException | IOException exception){
            Logger.getLogger(CourseReportController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }    

    private void setGraphicElements(Course course) throws SQLException{
        String semesterDate = getSemesterDate(course.getSemesterId());
        Date currentDate = new Date();
        String currentDateString = DATE_FORMAT.format(currentDate);
        
        lblSemester.setText(semesterDate);
        lblBlock.setText("" + course.getBlock());
        lblSection.setText("" + course.getSection());
        lblNrc.setText(course.getNrc());
        lblGenerationDate.setText(currentDateString);
        
    }

    private String getSemesterDate(int semesterId) throws SQLException{
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = semesterDao.getSemesterPerId(semesterId);
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);
        
        return semesterDate;
    }
}
