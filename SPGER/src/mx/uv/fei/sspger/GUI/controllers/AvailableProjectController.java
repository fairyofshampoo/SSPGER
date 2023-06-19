package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;


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
    private Label lblProjectDescription;

    @FXML
    private Text ntxtProjectDescriptionData;

    @FXML
    private Label lblProjectTitle;
    
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

    @FXML
    void applyToProject(MouseEvent event) {
        applyToProject();
    }
    @FXML
    void openAvailableProjectsCatalog(MouseEvent event) {
        SPGER.setRoot("AvailableProjectsCatalog.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBtnApplyToProject();
        showProjectData();
    }
    
    private final int LIMIT_STUDENT_PROJECT_APPLY = 3;
    private final int VALUE_BY_DEFAULT = 0;
    private final int ID_PROJECT = 1;
    private final String DIRECTOR_ROLE = "Director";
    private final String CODIRECTOR_ROLE = "Codirector";
    
    public void showBtnApplyToProject(){
        ProjectDAO projectDAO = new ProjectDAO();
        int totalProjectSelected = VALUE_BY_DEFAULT;
        int existenceProjectApplication = VALUE_BY_DEFAULT;
        
        try {
            totalProjectSelected = projectDAO.getTotalProjectSelectedByStudent(6);
            existenceProjectApplication = projectDAO.getExistenceApplicationToProject(6, ID_PROJECT);
        } catch (SQLException ex) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(existenceProjectApplication > VALUE_BY_DEFAULT){
            setInvisibleBtnApply("Ya te has postulado a este anteproyecto");
        }else if(totalProjectSelected > LIMIT_STUDENT_PROJECT_APPLY){
            setInvisibleBtnApply("No te puedes postular a más anteproyectos");
        }
        
        
    }
    
    public void setInvisibleBtnApply(String message){
        btnApply.setDisable(false);
        btnApply.setVisible(false);

        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        lblMessage.setStyle("-fx-font-weight: bold");
        vboxBtnApplyContent.getChildren().add(lblMessage);
    }
    
    //this method has a high complexity due to the validations cause it mantains data integrity.
    public void showProjectData(){
    /*    Project project = new Project();
        ProjectDAO projectDAO = new ProjectDAO();
            
        try {
            project = projectDAO.getProject(ID_PROJECT);
            project.setAcademicBody(projectDAO.getAcademicBodyByProject(ID_PROJECT));
            project.setLgac(projectDAO.getLgacByProject(ID_PROJECT));
            project.setDirector(projectDAO.getDirectorsByProject(ID_PROJECT));
            
        } catch (SQLException ex) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblAcademicBodyData.setText(project.getAcademicBody().getKey() + " - " + project.getAcademicBody().getName());
        ntxtBibliographyData.setText(project.getBibliography());
        lblDurationData.setText(String.valueOf(project.getDuration()) + " meses");
        ntxtExpectedResultsData.setText(project.getExpectedResults());
        ntxtPladeaFeiName.setText(project.getPladeaFeiName());
        
        boolean codirectorExistence = false;
        
        for(int i = 0; i < project.getDirector().size(); i++){
            if(project.getDirector().get(i).getRole().equals(DIRECTOR_ROLE)){
                lblDirectorData.setText(project.getDirector().get(i).getHonorificTitle() + " " + project.getDirector().get(i).getName() + " " + project.getDirector().get(i).getLastName());
            }
            if(project.getDirector().get(i).getRole().equals(CODIRECTOR_ROLE)){
                Label lblCodirectorData = new Label();
                lblCodirectorData.setText(project.getDirector().get(i).getHonorificTitle() + " " + project.getDirector().get(i).getName() + " " + project.getDirector().get(i).getLastName());
                vboxCodirectorDataContent.getChildren().add(lblCodirectorData);
                codirectorExistence = true;
            }
        }
        
        if(codirectorExistence){
            lblCodirector.setVisible(true);
        }
        
        for(int i = 0; i < project.getLgac().size(); i++){
            Label lblLgacData = new Label();
            lblLgacData.setText(project.getLgac().get(i).getId() + " - " + project.getLgac().get(i).getName());
            vboxLgacDataContent.getChildren().add(lblLgacData);
        }
        
        lblModalityData.setText(project.getModality());
        ntxtNotesData.setText(project.getNotes());
        lblParticipantsData.setText(String.valueOf(project.getSpaces()) + " participante(s)");
        
        if(project.getDescription() != null){
            ntxtProjectDescriptionData.setText(project.getDescription());
            lblProjectDescription.setVisible(true);
        }        
        lblProjectTitle.setText(project.getName());
        ntxtReceptionalWorkDescription.setText(project.getReceptionalWorkDescription());
        ntxtRequirementsData.setText(project.getRequeriments());
*/
    }
    
    public void applyToProject(){
        ProjectDAO projectDAO = new ProjectDAO();
        
        int resultApply = VALUE_BY_DEFAULT;
        
        try {
            resultApply = projectDAO.applyToProject(1, 6);
        } catch (SQLException ex) {
            Logger.getLogger(AvailableProjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(resultApply > VALUE_BY_DEFAULT){
            DialogGenerator.getDialog(new AlertMessage ("Te has postulado correctamente",Status.SUCCESS));
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/AvailableProjectsCatalog.fxml");
        }else{
            DialogGenerator.getDialog(new AlertMessage ("No fue posible realizar la postulación",Status.SUCCESS));
        }
        
    }
    
}
