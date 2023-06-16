package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;


public class AssignmentCardController {
    private int assignmentId;
        
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
        StudentAdvanceController.assignmentId = assignmentId;
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/StudentAdvance");
    }
    
    public void setAssignmentCard(Assignment assignment){
        assignmentId = assignment.getId();
        lblAssignmentTitle.setText(assignment.getTitle());
        lblDeadline.setText("Fecha fin: " + assignment.getDeadline());
        lblPublicationDate.setText("Fecha publicación: " + assignment.getPublicationDate());
        lblStartDate.setText("Fecha inicio: " + assignment.getStartDate());
        lblState.setText("Estado: " + assignment.getState());
    }
    
}
