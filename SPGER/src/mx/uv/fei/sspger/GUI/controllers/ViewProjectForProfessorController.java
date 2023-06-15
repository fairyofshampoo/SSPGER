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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;


public class ViewProjectForProfessorController implements Initializable {
    
    public static Student student;
    private ReceptionalWork receptionalWork;
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
    private Label lblReceptionalWorkName;

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
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor director = new Professor();
        List<Professor> coodirectors = new ArrayList<>();
        String coodirectorsName = "Coodirector(es): ";
        String directorName= "Director: ";
        
        try{
            receptionalWork = receptionalWorkDAO.getActiveReceptionalWorkByStudent(student.getId());
            director = professorDao.getDirectorByProject(receptionalWork.getIdProject());
            coodirectors = professorDao.getCoodirectorByProject(receptionalWork.getIdProject());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        if(director.getName() != null){
            directorName = directorName + director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName();
        }
        
        for(Professor coodirector: coodirectors){           
            coodirectorsName = coodirectorsName + coodirector.getHonorificTitle() + " " + coodirector.getName() + " " + coodirector.getLastName() + " ";
        }
        
        lblReceptionalWorkName.setText(receptionalWork.getName());
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
                
                gpAssignmentContainer.add(assignmentCard, column++, row);
                GridPane.setMargin(assignmentCard, new Insets(10));
            }
        
        } catch (IOException ioException){
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }
    
    private List<Assignment> getStudentAssignments() {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        List<Assignment> studentAssignments = new ArrayList<>();
        
        try{
            studentAssignments = assignmentDao.getAssignmentsPerReceptionalWork(receptionalWork.getIdReceptionalWork());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        
        return studentAssignments;
    }
    
    @FXML
    private void goBack(MouseEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ProfessorCourseView");
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
