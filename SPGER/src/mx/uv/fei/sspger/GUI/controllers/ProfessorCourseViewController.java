package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;


public class ProfessorCourseViewController implements Initializable {
    //private
    public static Course course;
    //public static int idCourse; 
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private int column = 0;
    private int row = 0;
    
    //SETTER
    
    //DAO buscar el curso con idCourse
    
    @FXML
    private Label lblBlock;

    @FXML
    private Label lblCourseTitle;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblSemester;
    
    @FXML
    private ImageView imgReturn;
    
    @FXML
    private GridPane gpCourseStudents;

    @FXML
    private void mouseEnteredBackButtonArea(MouseEvent event) {
        imgReturn.setCursor(Cursor.HAND);
    }

    @FXML
    private void mouseExitedBackButtonArea(MouseEvent event) {
        imgReturn.setCursor(Cursor.DEFAULT);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
        setStudentCards();
        
    }  
    
    private void setGraphicElements(){
        imgReturn.setImage(ImagesSetter.getGoBackImage());
        lblBlock.setText("Bloque: " + course.getBlock());
        lblNrc.setText("NRC: " + course.getNrc());
        lblCourseTitle.setText(course.getName());
        lblSection.setText("Sección: " + course.getSection());
        lblSemester.setText(getSemesterString(course.getSemesterId()));
    }
    
    private void setStudentCards(){
        List<Student> courseStudents = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();
        
        try{
            courseStudents = studentDao.getStudentsPerCourse(course.getCourseId());
        } catch (SQLException sqlException){
            Logger.getLogger(ProfessorCourseViewController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        try{
            for (int card = 0; card < courseStudents.size(); card++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentCardForProfessor.fxml"));
                AnchorPane studentCard = fxmlLoader.load();
                StudentCardForProfessorController studentsCardForProfessorController = fxmlLoader.getController();
                studentsCardForProfessorController.setStudentData(courseStudents.get(card));
                
                if(column == 3){
                    column = 0;
                    row++;
                }
                
                gpCourseStudents.add(studentCard, column++, row);
                GridPane.setMargin(studentCard, new Insets(10));
            }
        } catch (IOException ioException){
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
    private String getSemesterString(int semesterId) {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = new Semester();
        
        try{
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException sqlException){
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema.",
                Status.FATAL));           
        }
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);
        
        return semesterDate;
    }
    
    @FXML
    private void generateCourseReport(ActionEvent event) {
        CourseReportController.setCourseId(course.getCourseId());
        
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/CourseReport");
    }

    @FXML
    private void goBack(MouseEvent event) {

        imgReturn.setCursor(Cursor.DEFAULT);
        
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ProfessorCourseManager");
    }
}
