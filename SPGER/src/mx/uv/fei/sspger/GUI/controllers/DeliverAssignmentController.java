package mx.uv.fei.sspger.GUI.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import mx.uv.fei.sspger.logic.DAO.DeliverableFileDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;


public class DeliverAssignmentController implements Initializable {
    final String FINAL_PATH ="C:\\Users\\miche\\OneDrive - Universidad Veracruzana\\Cuarto semestre\\Principios de Construcción de Software\\SPGER\\SPGER\\src\\mx\\uv\\fei\\sspger\\GUI\\resources\\files\\";
    final int SUBSTRING_START = 1;
    private DeliverableFile deliverableFile;
    
    @FXML
    private Button btnAddFile;

    @FXML
    private Button btnAddSubmission;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblAsignmentTitle;

    @FXML
    private Label lblDescriptionAsignment;

    @FXML
    private Label lblEndDate;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Text ntxtDescription;

    @FXML
    private TextArea txtSubmission;
    
    @FXML
    void addSubmission(ActionEvent event) {
        java.sql.Date currentDate = getDeliverDate();
        Submission submission = new Submission();
        SubmissionManagerDAO submissionDAO = new SubmissionManagerDAO();
    }

    @FXML
    void uploadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File submissionFile = fileChooser.showOpenDialog(null);
        Path sourcePath = Paths.get(submissionFile.getAbsolutePath());
        Path finalPath = Paths.get(FINAL_PATH+submissionFile.getName());
        
        if(submissionFile != null){
            String fileName = submissionFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")+ SUBSTRING_START, fileName.length());
            DeliverableFile deliverableFile = new DeliverableFile();
            DeliverableFileDAO deliverableFileDAO = new DeliverableFileDAO();
            
            try {
                Files.copy(sourcePath, finalPath);
            } catch (IOException ex) {
                Logger.getLogger(DeliverAssignmentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            deliverableFile.setName(fileName);
            deliverableFile.setPath(FINAL_PATH);
            deliverableFile.setExtension(fileExtension);
            this.deliverableFile = deliverableFile;
            DialogGenerator.getDialog(new AlertMessage (
                    "Archivo subido exitosamente", Status.SUCCESS));
        } else{
            DialogGenerator.getDialog(new AlertMessage (
                "Archivo faltante. Seleccione un archivo",
                Status.WARNING));
        }
    }
    
    private java.sql.Date getDeliverDate() {
        Date currentDate = new Date();
        return new java.sql.Date(currentDate.getTime());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
