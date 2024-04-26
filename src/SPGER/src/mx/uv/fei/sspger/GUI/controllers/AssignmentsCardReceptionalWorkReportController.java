package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;

public class AssignmentsCardReceptionalWorkReportController {

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblSubmission;

    @FXML
    private Label lblTitle;

    private final int VALUE_BY_DEFAULT = 0;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public void setData(Assignment assignment) {
        lblTitle.setText(assignment.getTitle());
        lblStartDate.setText(DATE_FORMAT.format(assignment.getStartDate()));
        lblDeadline.setText(DATE_FORMAT.format(assignment.getDeadline()));

        if (assignment.getIdSubmission() == VALUE_BY_DEFAULT) {
            lblSubmission.setText("NO ENTREGADO");
        } else {
            Submission submission = getSubmission(assignment.getIdSubmission());
            lblSubmission.setText(DATE_FORMAT.format(submission.getDeliveryDate()));
        }
    }

    public Submission getSubmission(int idSubmission) {
        Submission submission = new Submission();
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();

        try {
            submission = submissionDao.getSubmissionById(idSubmission);
        } catch (SQLException sqlException) {
            Logger.getLogger(AssignmentsCardReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return submission;
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
