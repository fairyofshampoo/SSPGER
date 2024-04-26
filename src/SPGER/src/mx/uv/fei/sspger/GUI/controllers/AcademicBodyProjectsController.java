package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class AcademicBodyProjectsController implements Initializable {

    private final int VALUE_BY_DEFAULT = 0;
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSENT_SIZE_CARD = 10;
    private final int ID_PROFESSOR = UserSession.getInstance().getUserId();

    @FXML
    private VBox vboxDirectorProjects;

    @FXML
    private Pane pnNavigationBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAcademicBodyCards();
        setNavigationBar();
    }

    private void displayAcademicBodyProjects(List<Project> projects, String academicBodyName) {
        setLabel(academicBodyName);

        if (projects.isEmpty()) {
            setLabelNotFound();
        } else {
            setCards(projects);
        }
    }

    private void setLabel(String academicBodyName) {
        Label lblAcademicBodyName = new Label(academicBodyName);
        lblAcademicBodyName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        vboxDirectorProjects.getChildren().add(lblAcademicBodyName);
    }

    private void setLabelNotFound() {
        Label lblAcademicBodyName = new Label("El cuerpo académico no cuenta con anteproyectos.");
        lblAcademicBodyName.setStyle("-fx-font-size: 14px;");
        vboxDirectorProjects.getChildren().add(lblAcademicBodyName);
    }

    private void setCards(List<Project> projects) {
        GridPane gpProjectCardSpaces = new GridPane();
        int columnCardSpaces = VALUE_BY_DEFAULT;
        int row = VALUE_BY_DEFAULT;

        try {
            for (int i = 0; i < projects.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AcademicBodyProjectCard.fxml"));
                Pane pnAcademicBodyProjectsCard = loader.load();
                AcademicBodyProjectCardController cardController = loader.getController();
                cardController.setDirectorProjectData(projects.get(i));

                if (columnCardSpaces == UPPER_LIMIT_COLUMN) {
                    columnCardSpaces = VALUE_BY_DEFAULT;
                    row++;
                }

                gpProjectCardSpaces.add(pnAcademicBodyProjectsCard, columnCardSpaces++, row);
                GridPane.setMargin(pnAcademicBodyProjectsCard, new Insets(INSENT_SIZE_CARD));
            }
            vboxDirectorProjects.getChildren().add(gpProjectCardSpaces);

        } catch (IOException ioException) {
            Logger.getLogger(AcademicBodyProjectsController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
        }
    }

    private List<Project> getProjectsByAcademicBody(String idAcademicBody) {
        List<Project> projectList = new ArrayList<>();
        ProjectDAO projectDAO = new ProjectDAO();

        try {
            projectList = projectDAO.getProjectsByAcademicBody(idAcademicBody);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyProjectsController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return projectList;
    }

    private void setAcademicBodyCards() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBody> academicBodies = new ArrayList<>();

        try {
            academicBodies = academicBodyDao.getAcademicBodiesFromProfessor(ID_PROFESSOR);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyProjectsController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        for (AcademicBody academicBody : academicBodies) {
            displayAcademicBodyProjects(getProjectsByAcademicBody(academicBody.getKey()), academicBody.getName());
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
        } catch (IOException ioException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
        }
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    @FXML
    private void generateReport(ActionEvent event) {
        SPGER.setRoot("SelectAcademicBodyForReport.fxml");
    }

}
