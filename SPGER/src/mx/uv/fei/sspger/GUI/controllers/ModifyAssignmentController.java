package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import mx.uv.fei.sspger.GUI.controllers.FieldValidation;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Status;


public class ModifyAssignmentController implements Initializable {
    
    public static Professor professor;
    public static Project project;
    public static int idAssignment;
    private Assignment assignment;
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
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        try{
            assignment = assignmentDao.getAssignmentById(idAssignment);
        } catch (SQLException sqlException){
            Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        lblProyectName.setText("Anteproyecto: " + project.getName());
        txtAssignmentDescription.setText(assignment.getDescription());
        txtAssignmentTitle.setText(assignment.getTitle());
        
        Date dateDeadline = assignment.getDeadline();
        LocalDate deadline = dateDeadline.toLocalDate();
        
        dtpDeadline.setValue(deadline);
        
        Date dateStartDate = assignment.getStartDate();
        LocalDate startDate = dateStartDate.toLocalDate();
        
        dtpStartDate.setValue(startDate);
    }     
    
    @FXML
    void cancelAssignmentCreation(ActionEvent event) throws IOException {
        if (isConfirmedExit()){
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork");
        }
    }

    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de modificar asignación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    @FXML
    void updateAssignment(ActionEvent event) throws SQLException{
        if(validFields()){
            AssignmentDAO assignmentDao = new AssignmentDAO();
            
            setAssignment();
            //ViewAssignment(SetAssignmentId);
            
            try{
                if(assignmentDao.updateAssignment(assignment) == 1){
                    //MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ViewCourse");
                    
                    DialogGenerator.getDialog(new AlertMessage (
                        "Asignación modificada exitosamente.",
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
                Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
                System.out.println(sqlException);
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
        if(startDate != null && deadline != null){
            
            if(startDate.isAfter(deadline)){
                result = false;
                DialogGenerator.getDialog(new AlertMessage (
                    "La fecha de inicio debe ir antes del fin de la actividad.",
                    Status.ERROR));
                }
            if(LocalDate.now().isAfter(startDate)){
                result = false;
                DialogGenerator.getDialog(new AlertMessage (
                    "La fecha de inicio debe ir despues de la fecha actual.",
                    Status.ERROR));
            } 
        } else {
            result = false;
        }
        
        return result;
    }
    
    private void setAssignment(){
        assignment.setTitle(txtAssignmentTitle.getText());
        assignment.setDescription(txtAssignmentDescription.getText());
        assignment.setStartDate(setDate(dtpStartDate));
        assignment.setDeadline(setDate(dtpDeadline));
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
    
    @FXML
    private void setMissingAssignmentTitleInvisible (KeyEvent keyEvent){
        lblMissingAssignmentTitle.setVisible(false);
    }
    
    @FXML
    private void setMissingStartDateInvisible (ActionEvent event){
        lblMissingStartDate.setVisible(false);
    }
    
    @FXML
    private void setMissingDeadlineInvisible (ActionEvent event){
        lblMissingDeadline.setVisible(false);
    }
    
      @FXML
    void removeAssignment(ActionEvent event) {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        try{
            if(assignmentDao.deleteAssignment(assignment) == 1){
               //MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ViewCourse");
                
                DialogGenerator.getDialog(new AlertMessage (
                    "Asignación eliminada exitosamente.",
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
