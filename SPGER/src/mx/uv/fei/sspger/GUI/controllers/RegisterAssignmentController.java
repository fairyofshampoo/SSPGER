package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import mx.uv.fei.sspger.GUI.AlertMessage;
import mx.uv.fei.sspger.GUI.DialogGenerator;
import mx.uv.fei.sspger.GUI.MainApplication;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;


public class RegisterAssignmentController implements Initializable {
    public static Professor professor;
    public static Project project;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private DatePicker dtpDeadline;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private Label lblProyectName;
    
    @FXML
    private Label lblRemainingCharacters;
    
    @FXML
    private Label lblMissingAssignmentTitle;

    @FXML
    private Label lblMissingStartDate;
    
    @FXML
    private Label lblMissingDeadline;
    
    @FXML
    private TextArea txtAssignmentDescription;

    @FXML
    private TextField txtAssignmentTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblProyectName.setText("Anteproyecto: " + project.getName());
        
    }     
    
    @FXML
    void cancelAssignmentCreation(ActionEvent event) throws IOException {
        if (isConfirmedExit()){
            MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ProjectAssignments");
        }
    }

    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de agregar curso?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    @FXML
    void createAssignment(ActionEvent event) {
        if(validFields()){
            Assignment assignment = new Assignment();
            AssignmentDAO assignmentDao = new AssignmentDAO();
            
            setAssignment(assignment);
            
            //ViewAssignment(SetAssignmentId);
            
            try{
                if(assignmentDao.registerAssignment(assignment, professor.getId(), project.getIdProject()) == 1){
                    //MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ViewCourse");
                    
                    DialogGenerator.getDialog(new AlertMessage (
                        "Asignación creada exitosamente.",
                        Status.SUCCESS));
                } else {
                    DialogGenerator.getDialog(new AlertMessage (
                        "Algo salio mal. \n\t:/",
                        Status.WARNING));
                }
                
            }catch (SQLException sqlException){
                DialogGenerator.getDialog(new AlertMessage (
                        "Problema en la conexion a la base de datos.",
                        Status.FATAL));
            }            
        }
    }
    
    private boolean validFields(){
        boolean result = true;
        LocalDate startDate = dtpStartDate.getValue();
        LocalDate deadline = dtpDeadline.getValue();
        
        if(FieldValidation.doesNotExceedLenghtTxtArea(txtAssignmentDescription, 5000)){
            result = false;
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtAssignmentTitle)){
            result = false;
            lblMissingAssignmentTitle.setVisible(true);
        }
        if(dtpDeadline.getValue() == null){
            result = false;
            lblMissingDeadline.setVisible(true);
        }
        if(dtpStartDate.getValue() == null){
            result = false;
            lblMissingStartDate.setVisible(true);
        }
        if(result && startDate.isAfter(deadline)){
            result = false;
            //lbl
        }
        
        return result;
    }
    
    private void setAssignment(Assignment assignment){
        assignment.setTitle(txtAssignmentTitle.getText());
        assignment.setDescription(txtAssignmentDescription.getText());
        assignment.setStartDate(setDate(dtpStartDate));
        assignment.setDeadline(setDate(dtpDeadline));
        assignment.setProfessorId(professor.getId());
        assignment.setIdProject(project.getIdProject());
    }
    
    private java.sql.Date setDate(DatePicker datePicker){
        LocalDate localDate = datePicker.getValue();
        
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        
        return sqlDate;
    }
    
    @FXML
    private void remainingCharactersInDescription(KeyEvent keyEvent){
        txtAssignmentDescription.textProperty().addListener((observable, oldValue, newValue) ->{
            int remainingCharacters = 5000 - txtAssignmentDescription.getText().length(); 
            lblRemainingCharacters.setText("Caracteres disponibles: " + remainingCharacters);
        });
        if(FieldValidation.doesNotExceedLenghtTxtArea(txtAssignmentDescription, 20)){
            lblRemainingCharacters.setStyle("-fx-text-fill: red");
        }
    }
    
    
}