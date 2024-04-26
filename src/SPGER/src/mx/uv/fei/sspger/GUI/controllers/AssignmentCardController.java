package mx.uv.fei.sspger.GUI.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;

public class AssignmentCardController {

    private int assignmentId;
    private int idReceptionalWork;

    @FXML
    private Button btnProfessorDetails;

    @FXML
    private Button btnDirectorDetails;

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
    private void viewAssignmentDetails(ActionEvent event) {
        StudentSubmissionDetails.setAssignmentId(assignmentId);
        StudentSubmissionDetails.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("StudentSubmissionDetails.fxml");
    }

    @FXML
    private void viewAssignmentProfessorDetails(ActionEvent event) {
        StudentSubmissionDetailsForProfessorController.setAssignmentId(assignmentId);
        SPGER.setRoot("StudentSubmissionDetailsForProfessor.fxml");
    }

    public void setAssignmentCard(Assignment assignment, int idReceptionalWork) {
        assignmentId = assignment.getId();
        lblAssignmentTitle.setText(assignment.getTitle());
        lblDeadline.setText("Fecha fin: " + getDateFormated(assignment.getDeadline()));
        lblPublicationDate.setText("Fecha publicación: " + getDateFormated(assignment.getPublicationDate()));
        lblStartDate.setText("Fecha inicio: " + getDateFormated(assignment.getStartDate()));
        lblState.setText("Estado: " + assignment.getState());
        this.idReceptionalWork = idReceptionalWork;
    }

    public void setProfessorView() {
        btnDirectorDetails.setDisable(true);
        btnDirectorDetails.setVisible(false);
        btnProfessorDetails.setVisible(true);
        btnProfessorDetails.setDisable(false);
    }

    public void setDirectorView() {
        btnDirectorDetails.setDisable(false);
        btnDirectorDetails.setVisible(true);
        btnProfessorDetails.setVisible(false);
        btnProfessorDetails.setDisable(true);
    }

    public String getDateFormated(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

}
