package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.MainApplication;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Student;


public class ViewProjectForProfessorController implements Initializable {
    
    public static Student student;
    private Project project;
    private int column = 0;
    private int row = 0;
    
    @FXML
    private GridPane gpAssignmentContainer;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgStudent;

    @FXML
    private Label lblCoodirector;

    @FXML
    private Label lblDirector;

    @FXML
    private Label lblProjectName;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblStudentRegistrationTag;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
        setAssignments();
    }   
    
    private void setGraphicElements(){
        ProjectDAO projectDao = new ProjectDAO();
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor director = new Professor();
        List<Professor> coodirectors = new ArrayList<>();
        String coodirectorsName = "Coodirector(es): ";
        String directorName= "Director: ";
        
        try{
            project = projectDao.getProjectByStudent(student.getId());
            director = professorDao.getDirectorByProject(project.getIdProject());
            coodirectors = professorDao.getCoodirectorByProject(project.getIdProject());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        if(director.getName() != null){
            directorName = directorName + director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName();
        }
        
        for(Professor coodirector: coodirectors){           
            coodirectorsName = coodirectorsName + coodirector.getHonorificTitle() + " " + coodirector.getName() + " " + coodirector.getLastName() + " ";
        }
        
        lblProjectName.setText(project.getName());
        lblDirector.setText(directorName);
        lblCoodirector.setText(coodirectorsName);
        lblStudentName.setText(student.getName() + " " + student.getLastName());
        lblStudentRegistrationTag.setText(student.getRegistrationTag());
        imgBack.setImage(ImagesSetter.getGoBackImage());
        imgStudent.setImage(ImagesSetter.getUserIconImage());
        
    }

    private void setAssignments() {
        List<Assignment> studentAssignments = getStudentAssignments();
        
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
                
                gpAssignmentContainer.add(assignmentCard, column, row);
                GridPane.setMargin(assignmentCard, new Insets(15));
            }
        
        } catch (IOException ioException){
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
    private List<Assignment> getStudentAssignments() {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        List<Assignment> studentAssignments = new ArrayList<>();
        
        try{
            studentAssignments = assignmentDao.getAssignmentsPerProject(project.getIdProject());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        return studentAssignments;
    }
    
    @FXML
    private void goBack(MouseEvent event) {
        try {
            MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ProfessorCourseView");
        } catch (IOException ex) {
            Logger.getLogger(ProfessorCourseViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void mouseEnteredBackImageArea(MouseEvent event) {
       imgBack.setCursor(Cursor.HAND);
    }

    @FXML
    private void mouseExitedBackImageArea(MouseEvent event) {
        imgBack.setCursor(Cursor.DEFAULT);
    }
    
}
