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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.Submission;

public class ReceptionalWorkReportController implements Initializable {

    @FXML
    private AnchorPane apAssignments;

    @FXML
    private AnchorPane apStudent;

    @FXML
    private Label lblCodirector;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDirector;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblStatus;

    @FXML
    private Text ntxtDescription;

    @FXML
    private Text ntxtProjectName;

    @FXML
    private Text ntxtResults;

    @FXML
    private VBox vboxCodirectors;

    private static int idReceptionalWork;
    private final int NOT_FOUND_INT = -1;
    private final int VALUE_BY_DEFAULT = 0;
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSET_SIZE_CARD = 10;
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void setIdReceptionalWork(int idReceptionalWork) {
        ReceptionalWorkReportController.idReceptionalWork = idReceptionalWork;
    }

    @FXML
    void goBack(MouseEvent event) {
        ViewReceptionalWorkController.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork.fxml");
    }

    private ReceptionalWork getReceptionalWork() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        try {
            receptionalWork = receptionalWorkDao.getRecepetionalWorkById(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return receptionalWork;
    }

    private void setDirectorsData() {
        ReceptionalWork receptionalWork = getReceptionalWork();
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor director = new Professor();
        List<Professor> codirectors = new ArrayList<>();

        try {
            director = professorDao.getDirectorByProject(receptionalWork.getIdProject());
            codirectors = professorDao.getCoodirectorByProject(receptionalWork.getIdProject());
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (director.getId() != NOT_FOUND_INT) {
            lblDirector.setText(director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName());
        }

        if (codirectors.isEmpty()) {
            lblCodirector.setVisible(false);
            lblCodirector.setDisable(true);
        } else {
            for (int i = 0; i < codirectors.size(); i++) {
                Label lblCodirector = new Label(codirectors.get(i).getHonorificTitle() + " " + codirectors.get(i).getName() + " " + codirectors.get(i).getLastName());
                vboxCodirectors.getChildren().add(lblCodirector);
            }
        }
    }

    private void setReceptionalWorkData() {
        ReceptionalWork receptionalWork = getReceptionalWork();
        ntxtProjectName.setText(receptionalWork.getName());
        ntxtDescription.setText(receptionalWork.getDescription());
        ntxtResults.setText(receptionalWork.getResults());
        lblModality.setText(receptionalWork.getModality());
        lblStatus.setText("Estado del trabajo recepcional: " + receptionalWork.getState());
    }

    private List<Student> getStudentsPerReceptionalWork() {
        List<Student> studentList = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();

        try {
            studentList = studentDao.getStudentPerReceptionalWork(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return studentList;
    }

    private void setCurrentDate() {
        Date currentDate = new Date();
        String currentDateString = DATE_TIME_FORMAT.format(currentDate);
        lblDate.setText(currentDateString);
    }

    private void setStudentsCards() {
        List<Student> studentList = getStudentsPerReceptionalWork();

        if (studentList.isEmpty()) {
            Label lblNotFound = new Label("No hay estudiantes registrados");
            lblNotFound.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            apStudent.getChildren().add(lblNotFound);
        } else {
            GridPane gpStudentsContent = new GridPane();
            int column = VALUE_BY_DEFAULT;
            int row = VALUE_BY_DEFAULT;

            for (int i = 0; i < studentList.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentCardReceptionalWorkReport.fxml"));
                    AnchorPane apStudentCard = loader.load();
                    StudentCardReceptionalWorkReportController cardController = loader.getController();
                    cardController.setData(studentList.get(i), idReceptionalWork);

                    if (column == UPPER_LIMIT_COLUMN) {
                        column = VALUE_BY_DEFAULT;
                        row++;
                    }

                    gpStudentsContent.add(apStudentCard, column++, row);
                    GridPane.setMargin(apStudentCard, new Insets(INSET_SIZE_CARD));
                } catch (IOException ex) {
                    Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                    FailAlert.showFXMLFileFailedAlert();
                }
            }

            apStudent.getChildren().add(gpStudentsContent);
        }
    }

    private List<Assignment> getAssignmentPerReceptionalWork() {
        List<Assignment> assignmentList = new ArrayList<>();
        AssignmentDAO assignmentDao = new AssignmentDAO();

        try {
            assignmentList = assignmentDao.getAssignmentsPerReceptionalWork(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return assignmentList;
    }

    private void setAssignmentsCards() {
        List<Assignment> assignmentList = getAssignmentPerReceptionalWork();

        if (assignmentList.isEmpty()) {
            Label lblNotFound = new Label("No hay asignaciones registradass");
            lblNotFound.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            apAssignments.getChildren().add(lblNotFound);
        } else {
            GridPane gpAssignmentsContent = new GridPane();
            int column = VALUE_BY_DEFAULT;
            int row = VALUE_BY_DEFAULT;

            for (int i = 0; i < assignmentList.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AssignmentsCardReceptionalWorkReport.fxml"));
                    AnchorPane apStudentCard = loader.load();
                    AssignmentsCardReceptionalWorkReportController cardController = loader.getController();
                    cardController.setData(assignmentList.get(i));

                    if (column == UPPER_LIMIT_COLUMN) {
                        column = VALUE_BY_DEFAULT;
                        row++;
                    }

                    gpAssignmentsContent.add(apStudentCard, column++, row);
                    GridPane.setMargin(apStudentCard, new Insets(INSET_SIZE_CARD));
                } catch (IOException ex) {
                    Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                    FailAlert.showFXMLFileFailedAlert();
                }
            }

            apAssignments.getChildren().add(gpAssignmentsContent);
        }
    }

    @FXML
    void downloadClicked(ActionEvent event) {
        try {
            Document reportDocument = new Document();
            Stage DirectoryStage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(DirectoryStage);
            ReceptionalWork receptionalWork = getReceptionalWork();

            if (selectedDirectory == null) {
                DialogGenerator.getDialog(new AlertMessage("No se seleccionó directorio para la descarga", Status.WARNING));
            } else {
                PdfWriter.getInstance(reportDocument, new FileOutputStream(selectedDirectory + "\\ReporteTrabajoRecepcional" + receptionalWork.getName() + ".pdf"));
                reportDocument.open();

                String reportDate = lblDate.getText();
                Font titleFont = new Font();
                Font subtitleFont = new Font();
                Font textFont = new Font();

                setTitlesAndSubtitlesFont(titleFont, subtitleFont);
                setTextFont(textFont);

                Paragraph title = new Paragraph("Reporte del trabajo recepcional: " + receptionalWork.getName(), titleFont);
                Paragraph dateLine = new Paragraph("Fecha de generación de reporte: " + reportDate, subtitleFont);
                Paragraph description = new Paragraph("Descripción: ", subtitleFont);
                Paragraph descriptionData = new Paragraph(receptionalWork.getDescription(), textFont);
                Paragraph results = new Paragraph("Resultados: ", subtitleFont);
                Paragraph resultsData = new Paragraph(receptionalWork.getResults(), textFont);
                Paragraph modality = new Paragraph("Modalidad: ", subtitleFont);
                Paragraph modalityData = new Paragraph(receptionalWork.getModality(), textFont);
                Paragraph status = new Paragraph("Estado del trabajo recepcional: ", subtitleFont);
                Paragraph statusData = new Paragraph(receptionalWork.getState(), textFont);
                Paragraph tableStudentTitle = new Paragraph("ESTUDIANTES DEL TRABAJO RECEPCIONAL", subtitleFont);
                Paragraph tableAssignmentTitle = new Paragraph("ASIGNACIONES DEL TRABAJO RECEPCIONAL", subtitleFont);

                reportDocument.add(title);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(dateLine);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(description);
                reportDocument.add(descriptionData);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(results);
                reportDocument.add(resultsData);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(modality);
                reportDocument.add(modalityData);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(status);
                reportDocument.add(statusData);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(tableStudentTitle);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createStudentsReportTable());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(tableAssignmentTitle);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createAssignmentReportTable());
                reportDocument.add(Chunk.NEWLINE);

                reportDocument.close();
                DialogGenerator.getDialog(new AlertMessage("Descarga de reporte exitosa", Status.SUCCESS));

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.downloadFailedAlert();
        } catch (DocumentException ex) {
            Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.downloadFailedAlert();
        }
    }

    private void setTitlesAndSubtitlesFont(Font titleFont, Font subtitleFont) {
        titleFont.setColor(45, 82, 100);
        titleFont.setSize(20);
        titleFont.setStyle(Font.BOLD);
        subtitleFont.setSize(14);
        subtitleFont.setStyle(Font.BOLD);
    }

    private void setTextFont(Font textFont) {
        textFont.setSize(14);
    }

    private Element createStudentsReportTable() {
        PdfPTable studentReportTable = new PdfPTable(4);
        Font textFont = new Font();
        List<Student> studentsPerReceptionalWork = getStudentsPerReceptionalWork();

        textFont.setStyle(Font.BOLD);
        createStudentTableHeader(studentReportTable, textFont);

        for (Student student : studentsPerReceptionalWork) {
            try {
                fillStudentReportTable(student, studentReportTable);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.downloadFailedAlert();
            }
        }

        return studentReportTable;
    }

    private void createStudentTableHeader(PdfPTable studentReportTable, Font textFont) {
        PdfPCell columnStudent = new PdfPCell(new Paragraph("Estudiante", textFont));
        PdfPCell columnEmail = new PdfPCell(new Paragraph("Correo Institucional", textFont));
        PdfPCell columnAssignment = new PdfPCell(new Paragraph("Asignaciones Totales", textFont));
        PdfPCell columnSubmission = new PdfPCell(new Paragraph("Avances Entregados", textFont));

        studentReportTable.addCell(columnStudent);
        studentReportTable.addCell(columnEmail);
        studentReportTable.addCell(columnAssignment);
        studentReportTable.addCell(columnSubmission);
    }

    private void fillStudentReportTable(Student student, PdfPTable studentReportTable) throws SQLException {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        SubmissionManagerDAO submissionManagerDao = new SubmissionManagerDAO();

        ReceptionalWork receptionalWork = getReceptionalWork();
        int assignmentCount = VALUE_BY_DEFAULT;
        int submissionCount = VALUE_BY_DEFAULT;

        assignmentCount = assignmentDao.getCountAssignmentPerReceptionalWork(receptionalWork.getIdReceptionalWork());
        submissionCount = submissionManagerDao.getCountSubmissionsPerReceptionalWork(receptionalWork.getIdReceptionalWork());

        PdfPCell studentName = new PdfPCell(new Paragraph(student.getName() + " " + student.getLastName()));
        PdfPCell studentEmail = new PdfPCell(new Paragraph(student.getEMail()));
        PdfPCell totalAssignments = new PdfPCell(new Paragraph("" + assignmentCount));
        PdfPCell totalSubmissions = new PdfPCell(new Paragraph("" + submissionCount));

        studentReportTable.addCell(studentName);
        studentReportTable.addCell(studentEmail);
        studentReportTable.addCell(totalAssignments);
        studentReportTable.addCell(totalSubmissions);
    }

    private Element createAssignmentReportTable() {
        PdfPTable assignmentReportTable = new PdfPTable(4);
        Font textFont = new Font();
        List<Assignment> assignmentList = getAssignmentPerReceptionalWork();

        textFont.setStyle(Font.BOLD);
        createAssignmentTableHeader(assignmentReportTable, textFont);

        for (Assignment assignment : assignmentList) {
            try {
                fillAssignmentReportTable(assignment, assignmentReportTable);
            } catch (SQLException ex) {
                Logger.getLogger(ReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFailedConnectionAlert();
            }
        }

        return assignmentReportTable;
    }

    private void createAssignmentTableHeader(PdfPTable receptionalWorkReportTable, Font textFont) {
        PdfPCell columnTitle = new PdfPCell(new Paragraph("Título", textFont));
        PdfPCell columnStartDate = new PdfPCell(new Paragraph("Fecha de Inicio", textFont));
        PdfPCell columnDateline = new PdfPCell(new Paragraph("Fecha de Fin", textFont));
        PdfPCell columnDeliveryDate = new PdfPCell(new Paragraph("Fecha de Entrega", textFont));

        receptionalWorkReportTable.addCell(columnTitle);
        receptionalWorkReportTable.addCell(columnStartDate);
        receptionalWorkReportTable.addCell(columnDateline);
        receptionalWorkReportTable.addCell(columnDeliveryDate);
    }

    private void fillAssignmentReportTable(Assignment assignment, PdfPTable assignmentReportTable) throws SQLException {
        PdfPCell title = new PdfPCell(new Paragraph(assignment.getTitle()));
        PdfPCell startDate = new PdfPCell(new Paragraph(DATE_FORMAT.format(assignment.getStartDate())));
        PdfPCell dateline = new PdfPCell(new Paragraph(DATE_FORMAT.format(assignment.getDeadline())));
        PdfPCell deliveryDate = new PdfPCell();
        Paragraph paragraphSubmission = new Paragraph();

        if (assignment.getIdSubmission() == VALUE_BY_DEFAULT) {
            paragraphSubmission.add("NO ENTREGADO");
            deliveryDate.addElement(paragraphSubmission);
        } else {
            Submission submission = getSubmission(assignment.getIdSubmission());
            paragraphSubmission.add(DATE_FORMAT.format(submission.getDeliveryDate()));
            deliveryDate.addElement(paragraphSubmission);
        }

        assignmentReportTable.addCell(title);
        assignmentReportTable.addCell(startDate);
        assignmentReportTable.addCell(dateline);
        assignmentReportTable.addCell(deliveryDate);
    }

    public Submission getSubmission(int idSubmission) {
        Submission submission = new Submission();
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();

        try {
            submission = submissionDao.getSubmissionById(idSubmission);
        } catch (SQLException ex) {
            Logger.getLogger(AssignmentsCardReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return submission;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCurrentDate();
        setReceptionalWorkData();
        setDirectorsData();
        setStudentsCards();
        setAssignmentsCards();
    }
}
