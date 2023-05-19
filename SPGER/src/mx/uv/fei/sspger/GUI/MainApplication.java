package mx.uv.fei.sspger.GUI;


import mx.uv.fei.sspger.GUI.controllers.ModifyAssignmentController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.controllers.DirectorReceptionalWorkController;
import mx.uv.fei.sspger.GUI.controllers.ProfessorCourseManagerController;
import mx.uv.fei.sspger.GUI.controllers.ProfessorCourseViewController;
import mx.uv.fei.sspger.GUI.controllers.RegisterAssignmentController;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;


public class MainApplication extends Application{

    private static Scene scene;
    
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        Professor professor = new Professor();
        Project project = new Project();
        Assignment assignment = new Assignment();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
                
        project.setIdProject(1);
        project.setName("Control Estadístico de Procesos en el desarrollo de");
        
        professor.setId(1);    
        
        RegisterAssignmentController.professor = professor;
        RegisterAssignmentController.project = project;
        
        ModifyAssignmentController.assignment = assignment;
        ModifyAssignmentController.professor = professor;
        ModifyAssignmentController.project = project;
        
        //Parent root = FXMLLoader.load(getClass().getResource("modifyAssignment.fxml"));
        
        //Parent root = FXMLLoader.load(getClass().getResource("RegisterAssignment.fxml"));
        
      // ProfessorCourseManagerController.professorId = professor.getId();
      //Parent root = FXMLLoader.load(getClass().getResource("ProfessorCourseManager.fxml"));
      
        DirectorReceptionalWorkController.idProfessor = professor.getId();
        Parent root = FXMLLoader.load(getClass().getResource("DirectorReceptionalWork.fxml"));
        
        //Parent root = FXMLLoader.load(getClass().getResource("AddCourse.fxml"));
        
        scene = new Scene (root);
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void setRoot(String fxml) throws IOException{
        try{
            scene.setRoot(loadFXML(fxml));
        } catch (IOException ioException){
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ioException);
        }
        
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
}
