package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.sspger.GUI.ComboBoxProfessor;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseName;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.Status;

public class AddCourseController implements Initializable {
    
    private final CourseName GUIDED_PROYECT = CourseName.GUIDED_PROYECT;
    private final CourseName RECEPCIONAL_EXPERIENCE = CourseName.RECEPCIONAL_EXPERIENCE;
    private final CourseStates COURSES_STATES = CourseStates.AVAILABLE;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCStudentName;
    
    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCRegistrationTag;
    
    @FXML
    private TableColumn<TableStudentsPerCourse, CheckBox> tblCCheckBox;
    
    @FXML
    private TableView<TableStudentsPerCourse> tblVwStudents;

    @FXML
    private Label lblInvalidNrc;

    @FXML
    private Label lblInvalidSection;

    @FXML
    private Label lblInvalidSemester;

    @FXML
    private Label lblInvalidBlock;
    
    @FXML
    private TextField txtBlock;

    @FXML
    private TextField txtNrc;

    @FXML
    private TextField txtSection;
    
    @FXML
    private ComboBox<String> cbxCourseName;
    
    @FXML
    private ComboBox<ComboBoxProfessor> cbxProfessor;
    
    @FXML
    private ComboBox<String> cbxSemester;
    
    ObservableList<TableStudentsPerCourse> list = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbxCourseName.setItems(FXCollections.observableArrayList(RECEPCIONAL_EXPERIENCE.getCourseName(), GUIDED_PROYECT.getCourseName()));
        cbxCourseName.getSelectionModel().select(RECEPCIONAL_EXPERIENCE.getCourseName());
        
        setRestrictionsForGraphicElements();
        setStudentTable();
        setCbxProfessor();
        setCbxSemester();
    }
    
    private void setRestrictionsForGraphicElements() {
        UnaryOperator <TextFormatter.Change> sectionAndBlockLenght = change ->{
            if(change.getControlNewText().matches("\\d{0,1}")){
                return change;
            } else{
                return null;
            }
        };
        
        UnaryOperator <TextFormatter.Change> nrcLenght = change ->{
            if(change.getControlNewText().matches("\\d{0,5}")){
                return change;
            } else{
                return null;
            }
        };
        
        txtBlock.setTextFormatter(new TextFormatter<>(sectionAndBlockLenght));
        txtSection.setTextFormatter(new TextFormatter<>(sectionAndBlockLenght));
        txtNrc.setTextFormatter(new TextFormatter<>(nrcLenght));
    }
    
    private void setStudentTable(){
        StudentDAO studentDao = new StudentDAO();
        List<Student> availableStudents = new ArrayList<>();
        
        try{
            availableStudents = studentDao.getAvailableStudents();
        } catch (SQLException Ex) {
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema.",
                Status.FATAL));
        }

        for(int quantityOfStudents = 0; quantityOfStudents < availableStudents.size(); quantityOfStudents++){
            CheckBox checkBoxTable = new CheckBox ("" + (availableStudents.get(quantityOfStudents).getId()));
            String studentFullName = availableStudents.get(quantityOfStudents).getName() + " " +
                    availableStudents.get(quantityOfStudents).getLastName();
            
            list.add(new TableStudentsPerCourse (
                    availableStudents.get(quantityOfStudents).getRegistrationTag(), 
                    studentFullName, checkBoxTable));
        }
        
        tblVwStudents.setItems(list);
        tblCRegistrationTag.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("registrationTag"));
        tblCStudentName.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentName"));
        tblCCheckBox.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, CheckBox>("checkBox"));   
    }
    
    private void setCbxProfessor(){
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        ObservableList<ComboBoxProfessor> professorObservableList = FXCollections.observableArrayList();
        
        try{
            professorList = professorDao.getAllProfessors();
        } catch (SQLException SqlException){
            DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema.",
                Status.FATAL));
        }
        
        for(Professor professor : professorList){
            String professorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();
            int professorId = professor.getId();
            ComboBoxProfessor comboBoxProfessor = new ComboBoxProfessor(professorName, professorId);
            professorObservableList.add(comboBoxProfessor);
        }
        
        cbxProfessor.setItems(professorObservableList);
    }
    
    private void setCbxSemester(){
        SemesterDAO semesterDao = new SemesterDAO();
        List<Semester> semesterList = new ArrayList<>();
        ObservableList<String> semesterDateObservableList = FXCollections.observableArrayList();
        
        try{
            semesterList = semesterDao.getAvailableSemesters();
        } catch (SQLException SqlException){
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema.",
                Status.FATAL));
        }
        
        for(Semester semester : semesterList){
            Date semesterStart = new Date(semester.getStartDate().getTime());
            Date semesterEnd = new Date(semester.getDeadline().getTime());
            String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);
            semesterDateObservableList.add(semesterDate);
        }
        
        cbxSemester.setItems(semesterDateObservableList);
    }

    @FXML
    private void cancelCourseAddition(ActionEvent actionEvent) throws IOException{
        if (isConfirmedExit()){
            SPGER.setRoot("CourseManagement.fxml");
        }
    }
    
    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de agregar curso?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    @FXML
    private void addCourse(ActionEvent actionEvent) throws IOException{
        if(validateFields()){
            CourseDAO courseDao = new CourseDAO();
            Course course = new Course();
            Semester semester = getSemester();
            
            setCourse(course, semester);
            
            ViewCourseController.setIdCourse(course.getCourseId());
            
            try{
                registerCourse(semester, course, courseDao);
                
                SPGER.setRoot("ViewCourse.fxml");
                
            }catch (SQLException sqlException){
                DialogGenerator.getDialog(new AlertMessage (
                    "Hubo un problema al registrar el sistema",
                    Status.FATAL));
                Logger.getLogger(AddCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
            }
        }
    }
    
    private void registerCourse(Semester semester, Course course, CourseDAO courseDao) throws SQLException{
        if(course.equals(courseDao.getCourseByID(course.getCourseId()))){
            DialogGenerator.getDialog(new AlertMessage (
                "El curso ya existe en el sistema.", 
                Status.WARNING));
        } else {
            List <EnrollToCourse> studentsToRegister = studentsList(courseDao, course);
            int professorId = getProfessor();
                   
            courseDao.registerCourseTransaction(professorId, course, studentsToRegister, semester.getSemesterId());
                    
            DialogGenerator.getDialog(new AlertMessage (
                "Se ha registrado el curso al sistema.",
                Status.SUCCESS));
        }
    }
    
    private List<EnrollToCourse> studentsList(CourseDAO courseDao, Course course) {
        List<EnrollToCourse> enrollToCourseList = new ArrayList<>();

        for(int counter = 0; counter < tblVwStudents.getItems().size(); counter++){
          
            if(tblVwStudents.getItems().get(counter).getCheckBox().isSelected()){
                EnrollToCourse enrollToCourse = new EnrollToCourse();
                int studentId = Integer.parseInt(tblVwStudents.getItems().get(counter).getCheckBox().getText());
                enrollToCourse.setStudentId(studentId);
                enrollToCourse.setCourseId(course.getCourseId());
  
                enrollToCourseList.add(enrollToCourse);
            }
        }
        
        return enrollToCourseList;
    }
    
    private int getProfessor () {
        int professorId;
        
        if(cbxProfessor.getSelectionModel().isEmpty()){
            professorId = 0;
        }else{
            ComboBoxProfessor selectedItem = (ComboBoxProfessor) cbxProfessor.getSelectionModel().getSelectedItem();
            professorId = selectedItem.getProfessorId();
        }
        
        return professorId;
    }
      
    private Semester getSemester() {
        Semester semester = new Semester();
        SemesterDAO semesterDao = new SemesterDAO();
        String startDate = cbxSemester.getValue();
        String subStringStartDate = startDate.substring(0, 10);
        
        try{
            Date date = DATE_FORMAT.parse(subStringStartDate);
            java.sql.Date sqlDate= new java.sql.Date(date.getTime());
            semester.setStartDate(sqlDate);
        } catch (ParseException parseException){
            DialogGenerator.getDialog(new AlertMessage (
                "Error en el semestre.",
                Status.FATAL));    
        }
        
        try{
            semester = semesterDao.getSemesterPerStartDate(semester);
        }catch (SQLException SqlException){
             DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema al obtener el semestre.",
                Status.FATAL));           
        }
        
        return semester;
    }

    private boolean validateFields() {
        boolean result = true;
        if(FieldValidation.isNullOrEmptyTxtField(txtBlock)){
            result = false;
            lblInvalidBlock.setVisible(true);
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtSection)){
            result = false;
            lblInvalidSection.setVisible(true);
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtNrc)){
            result = false;
            lblInvalidNrc.setVisible(true);
        }
        if(cbxSemester.getSelectionModel().isEmpty()){
            result = false;
            lblInvalidSemester.setVisible(true);
        }
        return result;
    }

    private void setCourse(Course course, Semester semester) {
        course.setName(cbxCourseName.getValue());
        course.setBlock(Integer.parseInt(txtBlock.getText()));
        course.setSection(Integer.parseInt(txtSection.getText()));
        course.setNrc(txtNrc.getText());
        course.setState(COURSES_STATES.getCourseState());
        course.setCourseId(semester.getSemesterId());
    }
}
