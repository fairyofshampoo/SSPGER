package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Feedback;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;

public class StudentSubmissionDetailsForProfessorController implements Initializable {

    private static int assignmentId;

    @FXML
    private ImageView imgBackButton;

    @FXML
    private Text nTextAssignmentDescription;

    @FXML
    private Label lblAssignmentTitle;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;

    @FXML
    private Pane pnSubmission;

    @FXML
    private Button btnAddFeedback;

    @FXML
    private Label lblAssignmentNotFinished;

    @FXML
    private Pane pnSubmissionNotFound;

    private final int NOT_FOUND = -4;
    private final String FINISH_STATUS = "Finalizada";
    private int idSubmission;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Assignment assignment = getAssignment();

        lblAssignmentTitle.setText(assignment.getTitle());
        lblState.setText(assignment.getState());
        lblDeadline.setText("" + getDateFormated(assignment.getDeadline()));
        lblStartDate.setText("" + getDateFormated(assignment.getStartDate()));
        lblPublicationDate.setText("" + getDateFormated(assignment.getPublicationDate()));
        nTextAssignmentDescription.setText(assignment.getDescription());

        verifyAssignmentStatus(assignment.getState());

    }

    public String getDateFormated(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    public void verifyAssignmentStatus(String state) {

        if (state.equals(FINISH_STATUS)) {
            searchSubmission();
        } else {
            lblAssignmentNotFinished.setVisible(true);
        }
    }

    public static void setAssignmentId(int assignmentId) {
        StudentSubmissionDetailsForProfessorController.assignmentId = assignmentId;
    }

    private Assignment getAssignment() {
        Assignment assignment = new Assignment();
        AssignmentDAO assignmentDao = new AssignmentDAO();

        try {
            assignment = assignmentDao.getAssignmentById(assignmentId);
        } catch (SQLException sqlException) {
            Logger.getLogger(StudentSubmissionDetailsForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return assignment;
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
            Logger.getLogger(StudentSubmissionDetailsForProfessorController.class.getName()).log(Level.SEVERE, null, ex);
            showFXMLFileFailedAlert();
        }
    }

    private void searchSubmission() {
        Submission submission = new Submission();
        SubmissionManagerDAO submissionDAO = new SubmissionManagerDAO();
        try {
            submission = submissionDAO.getSubmissionPerAssignment(assignmentId);
            if (submission.getId() == NOT_FOUND) {
                pnSubmission.setVisible(false);
                pnSubmissionNotFound.setVisible(true);
                btnAddFeedback.setVisible(false);

            } else {
                idSubmission = submission.getId();
                showSubmissionPane(submission);
                btnAddFeedback.setVisible(true);
                changeFeedbackButton();
            }

        } catch (SQLException ex) {
            Logger.getLogger(StudentSubmissionDetailsForProfessorController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void changeFeedbackButton() {
        if (isThereAFeedback()) {
            btnAddFeedback.setText("Ver retroalimentación");
        } else {
            btnAddFeedback.setDisable(true);
            btnAddFeedback.setText("Sin retroalimentación");
        }
    }

    @FXML
    void goBack(MouseEvent event) {
        SPGER.setRoot("ViewProjectForProfessor.fxml");
    }

    @FXML
    void mouseEnteredBackButtonArea(MouseEvent event) {

    }

    @FXML
    void mouseExitedBackButtonArea(MouseEvent event) {

    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    @FXML
    private void seeFeedbackButtonClicked(ActionEvent event) {
        if (isThereAFeedback()) {
            ProfessorViewFeedbackController.setIdSubmission(idSubmission);
            SPGER.setRoot("ProfessorViewFeedback.fxml");
        }
    }

    private boolean isThereAFeedback() {
        boolean feedbackFound = true;
        try {
            SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
            Feedback feedback = submissionDao.getFeedbackBySubmission(idSubmission);
            if (feedback.getId() == NOT_FOUND) {
                feedbackFound = false;
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(StudentSubmissionDetailsForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return feedbackFound;
    }
}
