package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class AvailableProjectController implements Initializable {

    @FXML
    private Button btnApply;

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
    private VBox vboxBtnApplyContent;

    @FXML
    private VBox vboxLgacDataContent;

    @FXML
    private VBox vboxCodirectorDataContent;

    private static int idProject;
    private final int ID_STUDENT = UserSession.getInstance().getUserId();
    private final int NOT_FOUND_INT = -1;
    private final int LIMIT_STUDENT_PROJECT_APPLY = 3;
    private final int VALUE_BY_DEFAULT = 0;
    private final String NOT_FOUND_STRING = "-1";

    public static void setIdProject(int idProject) {
        AvailableProjectController.idProject = idProject;
    }

    public int getTotalProjectSelectedByStudent() {
        ProjectDAO projectDAO = new ProjectDAO();
        int totalProjectSelected = VALUE_BY_DEFAULT;

        try {
            totalProjectSelected = projectDAO.getTotalProjectSelectedByStudent(ID_STUDENT);
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return totalProjectSelected;
    }

    public int getExistenceProjectApplication() {
        ProjectDAO projectDAO = new ProjectDAO();
        int existenceProjectApplication = VALUE_BY_DEFAULT;

        try {
            existenceProjectApplication = projectDAO.getExistenceApplicationToProject(ID_STUDENT, idProject);
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return existenceProjectApplication;
    }

    public void showBtnApplyToProject() {
        ProjectDAO projectDAO = new ProjectDAO();
        int totalProjectSelected = getTotalProjectSelectedByStudent();
        int existenceProjectApplication = getExistenceProjectApplication();
        int totalAccepted = getTotalStudentAccepted();

        if (totalAccepted > VALUE_BY_DEFAULT) {
            setInvisibleBtnApply("Ya tienes un anteproyecto asignado");
        } else if (existenceProjectApplication > VALUE_BY_DEFAULT) {
            setInvisibleBtnApply("Ya te has postulado a este anteproyecto");
        } else if (totalProjectSelected > LIMIT_STUDENT_PROJECT_APPLY) {
            setInvisibleBtnApply("No te puedes postular a más anteproyectos");
        }
    }

    public int getTotalStudentAccepted() {
        ProjectDAO projectDao = new ProjectDAO();
        int total = NOT_FOUND_INT;

        try {
            total = projectDao.totalStudentAccepted(ID_STUDENT);
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return total;
    }

    public void setInvisibleBtnApply(String message) {
        btnApply.setDisable(false);
        btnApply.setVisible(false);

        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        lblMessage.setStyle("-fx-font-weight: bold");
        vboxBtnApplyContent.getChildren().add(lblMessage);
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

        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
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

    public void applyToProject() {
        ProjectDAO projectDAO = new ProjectDAO();

        int resultApply = VALUE_BY_DEFAULT;

        try {
            resultApply = projectDAO.applyToProject(idProject, ID_STUDENT);
        } catch (SQLException sqlException) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        if (resultApply > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Te has postulado correctamente", Status.SUCCESS));
            goBack();
        } else {
            DialogGenerator.getDialog(new AlertMessage("No fue posible realizar la postulación", Status.ERROR));
        }
    }

    @FXML
    void applyToProject(MouseEvent event) {
        if (postulatedConfirmation()) {
            applyToProject();
        }
    }

    @FXML
    void openAvailableProjectsCatalog(MouseEvent event) {
        goBack();
    }

    private void goBack() {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AvailableProjectsCatalog.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBtnApplyToProject();
        showProjectData();
    }

    private boolean postulatedConfirmation() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Estás seguro de tu elección?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
