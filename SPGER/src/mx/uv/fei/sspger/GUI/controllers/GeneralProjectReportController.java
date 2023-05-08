package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;


public class GeneralProjectReportController implements Initializable {
    
    @FXML
    private Button btnDownloadReport;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblAsignmentTitle;

    @FXML
    private Label lblDirectorLeastProjects;

    @FXML
    private Label lblDirectorMostProjects;

    @FXML
    private Label lblLGACLeast;

    @FXML
    private Label lblLGACMost;

    @FXML
    private Label lblModalityLeast;

    @FXML
    private Label lblModalityMost;

    @FXML
    private Label lblProjectsToValidate;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblRejectedProjects;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblValidatedProjects;

    @FXML
    void downloadReport(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
