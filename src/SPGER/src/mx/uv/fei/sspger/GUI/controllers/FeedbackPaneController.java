package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Feedback;

public class FeedbackPaneController {

    @FXML
    private Text ntxtCommentFeedback;

    @FXML
    private Pane pnFeedbackParent;

    @FXML
    private Pane pnFeedbackFound;

    @FXML
    private Label lblFeedbackNotFound;

    private final int NOT_FOUND = -4;

    public void setFeedbackData(int idSubmission) {
        try {
            SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
            Feedback feedbackForCard = submissionDao.getFeedbackBySubmission(idSubmission);
            if (feedbackForCard.getId() == NOT_FOUND) {
                lblFeedbackNotFound.setVisible(true);
                pnFeedbackFound.setVisible(false);
            } else {
                ntxtCommentFeedback.setText(feedbackForCard.getComment());
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackPaneController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
    }

}
