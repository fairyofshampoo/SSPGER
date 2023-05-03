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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.Professor;


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
    private Label lblUsers;
    
    @FXML
    private HBox coursesCard;
    
    @FXML
    private HBox usersCardLayout;
    
    int proffesorsCardSpaces = 3;
    
    private List<Professor> professorsRecentlyAdded;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        displayProfessors();
    }
    
    @FXML
    void usersManagerClicked(MouseEvent mouseEvent){
        try {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/UsersManager.fxml");
        } catch (IOException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void displayImages(){
        
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
            
            if (professorList.size() > proffesorsCardSpaces){
                professorList = reduceProfessorList(professorList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HomeAdministrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return professorList;
    }
    
    private List<Professor> reduceProfessorList(List<Professor> professorList){
    List<Professor> newProfessorList = new ArrayList<>();
    
    for(int i = 0; i < proffesorsCardSpaces; i++){
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
    
}
