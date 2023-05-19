package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class SubmissionConcludedDetailsController implements Initializable {
    @FXML
    private Button btnAddFeedback;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSendFeedback;

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
    private Label lblEndDate;

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

    @FXML
    private TextArea txtFeedbackComment;

    @FXML
    void downloadImageClicked(MouseEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
