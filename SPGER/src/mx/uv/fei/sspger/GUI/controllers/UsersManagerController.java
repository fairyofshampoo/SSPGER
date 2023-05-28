package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;


public class UsersManagerController implements Initializable {

    
    @FXML
    private Button btnAddUser;

    @FXML
    private GridPane gpUsers;

    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgAddUsers;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgSearchBar;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    private TextField txtSearchBar;
    
    private final int ACTIVE_STATUS = 1;
    private int column = 0;
    private int row = 0;
    
    @FXML
    void addUserButtonClicked(ActionEvent event){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UserRegister.fxml");
        } catch (IOException ex) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void homeClicked(MouseEvent mouseEvent){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/HomeProfessor.fxml");
        } catch (IOException ex) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        setStudentCards();
        setProfessorCards();
    } 
    
    private void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourses.setImage(ImagesSetter.getCoursesImage());
        imgAddUsers.setImage(ImagesSetter.getUsersImage());
        imgSearchBar.setImage(ImagesSetter.getSearchBarImage());
        
    }
    
    private void setStudentCards(){
        List<Student> studentsList = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();
        
        try{
            studentsList = studentDao.getStudentsByStatus(ACTIVE_STATUS);
        } catch (SQLException sqlException){
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        try{
            for (int card = 0; card < studentsList.size(); card++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/UsersCard.fxml"));
                HBox studentCard = fxmlLoader.load();
                UsersCardController usersCardController = fxmlLoader.getController();
                usersCardController.setUserStudentData(studentsList.get(card));
                
                if(column == 3){
                    column = 0;
                    row++;
                }
                
                gpUsers.add(studentCard, column++, row);
                GridPane.setMargin(studentCard, new Insets(10));
            }
        } catch (IOException ioException){
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
    private void setProfessorCards(){
        List<Professor> professorsList = new ArrayList<>();
        ProfessorDAO professorDao = new ProfessorDAO();
        
        try{
            professorsList = professorDao.getProfessorsByStatus(ACTIVE_STATUS);
        } catch (SQLException sqlException){
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        try{
            for (int card = 0; card < professorsList.size(); card++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/UsersCard.fxml"));
                HBox professorCard = fxmlLoader.load();
                UsersCardController usersCardController = fxmlLoader.getController();
                usersCardController.setUserProfessorData(professorsList.get(card));
                
                if(column == 3){
                    column = 0;
                    row++;
                }
                
                gpUsers.add(professorCard, column++, row);
                GridPane.setMargin(professorCard, new Insets(10));
            }
        } catch (IOException ioException){
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
}

