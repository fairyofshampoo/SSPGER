package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.DialogGenerator;
import mx.uv.fei.sspger.GUI.TableStudentsPerCourse;
import mx.uv.fei.sspger.GUI.AlertMessage;
import mx.uv.fei.sspger.GUI.ComboBoxProfessor;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.Status;

public class AddCourseController implements Initializable {
    
    private CourseStates courseState = CourseStates.AVAILABLE;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private Button cancelBtn;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> studentName;
    
    @FXML
    private TableColumn<TableStudentsPerCourse, String> registrationTag;
    
    @FXML
    private TableColumn<TableStudentsPerCourse, CheckBox> checkBox;
    
    @FXML
    private TableView<TableStudentsPerCourse> studentsTableView;

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
        cbxCourseName.setItems(FXCollections.observableArrayList("Experiencia recepcional", "Proyecto guiado"));
        cbxCourseName.getSelectionModel().select("Experiencia recepcional");
        
        setStudentTable();
        setCbxProfessor();
        setCbxSemester();
    }

    @FXML
    private void cancelCourseAddition(ActionEvent actionEvent){
        if (isConfirmedExit()){
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void addCourse(ActionEvent actionEvent){
        
        CourseDAO courseDao = new CourseDAO();
        Course course = new Course();
        
        Semester semester = getSemester();
        
        course.setName(cbxCourseName.getValue());
        course.setBlock(Integer.parseInt(txtBlock.getText()));
        course.setSection(Integer.parseInt(txtSection.getText()));
        course.setNrc(txtNrc.getText());
        course.setState(courseState.getCourseState());
        course.setCourseId(semester.getSemesterId());
        
        try{
            if(courseDao.registerCourse(course, semester.getSemesterId()) == 1){
                DialogGenerator.getDialog(new AlertMessage (
                    "Curso registrado con éxito.", 
                    Status.SUCCESS));
                registerStudents(courseDao, course);
                setProfessor(courseDao, course);
            } else {
                DialogGenerator.getDialog(new AlertMessage (
                    "No fue posible añadir el curso",
                    Status.WARNING));
            }
        }catch (SQLException SqlException){
            DialogGenerator.getDialog(new AlertMessage (
                "Hubo un problema al registrar el sistema",
                Status.FATAL));
        }
    }
    
    private void registerStudents(CourseDAO courseDao, Course course) {
        EnrollToCourse enrollToCourse = new EnrollToCourse();
        
        for(int counter = 0; counter < studentsTableView.getItems().size(); counter++){
          
            if(studentsTableView.getItems().get(counter).getCheckBox().isSelected()){
                int studentId = Integer.parseInt(studentsTableView.getItems().get(counter).getCheckBox().getText());

                enrollToCourse.setStudentId(studentId);
                enrollToCourse.setCourseId(course.getCourseId());

                try{
                    if(courseDao.enrollStudentToCourse(enrollToCourse) > 1){
                        //TODO
                    } else {
                        String message = "No fue posible añadir al usuario" + studentsTableView.getItems().get(counter).getStudentName();
                        DialogGenerator.getDialog(new AlertMessage (
                        message,
                        Status.WARNING));
                    }
                    }catch (SQLException SqlException){
                    DialogGenerator.getDialog(new AlertMessage (
                        "Error en la conexion al sistema al ingresar estudiantes.",
                        Status.FATAL));
                }
            }
        }
    }
    
    private void setProfessor (CourseDAO courseDao, Course course) {
        ComboBoxProfessor selectedItem = (ComboBoxProfessor) cbxProfessor.getSelectionModel().getSelectedItem();
        int professorId = selectedItem.getProfessorId();
        
        try{
            courseDao.enrollProfessorToCourse(professorId, course);
        }catch (SQLException SqlException){
            DialogGenerator.getDialog(new AlertMessage (
                "Hubo un problema al registrar el profesor al curso",
                Status.FATAL));
        }
        
    }
    
    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de agregar curso?");
        return (response.get() == DialogGenerator.BUTTON_YES);
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
            String semesterDate = dateFormat.format(semesterStart) + " / " + dateFormat.format(semesterEnd);
            semesterDateObservableList.add(semesterDate);
        }
        
        cbxSemester.setItems(semesterDateObservableList);
    }
    
    private Semester getSemester() {
        Semester semester = new Semester();
        SemesterDAO semesterDao = new SemesterDAO();
        String startDate = cbxSemester.getValue();
        String subStringStartDate = startDate.substring(0, 10);
        
        try{
            Date date = dateFormat.parse(subStringStartDate);
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
            list.add(new TableStudentsPerCourse (availableStudents.get(quantityOfStudents).getRegistrationTag(), availableStudents.get(quantityOfStudents).getName(), checkBoxTable));
        }
        
        studentsTableView.setItems(list);
        registrationTag.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("registrationTag"));
        studentName.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentName"));
        checkBox.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, CheckBox>("checkBox"));   
    }
}
