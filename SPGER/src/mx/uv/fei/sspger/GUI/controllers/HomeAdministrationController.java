package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;


public class HomeAdministrationController implements Initializable {
    
    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;

    @FXML
    private ImageView imgHome;

    @FXML
    private Label lblTitleSystem;
    
    @FXML
    private Label lblAllUsers;

    @FXML
    private Label lblUsers;
    
    @FXML
    private HBox coursesCardLayout;
    
    @FXML
    private HBox usersCardLayout;
    
    @FXML
    private HBox studentsCardLayout;
    
    final int CARD_SPACES = 3;
    
    private List<Professor> professorsRecentlyAdded;
    private List<Course> coursesRecentlyAdded;
    private List<Student> studentsRecentlyAdded;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        displayProfessors();
        displayCourses();
        displayStudents();
    }
    
    @FXML
    void usersManagerClicked(MouseEvent mouseEvent){
        displayUsersManagerWindow();
    }
    
    @FXML
    void allUsersClicked(MouseEvent event) {
        displayUsersManagerWindow();
    }
    
    private void displayUsersManagerWindow(){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
        } catch (IOException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void displayImages(){
        
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());
        
    }
    
    private List<Professor> professorsRecentlyAdded(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        
        try {
            professorList = professorDAO.getAllProfessors();
            
            if (professorList.size() > CARD_SPACES){
                professorList = reduceProfessorList(professorList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return professorList;
    }
    
    private List<Professor> reduceProfessorList(List<Professor> professorList){
    List<Professor> newProfessorList = new ArrayList<>();
    
    for(int i = 0; i < CARD_SPACES; i++){
        newProfessorList.add(professorList.get(i));
    }
    
    return newProfessorList;
    }

    public void displayProfessors(){
        professorsRecentlyAdded = new ArrayList<>(professorsRecentlyAdded());
        try {
            for(int i=0; i<professorsRecentlyAdded.size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/usersCard.fxml"));
                HBox boxProfessor = loader.load();
                UsersCardController cardController = loader.getController();
                cardController.setUserProfessorData(professorsRecentlyAdded.get(i));
                usersCardLayout.getChildren().add(boxProfessor);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void displayStudents(){
        studentsRecentlyAdded = new ArrayList<>(studentsRecentlyAdded());
        try {
            for(int i=0; i<studentsRecentlyAdded.size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/usersCard.fxml"));
                HBox boxStudent = loader.load();
                UsersCardController cardController = loader.getController();
                cardController.setUserStudentData(studentsRecentlyAdded.get(i));
                studentsCardLayout.getChildren().add(boxStudent);
                
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Student> studentsRecentlyAdded(){
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentsList = new ArrayList<>();
        
        try {
            studentsList = studentDAO.getAllStudents();
            
            if (studentsList.size() > CARD_SPACES){
                studentsList = reduceStudentList(studentsList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return studentsList;
    }
    
    private List<Student> reduceStudentList(List<Student> studentList){
    List<Student> newStudentList = new ArrayList<>();
    
    for(int i = 0; i < CARD_SPACES; i++){
        newStudentList.add(studentList.get(i));
    }
    
    return newStudentList;
    }
    
    public void displayCourses(){
        coursesRecentlyAdded = new ArrayList<>(coursesRecentlyAdded());
        try {
            for(int i=0; i<coursesRecentlyAdded.size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCard.fxml"));
                AnchorPane apCourseCard = loader.load();
                CourseCardController courseCardController = loader.getController();
                courseCardController.setCourseData(coursesRecentlyAdded.get(i));
                coursesCardLayout.getChildren().add(apCourseCard);
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<Course> coursesRecentlyAdded(){
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = new ArrayList<>();
        
        try {
            courseList = courseDAO.getAllCourses();
            
            if (courseList.size() > CARD_SPACES){
                courseList = reduceCourseList(courseList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return courseList;
    }
    
    private List<Course> reduceCourseList(List<Course> courseList){
    List<Course> newCourseList = new ArrayList<>();
    
    for(int i = 0; i < CARD_SPACES; i++){
        newCourseList.add(courseList.get(i));
    }
    
    return newCourseList;
    }
    
}
