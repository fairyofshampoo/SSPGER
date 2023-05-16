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
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.Project;


public class HomeProfessorController implements Initializable {
    
    @FXML
    private HBox coursesCardLayout;

    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgReceptionalWork;

    @FXML
    private ImageView imgMyAcademicBody;

    @FXML
    private ImageView imgMyCourses;

    @FXML
    private ImageView imgMyProjects;

    @FXML
    private HBox projectsCardLayout;

    
    final int CARD_SPACES = 3;
    
    private List<Project> projectsRecentlyAdded;
    private List<Course> coursesRecentlyAdded;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        displayCourses();
        setToolTips();
        showAdminFunctionalities();
    }
    
    @FXML
    void usersManagerClicked(MouseEvent mouseEvent){
        displayUsersManagerWindow();
    }
    
    @FXML
    void allProjectsClicked(MouseEvent event) {
        displayUsersManagerWindow();
    }
    
    @FXML
    void allCoursesClicked(MouseEvent event) {
        displayUsersManagerWindow();
    }
    
    @FXML
    void academicBodyClicked(MouseEvent event) {

    }

    @FXML
    void academicBodyManagerClicked(MouseEvent event) {

    }

    @FXML
    void coursesManagerClicked(MouseEvent event) {

    }
    private void showAdminFunctionalities(){
        
    }
    
    private void setToolTips(){
        Tooltip tltpMyProjects = new Tooltip("Mis anteproyectos");
        Tooltip tltpAddAcademicBody = new Tooltip("Gestión de cuerpo académico");
        Tooltip tltpReceptionalWork = new Tooltip("Trabajos recepcionales");
        Tooltip tltpHome = new Tooltip("Página principal");
        Tooltip tltpCourses = new Tooltip("Mis cursos");
        Tooltip tltpCoursesManager = new Tooltip("Gestión de cursos");
        Tooltip tltpAcademicBody = new Tooltip("Cuerpo académico");
        
        Tooltip.install(imgMyProjects, tltpMyProjects);
        Tooltip.install(imgAddAcademicBody, tltpAddAcademicBody);
        Tooltip.install(imgReceptionalWork, tltpReceptionalWork);
        Tooltip.install(imgHome, tltpHome);
        Tooltip.install(imgMyCourses, tltpCourses);
        Tooltip.install(imgAddCourses, tltpCoursesManager);
        Tooltip.install(imgMyAcademicBody, tltpAcademicBody);
    }
    
    private void displayUsersManagerWindow(){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
        } catch (IOException ex) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void displayImages(){
        
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());
        
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
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
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
