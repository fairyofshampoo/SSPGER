package mx.uv.fei.sspger.GUI.controllers;


import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.UserSession;
import mx.uv.fei.sspger.logic.UserTypes;


public class NavigationBarController {

    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;
    
    @FXML
    private ImageView imgMyAcademicBody;

    @FXML
    private ImageView imgMyCourses;

    @FXML
    private ImageView imgMyProjects;

    @FXML
    private ImageView imgReceptionalWork;

    @FXML
    private Pane pnNavigationBarProfessor;
    
    @FXML
    private ImageView imgHomeProfessor;
    @FXML
    private Pane pnNavigationBarStudent;
    @FXML
    private ImageView imgHomeStudent;
    @FXML
    private ImageView imgMyReceptionalWork;
    @FXML
    private ImageView imgAllProjects;
    @FXML
    private Pane pnNavigationBarParent;
    @FXML
    private Group groupCloseSession;
    @FXML
    private ImageView imgExit;

    private final int ADMIN_ROLE = 1;
    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    
    @FXML
    void academicBodyClicked(MouseEvent event) {
        SPGER.setRoot("AcademicBodyProjects.fxml");
    }

    @FXML
    void allCoursesClicked(MouseEvent event) {
        SPGER.setRoot("ProfessorCourseManager.fxml");
    }

    @FXML
    void homeClicked(MouseEvent event) {
        SPGER.setRoot("HomeProfessor.fxml");
    }
    
    @FXML
    void academicBodyManagerClicked(MouseEvent event) {
        SPGER.setRoot("AcademicBodyManager.fxml");
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
        verifyUserType();
        displayImages();
        showAdminFunctionalities();
    }
    
    private void displayImages(){
        
        imgHomeProfessor.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());
        imgMyAcademicBody.setImage(ImagesSetter.getMyAcademicBodyImage());
        imgMyCourses.setImage(ImagesSetter.getAddCoursesImage());
        imgMyProjects.setImage(ImagesSetter.getMyProjectImage());
        imgReceptionalWork.setImage(ImagesSetter.getReceptionalWorkImage());
        imgHomeStudent.setImage(ImagesSetter.getHomeImage());
        imgMyReceptionalWork.setImage(ImagesSetter.getReceptionalWorkImage());
        imgAllProjects.setImage(ImagesSetter.getProjectsImage());
        imgExit.setImage(ImagesSetter.getExitImage());
        
    }
    
    private void verifyUserType(){
        if(UserSession.getInstance().getUserType().equals(PROFESSOR_TYPE.getDisplayName())){
            setProfessorToolTips();
            pnNavigationBarProfessor.setVisible(true);
        }
        
        if(UserSession.getInstance().getUserType().equals(STUDENT_TYPE.getDisplayName())){
            setStudentToolTips();
            pnNavigationBarStudent.setVisible(true);
        }
    }
    
    private void showAdminFunctionalities(){
        if(UserSession.getInstance().getPrivileges() == ADMIN_ROLE){
            imgAddUsers.setVisible(true);
            imgAddCourses.setVisible(true);
            imgAddAcademicBody.setVisible(true);
        }
    }
    
    private void setProfessorToolTips(){
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
        Tooltip.install(imgHomeProfessor, tltpHome);
        Tooltip.install(imgMyCourses, tltpCourses);
        Tooltip.install(imgAddCourses, tltpCoursesManager);
        Tooltip.install(imgMyAcademicBody, tltpAcademicBody);
        Tooltip.install(imgAddUsers, tltpAddUsers);
    }
    
    private void setStudentToolTips(){
        Tooltip tltpAllProjects = new Tooltip("Anteproyectos");
        Tooltip tltpReceptionalWork = new Tooltip("Mi trabajo recepcional");
        Tooltip tltpHome = new Tooltip("Página principal");
        
        Tooltip.install(imgAllProjects, tltpAllProjects);
        Tooltip.install(imgMyReceptionalWork, tltpReceptionalWork);
        Tooltip.install(imgHomeStudent, tltpHome);
    }
    
    @FXML
    void mouseEnteredArea(MouseEvent event) {
        pnNavigationBarParent.setCursor(Cursor.HAND);
    }

    @FXML
    void mouseExitedArea(MouseEvent event) {
        pnNavigationBarParent.setCursor(Cursor.DEFAULT);
    }
    
    @FXML
    void allProjectsClicked(MouseEvent event) {
        SPGER.setRoot("AvailableProjectsCatalog.fxml");
    }
    
    @FXML
    private void myProjectsClicked(MouseEvent event) {
        SPGER.setRoot("MyProjects.fxml");
    }

    @FXML
    private void receptionalWorksViewClicked(MouseEvent event) {
        SPGER.setRoot("MyReceptionalWorks.fxml");
    }

    @FXML
    private void homeStudentClicked(MouseEvent event) {
        SPGER.setRoot("HomeStudent.fxml");
    }

    @FXML
    private void myReceptionalWorkClicked(MouseEvent event) {
        SPGER.setRoot("MyReceptionalWork.fxml");
    }

    @FXML
    private void exitClicked(MouseEvent event) {
        if(isExitConfirmed()){
            UserSession.getInstance().cleanUserSession();
            SPGER.setRoot("Login.fxml");
        }
    }
    
    private boolean isExitConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas cerrar sesión?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
}
