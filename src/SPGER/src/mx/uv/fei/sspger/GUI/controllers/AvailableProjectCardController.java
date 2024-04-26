package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Project;

public class AvailableProjectCardController {

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblSpaces;

    private int idProject;

    public void setAvailableProjectData(Project project) {
        lblProjectName.setText(project.getName());
        lblModality.setText(project.getModality());
        lblSpaces.setText(Integer.toString(project.getSpaces()));
        lblDuration.setText(Integer.toString(project.getDuration()) + " meses");
        idProject = project.getIdProject();
    }

    @FXML
    void goToAvailableProject(MouseEvent event) {
        AvailableProjectController.setIdProject(idProject);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AvailableProject.fxml");
    }
}
