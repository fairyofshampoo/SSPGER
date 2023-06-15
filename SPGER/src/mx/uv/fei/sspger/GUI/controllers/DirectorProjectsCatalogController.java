package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;


public class DirectorProjectsCatalogController implements Initializable {
    @FXML
    private GridPane gpProjectCardSpaces;

    @FXML
    private ImageView imgCourses;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgMyAcademicBody;

    @FXML
    private ImageView imgMyProjects;

    @FXML
    private ImageView imgReceptionalWork;
    
    
    
    public void displayDirectorProjectCard(){
        List<Project> projectList = new ArrayList<>();
        ProjectDAO projectDAO = new ProjectDAO();
        int columnCardSpaces = 0;
        int row = 0;
        
        try {
            projectList = projectDAO.getProjectsPerDirectorCard(23);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            for(int i = 0; i < projectList.size(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/DirectorProjectCard.fxml"));
                AnchorPane apDirectorProjectsCard = loader.load();
                DirectorProjectCardController cardController = loader.getController();
                cardController.setDirectorProjectData(projectList.get(i));
                
                if(columnCardSpaces == 2){
                    columnCardSpaces = 0;
                    row++;
                }
                gpProjectCardSpaces.add(apDirectorProjectsCard, columnCardSpaces++, row);
                GridPane.setMargin(apDirectorProjectsCard, new Insets(15));
            }
        } catch (IOException ex) {
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayDirectorProjectCard();
        displayImages();
    }
    
    public void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgCourses.setImage(ImagesSetter.getCoursesImage());
        imgMyAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgMyProjects.setImage(ImagesSetter.getMyProjectImage());
        imgReceptionalWork.setImage(ImagesSetter.getReceptionalWorkImage());
    }
}
