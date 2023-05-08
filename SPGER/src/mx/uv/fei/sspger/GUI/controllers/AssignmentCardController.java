package mx.uv.fei.sspger.GUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.uv.fei.sspger.logic.Assignment;


public class AssignmentCardController {
        
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
        
    }
    
    public void setAssignmentCard(Assignment assignment){
        lblAssignmentTitle.setText(assignment.getTitle());
        lblDeadline.setText("Fecha fin: " + assignment.getDeadline());
        lblPublicationDate.setText("Fecha publicación: " + assignment.getPublicationDate());
        lblStartDate.setText("Fecha inicio: " + assignment.getStartDate());
        lblState.setText("Estado: " + assignment.getState());
    }
    
}
