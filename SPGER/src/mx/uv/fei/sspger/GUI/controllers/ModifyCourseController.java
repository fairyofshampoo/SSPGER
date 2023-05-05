package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import mx.uv.fei.sspger.GUI.AlertMessage;
import mx.uv.fei.sspger.GUI.ComboBoxProfessor;
import mx.uv.fei.sspger.GUI.DialogGenerator;
import mx.uv.fei.sspger.GUI.MainApplication;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseName;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;


public class ModifyCourseController implements Initializable{

    private final CourseName GUIDED_PROYECT = CourseName.GUIDED_PROYECT;
    private final CourseName RECEPCIONAL_EXPERIENCE = CourseName.RECEPCIONAL_EXPERIENCE;
    private final CourseStates COURSES_STATES = CourseStates.AVAILABLE;
    public static String courseId;
    private int professorCourseId;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    private Button cancelBtn;
    
    @FXML
    private ComboBox<String> cbxCourseName;

    @FXML
    private ComboBox<ComboBoxProfessor> cbxProfessor;

    @FXML
    private ComboBox<String> cbxSemester;
    
    @FXML
    private Label lblInvalidBlock;

    @FXML
    private Label lblInvalidNrc;

    @FXML
    private Label lblInvalidSection;

    @FXML
    private Label lblInvalidSemester;

    @FXML
    private TableColumn<TableStudentsPerCourse, CheckBox> tblCCheckBox;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCRegistrationTag;

    @FXML
    private TableColumn<TableStudentsPerCourse, String> tblCStudentName;

    @FXML
    private TableView<TableStudentsPerCourse> tblVwStudents;

    @FXML
    private TextField txtBlock;

    @FXML
    private TextField txtNrc;

    @FXML
    private TextField txtSection;
    
    ObservableList<TableStudentsPerCourse> list = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Course course = new Course();
        CourseDAO courseDao = new CourseDAO();
        try {
        course = courseDao.getCourseByID(courseId);
        } catch (SQLException ex) {
            DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema students.",
                Status.FATAL));
            Logger.getLogger(ModifyCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setGraphicElements(course);
        setRestrictionsForGraphicElements();
        setCourseStudents();
        setStudentTable();
        setCbxProfessor(course);
        setCbxSemester(course);
    }
    
    private void setGraphicElements(Course course){
        txtBlock.setText("" + course.getBlock());
        txtNrc.setText("" + course.getNrc());
        txtSection.setText("" + course.getSection());
        cbxCourseName.setItems(FXCollections.observableArrayList(RECEPCIONAL_EXPERIENCE.getCourseName(), GUIDED_PROYECT.getCourseName()));
        cbxCourseName.getSelectionModel().select(course.getName());
        
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
    
    public void setCourseStudents(){
        StudentDAO studentDao = new StudentDAO();
        List<Student> courseStudents = new ArrayList<>();
        
        try{
            courseStudents = studentDao.getStudentsPerCourse(courseId);
        } catch (SQLException Ex) {
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema students.",
                Status.FATAL));
        }

        for(int quantityOfStudents = 0; quantityOfStudents < courseStudents.size(); quantityOfStudents++){
            CheckBox checkBoxTable = new CheckBox ("" + (courseStudents.get(quantityOfStudents).getId()));
            checkBoxTable.setSelected(true);
            String studentFullName = courseStudents.get(quantityOfStudents).getName() + " " +
                    courseStudents.get(quantityOfStudents).getLastName();
            
            list.add(new TableStudentsPerCourse (courseStudents.get(quantityOfStudents).getRegistrationTag(),
                    studentFullName, 
                    checkBoxTable));
        }
        
        tblVwStudents.setItems(list);
        tblCRegistrationTag.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("registrationTag"));
        tblCStudentName.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, String>("studentName"));
        tblCCheckBox.setCellValueFactory(new PropertyValueFactory<TableStudentsPerCourse, CheckBox>("checkBox"));   
    }
    
    private void setStudentTable(){
        StudentDAO studentDao = new StudentDAO();
        List<Student> availableStudents = new ArrayList<>();
        
        try{
            availableStudents = studentDao.getAvailableStudentsNotInCourse(courseId);
        } catch (SQLException Ex) {
                DialogGenerator.getDialog(new AlertMessage (
                "Error en la conexion al sistema settabe.",
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
    
    private void setCbxProfessor(Course course){
        Professor courseProfessor = new Professor();
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        ObservableList<ComboBoxProfessor> professorObservableList = FXCollections.observableArrayList();
        
        try{
            professorList = professorDao.getAllProfessors();
            courseProfessor = professorDao.getProfessorByCourse(courseId);
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
        
        String courseProfessorName = courseProfessor.getHonorificTitle() + " " + courseProfessor.getName() + " " + courseProfessor.getLastName();
        professorCourseId = courseProfessor.getId();
        ComboBoxProfessor comboBoxCourseProfessor = new ComboBoxProfessor(courseProfessorName, professorCourseId);
        
        cbxProfessor.setItems(professorObservableList);
        cbxProfessor.getSelectionModel().select(comboBoxCourseProfessor);
    }
    
    private void setCbxSemester(Course course){
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
        cbxSemester.getSelectionModel().select(getSemesterDate(course.getSemesterId()));
    }
    
    public String getSemesterDate(int semesterId){
        SemesterDAO semesterDao = new SemesterDAO ();
        Semester semester = new Semester();
        
        try{
            semester = semesterDao.getSemesterPerId(semesterId);
        } catch (SQLException sqlException){
            Logger.getLogger(ModifyCourseController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
       
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " / " + DATE_FORMAT.format(semesterEnd);
        
        return semesterDate;
    }

    @FXML
    void cancelCourseModification(ActionEvent event) throws IOException {
        if (isConfirmedExit()){
            MainApplication.setRoot("/mx/uv/fei/sspger/GUI/CourseManagement");
        }
    }

    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de modificar curso?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    @FXML
    void updateCourse(ActionEvent event) throws SQLException{
        if(validateFields()){
            CourseDAO courseDao = new CourseDAO();
            Course course = new Course();
            getSemester(course);
            
            setCourse(course);
            
            try{
                modifyCourse(course, courseDao);
            
                MainApplication.setRoot("/mx/uv/fei/sspger/GUI/ViewCourse");
            } catch (SQLException | IOException exception){
                DialogGenerator.getDialog(new AlertMessage (
                    "Hubo un problema al registrar el sistema",
                    Status.FATAL));
                Logger.getLogger(ModifyCourseController.class.getName()).log(Level.SEVERE, null, exception);
            }            
        }
    }
    
    private void setCourse(Course course){
        course.setName(cbxCourseName.getValue());
        course.setBlock(Integer.parseInt(txtBlock.getText()));
        course.setSection(Integer.parseInt(txtSection.getText()));
        course.setNrc(txtNrc.getText());
        course.setState(COURSES_STATES.getCourseState());
        course.manualSetOfCourseId(courseId);
    }
    
    private void getSemester(Course course) {
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
        
        course.setSemesterId(semester.getSemesterId());
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
    
    void modifyCourse(Course course, CourseDAO courseDao)throws SQLException{

            List <EnrollToCourse> studentsToRegister = studentsList(courseDao, course);
            List <EnrollToCourse> studentsToExpell = expelledStudentsList(courseDao, course);
            int professorId = getProfessor();
                   
            courseDao.modifyCourseTransaction(professorId, course, studentsToRegister, studentsToExpell);
                    
            DialogGenerator.getDialog(new AlertMessage (
                "Se ha actualizado el curso exitosamente.",
                Status.SUCCESS));
        
    }
    
    private List<EnrollToCourse> expelledStudentsList (CourseDAO courseDao, Course course) throws SQLException{
        List<EnrollToCourse> enrollToExpellList = new ArrayList<>();
        List<EnrollToCourse> enrolledStudents = new ArrayList<>();
        enrolledStudents = courseDao.getEnrollId(courseId);

        for(int counter = 0; counter < tblVwStudents.getItems().size(); counter++){
          
            if(!tblVwStudents.getItems().get(counter).getCheckBox().isSelected()){
                EnrollToCourse enrollToCourse = new EnrollToCourse();
                int studentId = Integer.parseInt(tblVwStudents.getItems().get(counter).getCheckBox().getText());
                enrollToCourse.setStudentId(studentId);
                enrollToCourse.setCourseId(course.getCourseId());
  
                if(!AlreadyEnrolled(enrollToCourse,enrolledStudents)){
                    enrollToExpellList.add(enrollToCourse);
                }
            }
        }
        return enrollToExpellList;
    }
    
    private List<EnrollToCourse> studentsList(CourseDAO courseDao, Course course)throws SQLException{
        List<EnrollToCourse> enrollToCourseList = new ArrayList<>();
        List<EnrollToCourse> enrolledStudents = new ArrayList<>();
        enrolledStudents = courseDao.getEnrollId(courseId);

        for(int counter = 0; counter < tblVwStudents.getItems().size(); counter++){
          
            if(tblVwStudents.getItems().get(counter).getCheckBox().isSelected()){
                EnrollToCourse enrollToCourse = new EnrollToCourse();
                int studentId = Integer.parseInt(tblVwStudents.getItems().get(counter).getCheckBox().getText());
                enrollToCourse.setStudentId(studentId);
                enrollToCourse.setCourseId(course.getCourseId());
  
                if(AlreadyEnrolled(enrollToCourse , enrolledStudents)){
                    enrollToCourseList.add(enrollToCourse);
                }
            }
        }
        return enrollToCourseList;
    }
    
    private boolean AlreadyEnrolled(EnrollToCourse enrollToCourse, List<EnrollToCourse> enrolledStudents){
        boolean result = false;
        
        for(EnrollToCourse studentForEnrollment: enrolledStudents){
            if(enrollToCourse.equals(studentForEnrollment)){
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    private int getProfessor () {
        int professorId = 0;
        
        if(cbxProfessor.getSelectionModel().isEmpty()){
            professorId = 0;
        }else{
            ComboBoxProfessor selectedItem = (ComboBoxProfessor) cbxProfessor.getSelectionModel().getSelectedItem();
            if( professorCourseId != selectedItem.getProfessorId()){
                professorId = selectedItem.getProfessorId();
            }            
        }
        return professorId;
    }
}
