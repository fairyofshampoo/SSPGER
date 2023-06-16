package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.ReceptionalWorkStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;


public class StudentsApplicantsCatalogController implements Initializable {
    private final int ID_PROJECT = 1;
    private final int VALUE_BY_DEFAULT = 0;
    private static final ReceptionalWorkStatus RECEPTIONAL_WORK_ACTIVE_STATUS = ReceptionalWorkStatus.ACTIVE;
    private static final ProjectStatus PROJECT_VALIDATED_STATUS = ProjectStatus.VALIDATED;
    private static final ProjectStatus PROJECT_ASSIGNED_STATUS = ProjectStatus.ASSIGNED;
    
    @FXML
    private Button btnAcept;
    
    @FXML
    private VBox vboxStudentsContent;

    @FXML
    private Label lblProjectTitle;
    
    @FXML
    private Label lblSpaces;

    @FXML
    void aceptStudentsProject(MouseEvent event) {
        if(isValidSelection()){
            if(!studentAcceptedCardControllerList.isEmpty()){
                expellStudent();
            }
            
            if(!studentPostuledCardControllerList.isEmpty()){
                if(!existenceActiveReceptionalWork(ID_PROJECT)){
                    addReceptionalWork();
                }
                acceptStudentToProject();
            }
            
            closeWindow();
            
        }else{
            DialogGenerator.getDialog(new AlertMessage ("El número de estudiantes seleccionados no coincide con el cupo esperado",Status.ERROR));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }
    
    private List<StudentPostulatedCardController> studentPostuledCardControllerList = new ArrayList<>();
    private List<StudentPostulatedCardController> studentAcceptedCardControllerList = new ArrayList<>();
    
    public void showData(){
        Project project = getProject();
        List<Student> studentPostulatedList = new ArrayList<>();
        List<Student> studentAcceptedList = new ArrayList<>();
        StudentDAO studentDAO = new StudentDAO();
        
        try {
            studentPostulatedList = studentDAO.getStudentsByProjectByStatus(ID_PROJECT, "POSTULADO");
            studentAcceptedList = studentDAO.getStudentsByProjectByStatus(ID_PROJECT, "ACEPTADO");
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblProjectTitle.setText(project.getName());
        lblSpaces.setText(project.getSpaces() + " participante(s) en total");
        
        if(!studentAcceptedList.isEmpty()){
            Label lblStudentAccepted = new Label("ACEPTADOS");
            lblStudentAccepted.setStyle("-fx-font-weight: bold");
            vboxStudentsContent.getChildren().add(lblStudentAccepted);
        }
        
        GridPane gpStudentsAcceptedContent = new GridPane();
        int column = 0;
        int row = 0;
        
        for(int i = 0; i < studentAcceptedList.size(); i++){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentPostulatedCard.fxml"));
                AnchorPane apStudentCard = loader.load();
                StudentPostulatedCardController cardController = loader.getController();
                cardController.setStudent(studentAcceptedList.get(i));
                cardController.setSelection(true);
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                
                gpStudentsAcceptedContent.add(apStudentCard, column++, row);
                GridPane.setMargin(apStudentCard, new Insets(10));
                studentAcceptedCardControllerList.add(cardController);
            } catch (IOException ex) {
                Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        vboxStudentsContent.getChildren().add(gpStudentsAcceptedContent);
        
        if(!studentPostulatedList.isEmpty()){
            Label lblStudentPostulated = new Label("POSTULADOS");
            lblStudentPostulated.setStyle("-fx-font-weight: bold");
            vboxStudentsContent.getChildren().add(lblStudentPostulated);
        }
        
        GridPane gpStudentsPostulatedContent = new GridPane();
        column = 0;
        row = 0;
        
        for(int i = 0; i < studentPostulatedList.size(); i++){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentPostulatedCard.fxml"));
                AnchorPane apStudentCard = loader.load();
                StudentPostulatedCardController cardController = loader.getController();
                cardController.setStudent(studentPostulatedList.get(i));
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                
                gpStudentsPostulatedContent.add(apStudentCard, column++, row);
                GridPane.setMargin(apStudentCard, new Insets(10));
                studentPostuledCardControllerList.add(cardController);
            } catch (IOException ex) {
                Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        vboxStudentsContent.getChildren().add(gpStudentsPostulatedContent);
    }
    
    private void addReceptionalWork(){
        int result = VALUE_BY_DEFAULT;
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        Project project = getProject();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        
        receptionalWork.setName(project.getName());
        receptionalWork.setDescription(project.getDescription());
        receptionalWork.setModality(project.getModality());
        receptionalWork.setState(RECEPTIONAL_WORK_ACTIVE_STATUS.getDisplayName());
        receptionalWork.setResults(project.getExpectedResults());
        receptionalWork.setIdProject(ID_PROJECT);
        
        try {
            result = receptionalWorkDAO.addReceptionalWork(receptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(result > 0){
            DialogGenerator.getDialog(new AlertMessage ("Se ha creado un trabajo recepcional",Status.SUCCESS));
        }
    }
    
    private boolean existenceActiveReceptionalWork(int idProject){
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        int total = VALUE_BY_DEFAULT;
        boolean existence = false;
        
        try {
            total = receptionalWorkDAO.getTotalActiveReceptionalWorks(idProject, RECEPTIONAL_WORK_ACTIVE_STATUS.getDisplayName());
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(total > VALUE_BY_DEFAULT){
            existence = true;
        }
        
        return existence;
    }
    
    private ReceptionalWork getActiveReceptionalWork(){
        ReceptionalWork receptionalWork = new ReceptionalWork();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        
        try {
            receptionalWork = receptionalWorkDAO.getReceptionalWorkByStatus(ID_PROJECT, RECEPTIONAL_WORK_ACTIVE_STATUS.getDisplayName());
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return receptionalWork;
    }
    
    private Project getProject(){
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();
        
        try {
            project = projectDAO.getProject(ID_PROJECT);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return project;
    }
    
    private List<Student> getStudentSelected(){
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = new ArrayList<>();
        
        for(int i = 0; i < studentPostuledCardControllerList.size(); i++){
            Student student = new Student();
            
            if(studentPostuledCardControllerList.get(i).isSelected()){
                try {
                    student = studentDAO.getStudent(studentPostuledCardControllerList.get(i).getLblEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                }
                studentList.add(student);
            }
        }
        
        return studentList;
    }
    
    private List<Student> getStudentDeselected(){
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = new ArrayList<>();
                
        for(int i = 0; i < studentAcceptedCardControllerList.size(); i++){
            if(!studentAcceptedCardControllerList.get(i).isSelected()){
                Student student = new Student();
                
                try {
                    student = studentDAO.getStudent(studentAcceptedCardControllerList.get(i).getLblEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                studentList.add(student);
            }
        }
        
        return studentList;
    }
    
    private void closeWindow(){
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/StudentsApplicantsCatalog.fxml");
    }
    
    // this method has a high level of complexity due to the validation of the two states of the project
    private boolean isValidSelection(){
        Project project = getProject();
        List<Student> studentsSelectedList = getStudentSelected();
        List<Student> studentsDeselectedList = getStudentDeselected();
        int spacesAvailable = getSpacesAvailable();
        boolean isValid = true;
        
        if(project.getStatus().equals(PROJECT_ASSIGNED_STATUS.getDisplayName())){
            if((studentsSelectedList.size() > spacesAvailable && studentsDeselectedList.size() < studentsSelectedList.size()) || (!studentsSelectedList.isEmpty() && studentsDeselectedList.isEmpty() && spacesAvailable == VALUE_BY_DEFAULT)){
                isValid = false;
            }
        }
        
        return studentsSelectedList.size() <= project.getSpaces() && isValid;
    }
    
    private int getSpacesAvailable(){
        int spacesAvailable = VALUE_BY_DEFAULT;
        ProjectDAO projectDAO = new ProjectDAO();
        
        try {
            spacesAvailable = projectDAO.getAvailableSpacesByProject(ID_PROJECT);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return spacesAvailable;
    }
    
    public void acceptStudentToProject(){
        ProjectDAO projectDAO = new ProjectDAO();
        ReceptionalWork receptionalWork = getActiveReceptionalWork();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        List<Student> studentList = getStudentSelected();
        Project project = getProject();
        int result = VALUE_BY_DEFAULT;
        
        if(!studentList.isEmpty()){
            try {
                result = projectDAO.acceptToProject(ID_PROJECT, studentList);
                result += receptionalWorkDAO.addStudentToReceptionalWork(studentList, receptionalWork.getIdReceptionalWork());
            } catch (SQLException ex) {
                Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
            }

            if(result > VALUE_BY_DEFAULT){
                DialogGenerator.getDialog(new AlertMessage ("Estudiante(s) aceptado(s) correctamente",Status.SUCCESS));
            }

            if(project.getStatus().equals(PROJECT_VALIDATED_STATUS.getDisplayName())){
                changeProjectStatus(PROJECT_ASSIGNED_STATUS.getDisplayName());
            }
        }
    }
    
    public void expellStudent(){
        ProjectDAO projectDAO = new ProjectDAO();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        List<Student> studentList = getStudentDeselected();
        Project project = getProject();
        int result = VALUE_BY_DEFAULT;
        
        try {
            result = projectDAO.expellStudent(ID_PROJECT, studentList);
            result += receptionalWorkDAO.expellStudent(ID_PROJECT, studentList);
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
        }
        
        if(result > VALUE_BY_DEFAULT){
            DialogGenerator.getDialog(new AlertMessage ("Estudiante(s) desasignado(s) correctamente",Status.SUCCESS));
        }
        
        if(project.getStatus().equals(PROJECT_ASSIGNED_STATUS.getDisplayName()) && getSpacesAvailable() == project.getSpaces()){
            changeProjectStatus(PROJECT_VALIDATED_STATUS.getDisplayName());
        }
    }
    
    private void changeProjectStatus(String status){
        ProjectDAO projectDAO = new ProjectDAO();
        int result = VALUE_BY_DEFAULT;
        
        try {
            result = projectDAO.changeProjectStatus(status, ID_PROJECT);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsApplicantsCatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
