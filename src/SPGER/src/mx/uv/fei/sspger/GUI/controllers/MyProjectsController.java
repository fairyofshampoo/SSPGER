package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.UserSession;

public class MyProjectsController implements Initializable {

    @FXML
    private VBox vboxDirectorProjects;

    @FXML
    private Pane pnNavigationBar;

    @FXML
    private Button btnAddProject;

    @FXML
    private ChoiceBox<String> cbxProjectFilter;

    private final int VALUE_BY_DEFAULT = 0;
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSENT_SIZE_CARD = 10;
    private final int ID_PROFESSOR = UserSession.getInstance().getUserId();
    private final String DIRECTOR_ROLE = "Director";
    private final String CODIRECTOR_ROLE = "Codirector";
    private final String STATUS_ALL_PROJECTS = "TODOS";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayDirectorProjectCards(getProjectsByRole(DIRECTOR_ROLE), DIRECTOR_ROLE);
        displayDirectorProjectCards(getProjectsByRole(CODIRECTOR_ROLE), CODIRECTOR_ROLE);
        setNavigationBar();
        setChoiceBoxProject();
        setProjectFilter();
    }

    public void displayDirectorProjectCards(List<Project> projectList, String role) {
        Label lblDirector = new Label(role);
        lblDirector.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vboxDirectorProjects.getChildren().add(lblDirector);

        if (projectList.isEmpty()) {
            Label lblNotFound = new Label("No existen anteproyectos como " + role);
            lblNotFound.setStyle("-fx-font-size: 14px");
            vboxDirectorProjects.getChildren().add(lblNotFound);
        } else {
            GridPane gpProjectCardSpaces = new GridPane();
            int columnCardSpaces = VALUE_BY_DEFAULT;
            int row = VALUE_BY_DEFAULT;

            try {
                for (int i = 0; i < projectList.size(); i++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/DirectorProjectCard.fxml"));
                    AnchorPane apDirectorProjectsCard = loader.load();
                    DirectorProjectCardController cardController = loader.getController();
                    cardController.setDirectorProjectData(projectList.get(i));

                    if (columnCardSpaces == UPPER_LIMIT_COLUMN) {
                        columnCardSpaces = VALUE_BY_DEFAULT;
                        row++;
                    }

                    gpProjectCardSpaces.add(apDirectorProjectsCard, columnCardSpaces++, row);
                    GridPane.setMargin(apDirectorProjectsCard, new Insets(INSENT_SIZE_CARD));
                }
                vboxDirectorProjects.getChildren().add(gpProjectCardSpaces);
            } catch (IOException ex) {
                Logger.getLogger(MyProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFXMLFileFailedAlert();
            }
        }
    }

    private List<Project> getProjectsByRole(String role) {
        List<Project> projectList = new ArrayList<>();
        ProjectDAO projectDAO = new ProjectDAO();

        try {
            projectList = projectDAO.getProjectsPerDirectorCard(ID_PROFESSOR, role);
        } catch (SQLException ex) {
            Logger.getLogger(MyProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return projectList;
    }

    private void setChoiceBoxProject() {
        for (ProjectStatus status : ProjectStatus.values()) {
            cbxProjectFilter.getItems().add(status.getDisplayName());
        }
        cbxProjectFilter.getItems().add(STATUS_ALL_PROJECTS);
        cbxProjectFilter.setValue(STATUS_ALL_PROJECTS);
    }

    private List<Project> getProjectsByStatusPerRole(String role, String status) {
        List<Project> projectList = new ArrayList<>();
        ProjectDAO projectDao = new ProjectDAO();

        try {
            projectList = projectDao.getProjectsByStatusPerDirectorCard(ID_PROFESSOR, role, status);
        } catch (SQLException ex) {
            Logger.getLogger(MyProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return projectList;
    }

    private void filterStatus() {
        String selection = cbxProjectFilter.getValue();

        if (selection.equals(STATUS_ALL_PROJECTS)) {
            displayDirectorProjectCards(getProjectsByRole(DIRECTOR_ROLE), DIRECTOR_ROLE);
            displayDirectorProjectCards(getProjectsByRole(CODIRECTOR_ROLE), CODIRECTOR_ROLE);
        } else {
            displayDirectorProjectCards(getProjectsByStatusPerRole(DIRECTOR_ROLE, selection), DIRECTOR_ROLE);
            displayDirectorProjectCards(getProjectsByStatusPerRole(CODIRECTOR_ROLE, selection), CODIRECTOR_ROLE);
        }
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
            Logger.getLogger(MyProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private void setProjectFilter() {
        cbxProjectFilter.setOnAction(event -> {
            vboxDirectorProjects.getChildren().clear();
            filterStatus();
        });
    }

    @FXML
    private void addProjectClicked(ActionEvent event) {
        SPGER.setRoot("ProjectRegister.fxml");
    }
}
