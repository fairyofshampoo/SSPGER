package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.MainApplication;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;


public class ViewReceptionalWorkController implements Initializable {
    public static int idReceptionalWork;
    private int column = 0;
    private int row = 1;
    
    @FXML
    private GridPane gpAssignments;

    @FXML
    private Label lblReceptionalWorkName;

    @FXML
    private Text nTextCoodirectorsName;

    @FXML
    private Text nTextDirectorName;

    @FXML
    private Text nTextStudentsName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String studentsName;
        String directorName;
        String codirectorsName;
        String receptionalWorkName;
        
        try{
            ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
            ReceptionalWork receptionalWork = receptionalWorkDao.getRecepetionalWorkById(idReceptionalWork);
            studentsName = getStudentsName();
            directorName = getDirectorName(receptionalWork.getIdProject());
            codirectorsName = getCodirectorsName(receptionalWork.getIdProject());
            receptionalWorkName = receptionalWork.getName();
            
            lblReceptionalWorkName.setText(receptionalWorkName);
            nTextStudentsName.setText("Estudiantes: " + studentsName);
            nTextCoodirectorsName.setText("Coodirectores: " + codirectorsName);
            nTextDirectorName.setText("Director: " + directorName);
            setAssignments();
            
        } catch (SQLException sqlException){
            Logger.getLogger(ViewReceptionalWorkController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }  
    
    private String getStudentsName() throws SQLException{
        StudentDAO studentDao = new StudentDAO();
        List <Student> studentsInReceptionalWork = studentDao.getStudentPerReceptionalWork(idReceptionalWork);
        String studentsName = "";
        
        for(Student student : studentsInReceptionalWork){
            studentsName = studentsName + student.getName() + " " + student.getLastName() + ", ";
        }
        
        return studentsName;
    }

    private String getDirectorName(int idProject) throws SQLException{
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = professorDao.getDirectorByProject(idProject);
        
        String directorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();
        
        return directorName;
    }
    
    private String getCodirectorsName(int idProject) throws SQLException{
        ProfessorDAO professorDao = new ProfessorDAO();
        List <Professor> codirectorsList = professorDao.getCoodirectorByProject(idProject);
        String codirectorsName = "";
        
        for(Professor professor : codirectorsList){
            codirectorsName = codirectorsName + professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName() + " ";
        }
        
        return codirectorsName;
    }
    
    private void setAssignments() throws SQLException{
        AssignmentDAO assignmentDao = new AssignmentDAO();
        List<Assignment> studentAssignments = assignmentDao.getAssignmentsPerReceptionalWork(idReceptionalWork);
        
        try{
            for(Assignment assignment : studentAssignments){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AssignmentCard.fxml"));
                Pane assignmentCard = fxmlLoader.load();
                AssignmentCardController assignmentCardController = fxmlLoader.getController();
                assignmentCardController.setAssignmentCard(assignment);
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                
                gpAssignments.add(assignmentCard, column++, row);
                GridPane.setMargin(assignmentCard, new Insets(10));
            }
        
        } catch (IOException ioException){
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
    @FXML
    void addAssignment(ActionEvent event) {
        
    }

    @FXML
    void addSinodal(ActionEvent event) {
        
    }

    @FXML
    void createReport(ActionEvent event) {
        
    }

    @FXML
    void modifyReceptionalWork(ActionEvent event) {
        
    }
    
    @FXML
    void goBack(MouseEvent event) {
        try {
            MainApplication.setRoot("/mx/uv/fei/sspger/GUI/DirectorReceptionalWork");
        } catch (IOException ex) {
            Logger.getLogger(ProfessorCourseViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
