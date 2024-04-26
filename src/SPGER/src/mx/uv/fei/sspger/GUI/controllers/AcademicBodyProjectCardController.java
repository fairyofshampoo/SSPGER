package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;

public class AcademicBodyProjectCardController {

    @FXML
    private Pane pnAcademicBodyCard;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblSpaces;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblStatus;

    private int idProject = 0;
    private String academicBodyKey;

    public void setDirectorProjectData(Project project) {
        lblProjectName.setText(project.getName());
        lblModality.setText(project.getModality());
        lblSpaces.setText(Integer.toString(project.getSpaces()) + " participantes");
        lblDuration.setText(Integer.toString(project.getDuration()) + " meses");
        lblStatus.setText(project.getStatus());
        idProject = project.getIdProject();
        getAcademicBodyKey();
    }

    private void getAcademicBodyKey() {
        ProjectDAO projectDao = new ProjectDAO();

        try {
            academicBodyKey = projectDao.getAcademicBodyByProject(idProject).getKey();
        } catch (SQLException sqlException) {
            Logger.getLogger(DirectorProjectCardController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    @FXML
    private void visualizeProject(MouseEvent event) {
        ProjectViewAcademicBodyController.setIdProject(idProject);
        ProjectViewAcademicBodyController.setAcademicBodyKey(academicBodyKey);
        SPGER.setRoot("ProjectViewAcademicBody.fxml");
    }
}
