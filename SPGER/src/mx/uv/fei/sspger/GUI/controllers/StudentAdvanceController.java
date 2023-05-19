package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;


public class StudentAdvanceController implements Initializable {
    public static int assignmentId;
    public static String assignmentState;
    
    @FXML
    private ImageView imgBackButton;
    
    @FXML
    private Text nTextAssignmentDescription;

    @FXML
    private Text nTextSubmissionDescription;

    @FXML
    private Label lblAssignmentTitle;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblDeliveryDate;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Assignment assignment = getAssignment();
        
        lblAssignmentTitle.setText(assignment.getTitle());
        lblState.setText(assignment.getState());
        lblDeadline.setText("" + assignment.getDeadline());
        lblStartDate.setText("" + assignment.getStartDate());
        lblPublicationDate.setText("" + assignment.getPublicationDate());
        nTextAssignmentDescription.setText(assignment.getDescription());
        
    }   
    
    private Assignment getAssignment(){
        Assignment assignment = new Assignment();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        try{
            assignment = assignmentDao.getAssignmentById(assignmentId);
        } catch (SQLException sqlException){
            Logger.getLogger(StudentAdvanceController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        return assignment;
    }

    @FXML
    void goBack(MouseEvent event) {

    }

    @FXML
    void mouseEnteredBackButtonArea(MouseEvent event) {

    }

    @FXML
    void mouseExitedBackButtonArea(MouseEvent event) {

    } 
    
}
