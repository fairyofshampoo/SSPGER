package mx.uv.fei.sspger.GUI.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;

public class AssignmentStudentCardController {

    @FXML
    private Label lblTitleAssignment;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private HBox hboxAssignment;

    @FXML
    private Pane pnAssignmentCard;

    private int idAssignment;

    public void setAssignmentData(Assignment assignment) {
        lblTitleAssignment.setText(assignment.getTitle());
        lblStatus.setText(assignment.getState());
        lblStartDate.setText(getDateFormated(assignment.getStartDate()));
        lblDeadline.setText(getDateFormated(assignment.getDeadline()));
        lblPublicationDate.setText(getDateFormated(assignment.getPublicationDate()));
        idAssignment = assignment.getId();
    }

    public String getDateFormated(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @FXML
    private void viewDetailsClicked(ActionEvent event) {
        SubmissionConcludedDetailsController.setIdAssignment(idAssignment);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/SubmissionConcludedDetails.fxml");
    }

}
