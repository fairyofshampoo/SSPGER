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
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;


public class HomeProfessorController implements Initializable {
    
    @FXML
    private HBox coursesCardLayout;

    @FXML
    private HBox projectsCardLayout;
    
    @FXML
    private Pane pnNavigationBar;

    final int CARD_SPACES = 3;
    
    private List<Project> projectsRecentlyAdded;
    private List<Course> coursesRecentlyAdded;
    private final int ADMIN_ROLE= 1;
    
    @FXML
    void allCoursesClicked(MouseEvent event) {

    }

    @FXML
    void allProjectsClicked(MouseEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCourses();
        setNavigationBar();
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
            courseList = courseDAO.getCoursesPerProfessor(UserSession.getInstance().getUserId());
            
            if (courseList.size() > CARD_SPACES){
                courseList = reduceCourseList(courseList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage (
                "Error de conexión a la base de datos",
                Status.FATAL));
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

    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();
        
            pnNavigationBar.getChildren().add(pnNavigationBarParent);
        } catch (IOException ex) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage (
                "Archivo FXML corrupto",
                Status.FATAL));
        }
    }
}
