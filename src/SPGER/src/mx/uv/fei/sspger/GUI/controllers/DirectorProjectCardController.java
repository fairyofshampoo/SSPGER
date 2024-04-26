package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Project;

public class DirectorProjectCardController {

    @FXML
    private AnchorPane apDirectorProjectCard;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblSpaces;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblDuration;

    private int idProject;

    public void setDirectorProjectData(Project project) {
        lblProjectName.setText(project.getName());
        lblModality.setText(project.getModality());
        lblSpaces.setText(Integer.toString(project.getSpaces()) + " participantes");
        lblDuration.setText(Integer.toString(project.getDuration()) + " meses");
        lblStatus.setText(project.getStatus());
        idProject = project.getIdProject();
    }

    @FXML
    void openProjectData(MouseEvent event) {
        ProjectDataController.setIdProject(idProject);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ProjectData.fxml");
    }
}
