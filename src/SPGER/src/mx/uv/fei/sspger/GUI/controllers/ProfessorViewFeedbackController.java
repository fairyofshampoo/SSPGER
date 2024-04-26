package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;

public class ProfessorViewFeedbackController implements Initializable {

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Pane pnFeedback;

    private static int idSubmission;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showFeedbackPane();
    }

    public static void setIdSubmission(int idSubmission) {
        ProfessorViewFeedbackController.idSubmission = idSubmission;
    }

    private void showFeedbackPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/FeedbackPane.fxml"));
            Pane pnFeedbackParent = fxmlLoader.load();
            FeedbackPaneController feedbackController = fxmlLoader.getController();
            feedbackController.setFeedbackData(idSubmission);
            pnFeedback.getChildren().add(pnFeedbackParent);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorViewFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    @FXML
    private void goBackButtonClicked(ActionEvent event) {
        SPGER.setRoot("StudentSubmissionDetailsForProfessor.fxml");
    }

}
