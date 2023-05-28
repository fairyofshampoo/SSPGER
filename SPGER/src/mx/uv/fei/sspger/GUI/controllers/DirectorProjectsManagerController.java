package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;


public class DirectorProjectsManagerController implements Initializable {
    ObservableList<ProjectsTable> list = FXCollections.observableArrayList();
    
    @FXML
    private Button btnAddProject;

    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourses;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgProjects;

    @FXML
    private ImageView imgSearchBar;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    private TableView<ProjectsTable> tblProjects;

    @FXML
    private TableColumn<ProjectsTable, String> tblCDirectorName;

    @FXML
    private TableColumn<ProjectsTable, Integer> tblCProjectId;

    @FXML
    private TableColumn<ProjectsTable, String> tblCProjectStatus;

    @FXML
    private TableColumn<ProjectsTable, String> tblCProjectTitle;

    @FXML
    private TextField txtSearchBar;

    @FXML
    void addProjectButtonClicked(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/sspger/GUI/ProjectRegister.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage myStage = (Stage) this.btnAddProject.getScene().getWindow();
            myStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(DirectorProjectsManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void homeClicked(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTable();
    } 
    
    private void fillTable(){
        try {
            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projectList = projectDAO.getAllProjects();
            for (int i =0; i< projectList.size(); i++){
                Project project = projectList.get(i);
                list.add(new ProjectsTable(project.getIdProject(), project.getName(), project.getName(), project.getStatus()));
            }
            tblProjects.setItems(list);
            tblCDirectorName.setCellValueFactory(new PropertyValueFactory<ProjectsTable, String>("directorName"));
            tblCProjectId.setCellValueFactory(new PropertyValueFactory<ProjectsTable, Integer>("identificator"));
            tblCProjectStatus.setCellValueFactory(new PropertyValueFactory<ProjectsTable, String>("projectStatus"));
            tblCProjectTitle.setCellValueFactory(new PropertyValueFactory<ProjectsTable, String>("projectName"));
        } catch (SQLException ex) {
            Logger.getLogger(DirectorProjectsManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
