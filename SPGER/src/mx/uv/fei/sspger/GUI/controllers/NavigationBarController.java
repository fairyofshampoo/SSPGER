package mx.uv.fei.sspger.GUI.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.UserSession;


public class NavigationBarController {

    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgMyAcademicBody;

    @FXML
    private ImageView imgMyCourses;

    @FXML
    private ImageView imgMyProjects;

    @FXML
    private ImageView imgReceptionalWork;

    @FXML
    private Pane pnNavigationBar;

    private int ADMIN_ROLE = 1;
    
    @FXML
    void academicBodyClicked(MouseEvent event) {
        
    }

    @FXML
    void allCoursesClicked(MouseEvent event) {

    }

    @FXML
    void homeClicked(MouseEvent event) {
        SPGER.setRoot("HomeProfessor.fxml");
    }
    
    @FXML
    void allProjectsClicked(MouseEvent event) {

    }
    
    @FXML
    void academicBodyManagerClicked(MouseEvent event) {
        
    }

    @FXML
    void coursesManagerClicked(MouseEvent event) {
        SPGER.setRoot("CourseManagement.fxml");
    }

    @FXML
    void usersManagerClicked(MouseEvent event) {
        SPGER.setRoot("UsersManager.fxml");
    }
    
    public void setNavigationBar(){
        setToolTips();
        displayImages();
        showAdminFunctionalities();
    }
    
    private void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());   
    }
    
    private void showAdminFunctionalities(){
        if(UserSession.getInstance().getPrivileges()==ADMIN_ROLE){
            imgAddUsers.setVisible(true);
            imgAddCourses.setVisible(true);
            imgAddAcademicBody.setVisible(true);
            imgAddUsers.setDisable(false);
            imgAddCourses.setDisable(false);
            imgAddAcademicBody.setDisable(false);
            
        }
    }
    
    private void setToolTips(){
        Tooltip tltpMyProjects = new Tooltip("Mis anteproyectos");
        Tooltip tltpAddAcademicBody = new Tooltip("Gestión de cuerpo académico");
        Tooltip tltpReceptionalWork = new Tooltip("Trabajos recepcionales");
        Tooltip tltpHome = new Tooltip("Página principal");
        Tooltip tltpCourses = new Tooltip("Mis cursos");
        Tooltip tltpCoursesManager = new Tooltip("Gestión de cursos");
        Tooltip tltpAcademicBody = new Tooltip("Cuerpo académico");
        Tooltip tltpAddUsers = new Tooltip("Gestión de usuarios");
        
        Tooltip.install(imgMyProjects, tltpMyProjects);
        Tooltip.install(imgAddAcademicBody, tltpAddAcademicBody);
        Tooltip.install(imgReceptionalWork, tltpReceptionalWork);
        Tooltip.install(imgHome, tltpHome);
        Tooltip.install(imgMyCourses, tltpCourses);
        Tooltip.install(imgAddCourses, tltpCoursesManager);
        Tooltip.install(imgMyAcademicBody, tltpAcademicBody);
        Tooltip.install(imgAddUsers, tltpAddUsers);
    }
}
