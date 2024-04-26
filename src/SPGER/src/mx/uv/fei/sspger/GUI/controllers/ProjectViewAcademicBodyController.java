package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.AcademicBodyMemberRole;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;


public class ProjectViewAcademicBodyController implements Initializable {
    @FXML
    private Button btnRejected;

    @FXML
    private Button btnValidate;

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
    
    private static int idProject;
    private static String academicBodyKey;
    private final int ID_PROFESSOR = UserSession.getInstance().getUserId();
    private final String PROJECT_STATE_PROPOSED = ProjectStatus.PROPOSED.getDisplayName();
    private final String RESPONSIBLE_ROLE = AcademicBodyMemberRole.RESPONSIBLE.getDisplayName();
    private final int VALUE_BY_DEFAULT = 0;
    
    public static void setIdProject(int idProject){
        ProjectViewAcademicBodyController.idProject = idProject;
    }

    public static void setAcademicBodyKey(String academicBodyKey) {
        ProjectViewAcademicBodyController.academicBodyKey = academicBodyKey;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showProjectData();
        activateButtons();
    }
    
    private AcademicBodyMember getAcademicBodyMember(){
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        try {
            academicBodyMember = academicBodyDao.getMemberPerIdProfessor(ID_PROFESSOR, academicBodyKey);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectViewAcademicBodyController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        return academicBodyMember;
    }
    
    public void activateButtons(){
        Project project = getProjectData();
        AcademicBodyMember academicBodyMember = getAcademicBodyMember();
        
        if(project.getStatus().equals(PROJECT_STATE_PROPOSED) && academicBodyMember.getRole().equals(RESPONSIBLE_ROLE)){
            btnRejected.setDisable(false);
            btnValidate.setDisable(false);
        }
    }
   
    public Project getProjectData(){
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
            Logger.getLogger(ProjectViewAcademicBodyController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        return project;
    }
    
    public void showProjectData(){
        Project project = getProjectData();
        
        lblAcademicBodyData.setText(project.getAcademicBody().getKey() + "  " + project.getAcademicBody().getName());
        ntxtBibliographyData.setText(project.getBibliography());
        lblDurationData.setText(String.valueOf(project.getDuration()) + " meses");
        ntxtExpectedResultsData.setText(project.getExpectedResults());
        ntxtPladeaFeiName.setText(project.getPladeaFeiName());
        ntextInvestigationLine.setText(project.getInvestigationLine());
        lblDirectorData.setText(project.getDirector().getHonorificTitle() + " " + project.getDirector().getName() + " " + project.getDirector().getLastName());
        
        for(int i = 0; i < project.getCodirectors().size(); i++){
            lblCodirector.setVisible(true);
            Label lblCodirectorData = new Label();
            lblCodirectorData.setStyle("-fx-font-size: 14px");
            lblCodirectorData.setText(project.getCodirectors().get(i).getHonorificTitle() + " " + project.getCodirectors().get(i).getName() + " " + project.getCodirectors().get(i).getLastName());
            vboxCodirectorDataContent.getChildren().add(lblCodirectorData);
        }
        
        for(int i = 0; i < project.getLgac().size(); i++){
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
    
    private void rejectProject(){
        ProjectDAO projectDao = new ProjectDAO();
        
        int result = VALUE_BY_DEFAULT;
        
        try {
            result = projectDao.rejectProject(idProject, ID_PROFESSOR);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectViewAcademicBodyController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        if(result > VALUE_BY_DEFAULT){
            DialogGenerator.getDialog(new AlertMessage ("Se ha rechazado correctamente.",Status.SUCCESS));
        }else{
            DialogGenerator.getDialog(new AlertMessage ("Hubo un error al rechazar. Intente nuevamente o regrese más tarde.",Status.ERROR));
        }
    }
    
    private void validateProject(){
        ProjectDAO projectDao = new ProjectDAO();
        
        int result = VALUE_BY_DEFAULT;
        
        try {
            result = projectDao.validateProject(idProject, ID_PROFESSOR);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectViewAcademicBodyController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        if(result > VALUE_BY_DEFAULT){
            DialogGenerator.getDialog(new AlertMessage ("Se ha validado correctamente.",Status.SUCCESS));
        }else{
            DialogGenerator.getDialog(new AlertMessage ("Hubo un error al validar. Intente nuevamente o regrese más tarde.",Status.ERROR));
        }
    }
    
    @FXML
    void goBack(MouseEvent event) {
        close();
    }
    
    @FXML
    void rejectClicked(ActionEvent event) {
        if(rejectConfirmation()){
            rejectProject();
            close();
        }
    }

    @FXML
    void validateClicked(ActionEvent event) {
        if(validateConfirmation()){
            validateProject();
            close();
        }
    }
    
    private void close(){
        SPGER.setRoot("AcademicBodyProjects.fxml");
    }
    
    private boolean validateConfirmation(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Estás seguro de VALIDAR el anteproyecto?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private boolean rejectConfirmation(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Estás seguro de RECHAZAR el anteproyecto?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private void showFailedConnectionAlert(){
        DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.",Status.FATAL));
    }
}
