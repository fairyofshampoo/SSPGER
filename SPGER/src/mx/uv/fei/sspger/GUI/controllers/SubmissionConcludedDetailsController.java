package mx.uv.fei.sspger.GUI.controllers;


import com.itextpdf.text.Document;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;


public class SubmissionConcludedDetailsController implements Initializable {
    
    @FXML
    private Button btnAddFeedback;

    @FXML
    private ImageView imgDownload;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private ImageView imgPdfFile;

    @FXML
    private Label lblAssignmentTitle;

    @FXML
    private Label lblDeliveryDate;

    @FXML
    private Label lblDescriptionAssignment;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblFileTitle;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Text ntxtDescriptionAssignment;

    @FXML
    private Text ntxtDescriptionSubmission;
    
    private int idAssignment = 14;
    private int idSubmission;

    public void setIdAssignment(int idAssignment) {
        this.idAssignment = idAssignment;
    }

    public void setIdSubmission(int idSubmission) {
        this.idSubmission = idSubmission;
    }

    public int getIdAssignment() {
        return idAssignment;
    }

    public int getIdSubmission() {
        return idSubmission;
    }

    @FXML
    void addFeedBack(ActionEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/SendFeedback.fxml");
    }

    @FXML
    void downloadImageClicked(MouseEvent event) {
        Document submissionDocument = new Document();
            Stage DirectoryStage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(DirectoryStage);

            if(selectedDirectory == null){
                DialogGenerator.getDialog(new AlertMessage (
                    "No se seleccionó directorio para la descarga",
                    Status.WARNING));
            } else {
                copyDocument(selectedDirectory.getAbsolutePath());
            }
    }
    
    private void copyDocument(String selectedDirectory){
            DeliverableFile deliverableFile = new DeliverableFile();
    }
    
    @FXML
    void goBack(MouseEvent event) {

    }
    
    private void displayAssignmentInfo(){
        try {
            AssignmentDAO assignmentDAO = new AssignmentDAO();
            Assignment assignment = assignmentDAO.getAssignmentById(idAssignment);
            lblAssignmentTitle.setText(assignment.getTitle());
            ntxtDescriptionAssignment.setText(assignment.getDescription());
            lblPublicationDate.setText(formatDate(assignment.getPublicationDate()));
            lblStartDate.setText(formatDate(assignment.getStartDate()));
            lblDeadline.setText(formatDate(assignment.getDeadline()));
            lblState.setText(assignment.getState());
            setIdSubmission(assignment.getIdSubmission());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
    
    private void displaySubmissionInfo(){
        try {
            SubmissionManagerDAO submissionDAO = new SubmissionManagerDAO();
            Submission submission = submissionDAO.getSubmissionById(getIdSubmission());
            ntxtDescriptionSubmission.setText(submission.getDescription());
            lblDeliveryDate.setText(formatDate( submission.getDeliveryDate()));
            
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayAssignmentInfo();
        displaySubmissionInfo();
    }    
    
}
