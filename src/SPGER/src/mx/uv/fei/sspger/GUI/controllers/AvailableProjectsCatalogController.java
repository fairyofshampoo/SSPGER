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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class AvailableProjectsCatalogController implements Initializable {

    @FXML
    private ImageView imgSearch;

    @FXML
    private Pane pnNavigationBar;

    @FXML
    private TextField txtSearchProject;

    @FXML
    private VBox vboxAvailableProjectsContent;

    private final ProjectStatus VALIDATED_STATUS = ProjectStatus.VALIDATED;
    private final int VALUE_BY_DEFAULT = 0;
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSET_SIZE_CARD = 10;
    private final int ID_STUDENT = UserSession.getInstance().getUserId();

    private void displayAvailableProject() {
        List<Professor> directorList = getDirectorsPerProject();
        int numSections = directorList.size();

        if (numSections == VALUE_BY_DEFAULT) {
            Label lblNotFound = new Label("No hay anteproyectos disponibles");
            lblNotFound.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vboxAvailableProjectsContent.getChildren().add(lblNotFound);
        } else {
            for (int i = 0; i < numSections; i++) {
                Professor director = directorList.get(i);
                Label lblDirector = new Label(director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName());
                lblDirector.setStyle("-fx-font-size: 15px; -fx-font-weight: bold");
                vboxAvailableProjectsContent.getChildren().add(lblDirector);
                List<Project> projectList = getProjectsByDirector(director.getId());

                setDirectorCards(projectList);
            }
        }
    }

    private List<Professor> getDirectorsPerProject() {
        List<Professor> directorList = new ArrayList<>();
        ProfessorDAO professorDAO = new ProfessorDAO();

        try {
            directorList = professorDAO.getDirectorsPerProjectStatus(VALIDATED_STATUS.getDisplayName());
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return directorList;
    }

    private List<Project> getProjectsByDirector(int idProfessor) {
        ProjectDAO projectDAO = new ProjectDAO();
        List<Project> projectList = new ArrayList<>();

        try {
            projectList = projectDAO.getAvailableProjectsPerDirectorCard(idProfessor);
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return projectList;
    }

    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();

            pnNavigationBar.getChildren().add(pnNavigationBarParent);
        } catch (IOException ioException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
        }
    }

    private void setDirectorCards(List<Project> projectList) {
        GridPane gpAvailablesProjectContent = new GridPane();
        int row = VALUE_BY_DEFAULT;
        int column = VALUE_BY_DEFAULT;

        try {
            for (int j = 0; j < projectList.size(); j++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AvailableProjectCard.fxml"));
                AnchorPane apAvailableProject;
                apAvailableProject = loader.load();

                AvailableProjectCardController cardController = loader.getController();
                cardController.setAvailableProjectData(projectList.get(j));

                if (column == UPPER_LIMIT_COLUMN) {
                    column = VALUE_BY_DEFAULT;
                    row++;
                }
                gpAvailablesProjectContent.add(apAvailableProject, column++, row);
                GridPane.setMargin(apAvailableProject, new Insets(INSET_SIZE_CARD));
            }
        } catch (IOException ioException) {
            Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
        }
        vboxAvailableProjectsContent.getChildren().add(gpAvailablesProjectContent);
    }

    private List<Project> getSearchedAvailableProject() {
        ProjectDAO projectDao = new ProjectDAO();
        List<Project> projectList = new ArrayList<>();
        String projectName = txtSearchProject.getText();

        try {
            projectList = projectDao.getProjectsByStatusSearched(projectName, VALIDATED_STATUS.getDisplayName());
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectsCatalogController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return projectList;
    }

    private boolean isTextSearchedValid() {
        boolean isValid = true;
        String projectName = txtSearchProject.getText();

        if (projectName.isBlank() || projectName.isEmpty()) {
            isValid = false;
        }

        return isValid;
    }

    private void search() {
        List<Project> projectList = getSearchedAvailableProject();
        vboxAvailableProjectsContent.getChildren().clear();

        if (projectList.isEmpty()) {
            Label lblNotFound = new Label("No hay anteproyectos relacionados a su búsqueda");
            lblNotFound.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vboxAvailableProjectsContent.getChildren().add(lblNotFound);
        } else {
            setDirectorCards(projectList);
        }
    }

    @FXML
    void searchProject(MouseEvent event) {
        if (isTextSearchedValid()) {
            search();
        }
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNavigationBar();
        displayAvailableProject();
    }
}
