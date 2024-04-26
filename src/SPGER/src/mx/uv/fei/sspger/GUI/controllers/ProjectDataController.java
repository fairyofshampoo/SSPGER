package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;

public class ProjectDataController implements Initializable {

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblAcademicBodyData;

    @FXML
    private Text ntxtBibliographyData;

    @FXML
    private Label lblCodirector;

    @FXML
    private Label lblDirectorData;

    @FXML
    private Label lblDurationData;

    @FXML
    private Text ntxtExpectedResultsData;

    @FXML
    private Label lblModalityData;

    @FXML
    private Text ntxtNotesData;

    @FXML
    private Label lblParticipantsData;

    @FXML
    private Text ntxtPladeaFeiName;

    @FXML
    private Text ntxtProjectDescriptionData;

    @FXML
    private Label lblProjectTitle;

    @FXML
    private Text ntextInvestigationLine;

    @FXML
    private Text ntxtReceptionalWorkDescription;

    @FXML
    private Text ntxtRequirementsData;

    @FXML
    private VBox vboxLgacDataContent;

    @FXML
    private VBox vboxCodirectorDataContent;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnManageStudents;

    private static int idProject;
    private final String NOT_FOUND_STRING = "-1";
    private final String PROJECT_STATUS_PROPOSED = ProjectStatus.PROPOSED.getDisplayName();
    private final String PROJECT_STATUS_REJECTED = ProjectStatus.REJECTED.getDisplayName();
    private final String PROJECT_STATUS_VALIDATED = ProjectStatus.VALIDATED.getDisplayName();
    private final String PROJECT_STATUS_ASSIGNED = ProjectStatus.ASSIGNED.getDisplayName();

    public static void setIdProject(int idProject) {
        ProjectDataController.idProject = idProject;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showProjectData();
        if (!isValidForModification()) {
            btnModify.setDisable(true);
        }

        if (!isValidForManageStudents()) {
            btnManageStudents.setDisable(true);
        }
    }

    public Project getProjectData() {
        Project project = new Project();
        ProjectDAO projectDao = new ProjectDAO();
        ProfessorDAO professorDao = new ProfessorDAO();

        try {
            project = projectDao.getProject(idProject);
            project.setAcademicBody(projectDao.getAcademicBodyByProject(idProject));
            project.setLgac(projectDao.getLgacByProject(idProject));
            project.setDirector(professorDao.getDirectorByProject(idProject));
            project.setCodirectors(professorDao.getCoodirectorByProject(idProject));

        } catch (SQLException ex) {
            Logger.getLogger(ProjectDataController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return project;
    }

    public void showProjectData() {
        Project project = getProjectData();

        lblAcademicBodyData.setText(project.getAcademicBody().getKey() + "  " + project.getAcademicBody().getName());
        ntxtBibliographyData.setText(project.getBibliography());
        lblDurationData.setText(String.valueOf(project.getDuration()) + " meses");
        ntxtExpectedResultsData.setText(project.getExpectedResults());
        ntxtPladeaFeiName.setText(project.getPladeaFeiName());
        ntextInvestigationLine.setText(project.getInvestigationLine());
        lblDirectorData.setText(project.getDirector().getHonorificTitle() + " " + project.getDirector().getName() + " " + project.getDirector().getLastName());

        for (int i = 0; i < project.getCodirectors().size(); i++) {
            lblCodirector.setVisible(true);
            Label lblCodirectorData = new Label();
            lblCodirectorData.setStyle("-fx-font-size: 14px");
            lblCodirectorData.setText(project.getCodirectors().get(i).getHonorificTitle() + " " + project.getCodirectors().get(i).getName() + " " + project.getCodirectors().get(i).getLastName());
            vboxCodirectorDataContent.getChildren().add(lblCodirectorData);
        }

        for (int i = 0; i < project.getLgac().size(); i++) {
            Label lblLgacData = new Label();
            lblLgacData.setStyle("-fx-font-size: 14px");
            lblLgacData.setText(project.getLgac().get(i).getId() + " - " + project.getLgac().get(i).getName());
            vboxLgacDataContent.getChildren().add(lblLgacData);
        }

        lblModalityData.setText(project.getModality());
        ntxtNotesData.setText(project.getNotes());
        lblParticipantsData.setText(String.valueOf(project.getSpaces()) + " participante(s)");
        ntxtProjectDescriptionData.setText(project.getDescription());
        lblProjectTitle.setText(project.getName());
        ntxtReceptionalWorkDescription.setText(project.getReceptionalWorkDescription());
        ntxtRequirementsData.setText(project.getRequeriments());
    }

    @FXML
    void openMyProjects(MouseEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/MyProjects.fxml");
    }

    @FXML
    void manageStudents(ActionEvent event) {
        StudentsPostuledCatalogController.setIdProject(idProject);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/StudentsPostuledCatalog.fxml");
    }

    private boolean isValidForModification() {
        String status = NOT_FOUND_STRING;
        ProjectDAO projectDao = new ProjectDAO();

        try {
            status = projectDao.getProjectStatus(idProject);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDataController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return status.equals(PROJECT_STATUS_PROPOSED) || status.equals(PROJECT_STATUS_REJECTED);
    }

    private boolean isValidForManageStudents() {
        String status = NOT_FOUND_STRING;
        ProjectDAO projectDao = new ProjectDAO();

        try {
            status = projectDao.getProjectStatus(idProject);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectDataController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return status.equals(PROJECT_STATUS_VALIDATED) || status.equals(PROJECT_STATUS_ASSIGNED);
    }

    @FXML
    private void modifyProject(ActionEvent event) {
        ProjectModifierController.setProjectId(idProject);
        SPGER.setRoot("ProjectModifier.fxml");
    }
}
