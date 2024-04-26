package mx.uv.fei.sspger.GUI.controllers;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Student;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.SemesterDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Semester;
import mx.uv.fei.sspger.logic.Status;

public class CourseReportController implements Initializable {

    private static String courseId;
    private Course course;
    private int column = 0;
    private int row = 1;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final int ERROR_RECEPTIONAL_WORK_ID = 0;

    @FXML
    private GridPane gpStudents;

    @FXML
    private Label lblBlock;

    @FXML
    private Label lblGenerationDate;

    @FXML
    private Label lblNrc;

    @FXML
    private Label lblSection;

    @FXML
    private Label lblSemester;

    @FXML
    private ImageView imgGoBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StudentDAO studentDao = new StudentDAO();
        CourseDAO courseDao = new CourseDAO();

        List<Student> courseStudents = new ArrayList<>();

        try {
            course = courseDao.getCourseById(courseId);
            courseStudents = studentDao.getStudentsPerCourse(courseId);

            setGraphicElements(course);

            for (Student student : courseStudents) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentReportCard.fxml"));
                Pane studentReportCard = fxmlLoader.load();
                StudentReportCardController studentReportCardController = fxmlLoader.getController();
                studentReportCardController.setStudentReportCard(student.getId());

                if (column == 4) {
                    column = 0;
                    row++;
                }

                gpStudents.add(studentReportCard, column++, row);
                GridPane.setMargin(studentReportCard, new Insets(10));
            }
        } catch (SQLException | IOException exception) {
            Logger.getLogger(CourseReportController.class.getName()).log(Level.SEVERE, null, exception);
            FailAlert.showFailedConnectionAlert();
        }
    }

    public static void setCourseId(String courseId) {
        CourseReportController.courseId = courseId;
    }

    private void setGraphicElements(Course course) throws SQLException {
        String semesterDate = getSemesterDate(course.getSemesterId());
        Date currentDate = new Date();
        String currentDateString = DATE_FORMAT.format(currentDate);

        lblSemester.setText(semesterDate);
        lblBlock.setText("" + course.getBlock());
        lblSection.setText("" + course.getSection());
        lblNrc.setText(course.getNrc());
        lblGenerationDate.setText(currentDateString);

    }

    private String getSemesterDate(int semesterId) throws SQLException {
        SemesterDAO semesterDao = new SemesterDAO();
        Semester semester = semesterDao.getSemesterPerId(semesterId);
        Date semesterStart = new Date(semester.getStartDate().getTime());
        Date semesterEnd = new Date(semester.getDeadline().getTime());
        String semesterDate = DATE_FORMAT.format(semesterStart) + " a " + DATE_FORMAT.format(semesterEnd);

        return semesterDate;
    }

    @FXML
    void downloadReport(ActionEvent event) {
        try {
            Document reportDocument = new Document();
            Stage DirectoryStage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(DirectoryStage);

            if (selectedDirectory == null) {
                DialogGenerator.getDialog(new AlertMessage(
                        "No se seleccionó directorio para la descarga",
                        Status.WARNING));
            } else {
                PdfWriter.getInstance(reportDocument, new FileOutputStream(selectedDirectory + "\\ReporteCurso" + course.getName() + " " + lblSemester.getText() + ".pdf"));
                reportDocument.open();

                String reportDate = lblGenerationDate.getText();
                Font titleFont = new Font();
                Font subtitleFont = new Font();

                setTitlesAndSubtitlesFont(titleFont, subtitleFont);

                Paragraph title = new Paragraph("Reporte del curso: " + course.getName() + " " + lblSemester.getText(), titleFont);
                Paragraph dateLine = new Paragraph("Fecha de generación de reporte: " + reportDate, subtitleFont);

                reportDocument.add(title);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(dateLine);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createCourseReportTable());
                reportDocument.add(Chunk.NEWLINE);

                reportDocument.close();
                DialogGenerator.getDialog(new AlertMessage(
                        "Descarga de reporte exitosa",
                        Status.SUCCESS));
            }
        } catch (FileNotFoundException | DocumentException exception) {
            Logger.getLogger(CourseReportController.class.getName()).log(Level.SEVERE, null, exception);
            DialogGenerator.getDialog(new AlertMessage("Error al crear el reporte.", Status.SUCCESS));
        }
    }

    private Element createCourseReportTable() {
        PdfPTable courseReportTable = new PdfPTable(4);
        Font textFont = new Font();
        StudentDAO studentDao = new StudentDAO();
        List<Student> courseStudents = new ArrayList<>();

        textFont.setStyle(Font.BOLD);
        createPdfHeader(courseReportTable, textFont);

        try {
            courseStudents = studentDao.getStudentsPerCourse(courseId);

            for (Student student : courseStudents) {
                fillReportTable(student, courseReportTable);
            }

        } catch (SQLException sqlException) {
            Logger.getLogger(CourseReportController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return courseReportTable;
    }

    private void createPdfHeader(PdfPTable courseReportTable, Font textFont) {
        PdfPCell columnStudent = new PdfPCell(new Paragraph("Estudiante", textFont));
        PdfPCell columnReceptionalWork = new PdfPCell(new Paragraph("Trabajo Recepcional", textFont));
        PdfPCell columnAssignment = new PdfPCell(new Paragraph("Asignaciones totales", textFont));
        PdfPCell columnSubmission = new PdfPCell(new Paragraph("Avances entregados", textFont));

        courseReportTable.addCell(columnStudent);
        courseReportTable.addCell(columnReceptionalWork);
        courseReportTable.addCell(columnAssignment);
        courseReportTable.addCell(columnSubmission);
    }

    private void fillReportTable(Student student, PdfPTable courseReportTable) throws SQLException {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        SubmissionManagerDAO submissionManagerDao = new SubmissionManagerDAO();

        ReceptionalWork receptionalWork = new ReceptionalWork();
        int assignmentCount = 0;
        int submissionCount = 0;

        receptionalWork = receptionalWorkDao.getActiveReceptionalWorkByStudent(student.getId());
        assignmentCount = assignmentDao.getCountAssignmentPerReceptionalWork(receptionalWork.getIdReceptionalWork());
        submissionCount = submissionManagerDao.getCountSubmissionsPerReceptionalWork(receptionalWork.getIdReceptionalWork());

        PdfPCell studentName = new PdfPCell(new Paragraph(student.getName() + " " + student.getLastName()));
        PdfPCell receptionalWorkName = new PdfPCell(new Paragraph("Sin trabajo recepcional"));

        if (receptionalWork.getIdReceptionalWork() != ERROR_RECEPTIONAL_WORK_ID) {
            receptionalWorkName = new PdfPCell(new Paragraph(receptionalWork.getName()));
        }

        PdfPCell totalAssignments = new PdfPCell(new Paragraph("" + assignmentCount));
        PdfPCell totalSubmissions = new PdfPCell(new Paragraph("" + submissionCount));

        courseReportTable.addCell(studentName);
        courseReportTable.addCell(receptionalWorkName);
        courseReportTable.addCell(totalAssignments);
        courseReportTable.addCell(totalSubmissions);
    }

    private void setTitlesAndSubtitlesFont(Font titleFont, Font subtitleFont) {
        titleFont.setColor(45, 82, 100);
        titleFont.setSize(20);
        titleFont.setStyle(Font.BOLD);
        subtitleFont.setSize(14);
        subtitleFont.setStyle(Font.BOLD);
    }

    @FXML
    private void mouseExitedBackArea(MouseEvent event) {
        imgGoBack.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void mouseEnteredBackArea(MouseEvent event) {
        imgGoBack.setCursor(Cursor.HAND);
    }

    @FXML
    private void goBack(MouseEvent event) {
        imgGoBack.setCursor(Cursor.DEFAULT);

        SPGER.setRoot("ProfessorCourseView.fxml");
    }
}
