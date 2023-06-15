package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;


public class SendFeedbackController implements Initializable {
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSendFeedback;

    @FXML
    private ImageView imgDownload;

    @FXML
    private ImageView imgPdfFile;

    @FXML
    private Label lblDeliveryDate;

    @FXML
    private Label lblFileTitle;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Text ntxtDescriptionSubmission;

    @FXML
    private TextArea txtFeedbackComment;

    @FXML
    void cancelFeedback(ActionEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/SubmissionConcludedDetails.fxml");
    }

    @FXML
    void downloadImageClicked(MouseEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
