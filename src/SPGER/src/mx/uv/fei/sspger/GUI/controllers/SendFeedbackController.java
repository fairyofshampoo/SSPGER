package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;
import java.sql.SQLException;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Feedback;

public class SendFeedbackController implements Initializable {

    private final int NOT_FOUND = -4;
    private final int ERROR_ADDITION = -1;
    private static int idSubmission;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnSendFeedback;
    
    @FXML
    private Label lblTitleSystem;
    
    @FXML
    private TextArea txtFeedbackComment;
    
    @FXML
    private Pane pnSubmission;
    
    @FXML
    private Label lblCommentInvalid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchSubmission();
    }

    @FXML
    private void typingFeedback(KeyEvent event) {
        lblCommentInvalid.setVisible(false);
    }

    @FXML
    private void sendFeedbackClicked(ActionEvent event) {
        setFeedbackData();
    }
    
    public static void setIdSubmission(int idSubmission) {
        SendFeedbackController.idSubmission = idSubmission;
    }

    private void setFeedbackData() {
        Feedback feedback = new Feedback();
        feedback.getSubmission().setId(idSubmission);
        boolean validation = true;
        try {
            feedback.setComment(txtFeedbackComment.getText());
        } catch (IllegalArgumentException commentException) {
            lblCommentInvalid.setVisible(true);
            validation = false;
        }

        if (validation == true) {
            registerFeedback(feedback);
        }
    }

    private void registerFeedback(Feedback feedback) {
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
        try {
            if (submissionDao.addFeedback(feedback) == ERROR_ADDITION) {
                DialogGenerator.getDialog(new AlertMessage("No se ha podido registrar la retroalimentación", Status.ERROR));
            } else {
                DialogGenerator.getDialog(new AlertMessage("Registro exitoso de retroalimentación", Status.SUCCESS));
                showStudentSubmissionDetails();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SendFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    @FXML
    private void cancelFeedbackClicked(ActionEvent event) {
        showStudentSubmissionDetails();
    }

    private void showStudentSubmissionDetails() {
        SPGER.setRoot("StudentSubmissionDetails.fxml");
    }

    private void showSubmissionPane(Submission submission) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/SubmissionPane.fxml"));
            Pane pnSubmissionParent = fxmlLoader.load();
            SubmissionPaneController submissionController = fxmlLoader.getController();
            submissionController.setSubmissionData(submission);

            pnSubmission.getChildren().add(pnSubmissionParent);
        } catch (IOException ex) {
            Logger.getLogger(SendFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            showFXMLFileFailedAlert();
        }
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    private void searchSubmission() {
        Submission submission = new Submission();
        SubmissionManagerDAO submissionDAO = new SubmissionManagerDAO();
        try {
            submission = submissionDAO.getSubmissionById(idSubmission);
            if (submission.getId() == NOT_FOUND) {
                DialogGenerator.getDialog(new AlertMessage("Entrega no encontrada", Status.ERROR));
            }
            submission.setId(idSubmission);
            showSubmissionPane(submission);

        } catch (SQLException ex) {
            Logger.getLogger(SendFeedbackController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

}
