package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.controllers.ImagesSetter;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;


public class AvailableProjectsCatalogController implements Initializable {
    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgSearch;
    
    @FXML
    private ImageView imgMyProject;

    @FXML
    private ImageView imgProjects;

    @FXML
    private Label lblProjects;

    @FXML
    private Label lblTitleSystem;
    
    @FXML
    private ScrollPane scpAvailableProjectsContent;

    @FXML
    private TextField txtSearchProject;
    
    @FXML
    private VBox vboxAvailableProjectsContent;
    
    private final ProjectStatus VALIDATED_STATUS = ProjectStatus.VALIDATED;
    
    private void displayAvailableProject(){
        List<Project> projectList = new ArrayList<>();
        ProjectDAO projectDAO = new ProjectDAO();
        List<Professor> directorList = new ArrayList<>();
        ProfessorDAO professorDAO = new ProfessorDAO();
        
        try {
            directorList = professorDAO.getDirectorsPerProjectStatus(VALIDATED_STATUS.getDisplayName());
        } catch (SQLException ex) {
            Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int numSections = directorList.size();
        
        for(int i = 0; i < numSections; i++){
            Professor director = directorList.get(i);
            Label lblDirector = new Label(director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName());
            lblDirector.setStyle("-fx-font-weight: bold");
            vboxAvailableProjectsContent.getChildren().add(lblDirector);
            
            GridPane gpAvailablesProjectContent = new GridPane();
            try {
                projectList = projectDAO.getAvailableProjectsPerDirectorCard(director.getId());
            } catch (SQLException ex) {
                Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int row = 0;
            int column = 0;
            
            try{
                for(int j = 0; j < projectList.size(); j++){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AvailableProjectCard.fxml"));
                    AnchorPane apAvailableProject;
                    apAvailableProject = loader.load();

                    AvailableProjectCardController cardController = loader.getController();
                    cardController.setAvailableProjectData(projectList.get(j));

                    if(column == 2){
                        column = 0;
                        row++;
                    }
                    gpAvailablesProjectContent.add(apAvailableProject, column++, row);
                    GridPane.setMargin(apAvailableProject, new Insets(10));
                }
            } catch (IOException ex) {
                Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            }
            vboxAvailableProjectsContent.getChildren().add(gpAvailablesProjectContent);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        displayAvailableProject();
    }
    
    public void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgMyProject.setImage(ImagesSetter.getMyProjectImage());
        imgProjects.setImage(ImagesSetter.getProjectsImage());
        imgSearch.setImage(ImagesSetter.getSearchBarImage()); 
    }
    
}
