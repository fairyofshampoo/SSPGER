package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.ProjectGeneralReport;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.Status;


public class GeneralProjectReportController implements Initializable {

    @FXML
    private Button btnDownloadReport;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblReportHeader;

    @FXML
    private Label lblDirectorLeastProjects;

    @FXML
    private Label lblDirectorMostProjects;

    @FXML
    private Label lblLGACLeast;

    @FXML
    private Label lblLGACMost;

    @FXML
    private Label lblModalityLeast;

    @FXML
    private Label lblModalityMost;

    @FXML
    private Label lblProposedProjects;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblRejectedProjects;

    @FXML
    private Label lblValidatedProjects;
    
    @FXML
    private Label lblAssignedProjects;

    public static AcademicBody academicBody;
    private final ProjectStatus VALIDATED_STATUS = ProjectStatus.VALIDATED;
    private final ProjectStatus REJECTED_STATUS = ProjectStatus.REJECTED;
    private final ProjectStatus PROPOSED_STATUS = ProjectStatus.PROPOSED;
    private final ProjectStatus ASSIGNED_STATUS = ProjectStatus.ASSIGNED;
    private final String NOT_FOUND_STRING = "-1";
    private final int NO_PROJECTS_FOUND = 0;
    private final String NOT_FOUND_TEXT_DISPLAY = "No se encontraron resultados";
    private ProjectGeneralReport reportFormatter = new ProjectGeneralReport();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setReportData();
    }

    public static void setAcademicBody(AcademicBody academicBody) {
        GeneralProjectReportController.academicBody = academicBody;
    }

    private void setReportData() {
        ProjectGeneralReport report = new ProjectGeneralReport();
        ProjectDAO projectDao = new ProjectDAO();
        Date currentDate = new Date();
        report.setCreationDate(currentDate);
        report.setTitle("Reporte General de Anteproyectos");
        String idCuerpoAcademico = academicBody.getKey();
        try {
            report.setValidatedProjects(projectDao.getProjectsCountByStatus(VALIDATED_STATUS.getDisplayName(), idCuerpoAcademico));
            report.setProposedProjects(projectDao.getProjectsCountByStatus(PROPOSED_STATUS.getDisplayName(), idCuerpoAcademico));
            report.setRejectedProjects(projectDao.getProjectsCountByStatus(REJECTED_STATUS.getDisplayName(), idCuerpoAcademico));
            report.setAssignedProjects(projectDao.getProjectsCountByStatus(ASSIGNED_STATUS.getDisplayName(), idCuerpoAcademico));
            report.setLgacMostUsed(projectDao.getLgacMostUsed(idCuerpoAcademico));
            report.setLgacLeastUsed(projectDao.getLgacLeastUsed(idCuerpoAcademico));
            report.setModalityLeastUsed(projectDao.getModalityLeastUsed(idCuerpoAcademico));
            report.setModalityMostUsed(projectDao.getModalityMostUsed(idCuerpoAcademico));
            report.setDirectorMostProjects(projectDao.getProfessorWithMostProjects(idCuerpoAcademico));
            report.setDirectorLeastProjects(projectDao.getProfessorWithLeastProjects(idCuerpoAcademico));
            displayReport(report);
        } catch (SQLException sqlException) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private void displayReport(ProjectGeneralReport report) {
        if ((report.getValidatedProjects() == NO_PROJECTS_FOUND) && (report.getProposedProjects() == NO_PROJECTS_FOUND) 
                && (report.getRejectedProjects() == NO_PROJECTS_FOUND && (report.getAssignedProjects() == NO_PROJECTS_FOUND))) {
            disableCreateReport();
            showNoProjectsAlert();
        } else {
            lblReportHeader.setText(report.getTitle());
            lblPublicationDate.setText(report.getReportDateFormated());
            lblValidatedProjects.setText(String.valueOf(report.getValidatedProjects()));
            lblProposedProjects.setText(String.valueOf(report.getProposedProjects()));
            lblRejectedProjects.setText(String.valueOf(report.getRejectedProjects()));
            lblAssignedProjects.setText(String.valueOf(report.getAssignedProjects()));
            lblDirectorLeastProjects.setText(report.getDirectorLeastProjects());
            lblDirectorMostProjects.setText(report.getDirectorMostProjects());
            displayLgacReportData(report);
            displayModalityReportData(report);
            setReportFormatter(report);
        }
    }

    private ProjectGeneralReport getReportFormatter() {
        return reportFormatter;
    }

    private void setReportFormatter(ProjectGeneralReport reportFormatter) {
        this.reportFormatter = reportFormatter;
    }

    private void displayLgacReportData(ProjectGeneralReport report) {
        String lgacMostUsedText = NOT_FOUND_TEXT_DISPLAY;
        String lgacLeastUsedText = NOT_FOUND_TEXT_DISPLAY;

        if (!report.getLgacMostUsed().getId().equals(NOT_FOUND_STRING)) {
            lgacMostUsedText = String.valueOf(report.getLgacMostUsed());
        }

        if (!report.getLgacLeastUsed().getId().equals(NOT_FOUND_STRING)) {
            lgacLeastUsedText = String.valueOf(report.getLgacLeastUsed());
        }

        lblLGACMost.setText(lgacMostUsedText);
        lblLGACLeast.setText(lgacLeastUsedText);
    }

    private void displayModalityReportData(ProjectGeneralReport report) {
        String modalityMostUsedText = NOT_FOUND_TEXT_DISPLAY;
        String modalityLeastUsedText = NOT_FOUND_TEXT_DISPLAY;

        if (!report.getModalityMostUsed().equals(NOT_FOUND_STRING)) {
            modalityMostUsedText = report.getModalityMostUsed();
        }

        if (!report.getModalityLeastUsed().equals(NOT_FOUND_STRING)) {
            modalityLeastUsedText = report.getModalityLeastUsed();
        }

        lblModalityMost.setText(modalityMostUsedText);
        lblModalityLeast.setText(modalityLeastUsedText);
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
                PdfWriter.getInstance(reportDocument, new FileOutputStream(selectedDirectory + "\\ReporteGeneralAnteproyectos.pdf"));
                reportDocument.open();

                Font subtitleFont = getReportFormatter().getSubtitleFont();

                Paragraph lgacSubtitle = new Paragraph("Información de las LGAC", subtitleFont);
                Paragraph modalitiesSubtitle = new Paragraph("Información de las modalidades", subtitleFont);
                Paragraph directorsSubtitle = new Paragraph("Información de los directores", subtitleFont);

                reportDocument.add(getReportFormatter().getTitleParagraph());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(getReportFormatter().getDateLineParagraph());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createProjectTable());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(lgacSubtitle);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createLgacTable());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(modalitiesSubtitle);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createModalitiesTable());
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(directorsSubtitle);
                reportDocument.add(Chunk.NEWLINE);
                reportDocument.add(createDirectorTable());

                reportDocument.close();
                DialogGenerator.getDialog(new AlertMessage(
                        "Descarga de reporte exitosa",
                        Status.SUCCESS));
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.downloadFailedAlert();
        } catch (DocumentException ex) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.downloadFailedAlert();
        }
    }

    private PdfPTable createProjectTable() {
        PdfPTable projectTable = new PdfPTable(2);
        Font textFont = getReportFormatter().getSubtitle2Font();

        String numberValidatedProjects = lblValidatedProjects.getText();
        String numberProposedProjects = lblProposedProjects.getText();
        String numberRejectedProjects = lblRejectedProjects.getText();
        String numberAssignedProjects = lblAssignedProjects.getText();

        PdfPCell validatedProjectsTitleCell = new PdfPCell(new Paragraph("Anteproyectos validados en total:", textFont));
        PdfPCell proposedProjectsTitleCell = new PdfPCell(new Paragraph("Anteproyectos propuestos en total:", textFont));
        PdfPCell rejectedProjectsTitle = new PdfPCell(new Paragraph("Anteproyectos rechazados en total:", textFont));
        PdfPCell assignedProjectsTitle = new PdfPCell(new Paragraph("Anteproyectos asignados en total:", textFont));
        PdfPCell validatedProjectsCell = new PdfPCell(new Paragraph(numberValidatedProjects));
        PdfPCell proposedProjectsCell = new PdfPCell(new Paragraph(numberProposedProjects));
        PdfPCell rejectedProjectsCell = new PdfPCell(new Paragraph(numberRejectedProjects));
        PdfPCell assignedProjectsCell = new PdfPCell(new Paragraph(numberAssignedProjects));

        projectTable.addCell(validatedProjectsTitleCell);
        projectTable.addCell(validatedProjectsCell);
        projectTable.addCell(proposedProjectsTitleCell);
        projectTable.addCell(proposedProjectsCell);
        projectTable.addCell(rejectedProjectsTitle);
        projectTable.addCell(rejectedProjectsCell);
        projectTable.addCell(assignedProjectsTitle);
        projectTable.addCell(assignedProjectsCell);

        return projectTable;
    }

    private PdfPTable createLgacTable() {
        PdfPTable lgacTable = new PdfPTable(2);
        Font textFont = getReportFormatter().getSubtitle2Font();

        String mostLgacUsed = lblLGACMost.getText();
        String leastLgacUsed = lblLGACLeast.getText();

        PdfPCell mostLgacUsedTitleCell = new PdfPCell(new Paragraph("LGAC que más se alimenta:", textFont));
        PdfPCell leastLgacUsedTitleCell = new PdfPCell(new Paragraph("LGAC que menos se alimenta:", textFont));
        PdfPCell mostLgacUsedCell = new PdfPCell(new Paragraph(mostLgacUsed));
        PdfPCell leastLgacUsedCell = new PdfPCell(new Paragraph(leastLgacUsed));

        lgacTable.addCell(mostLgacUsedTitleCell);
        lgacTable.addCell(leastLgacUsedTitleCell);
        lgacTable.addCell(mostLgacUsedCell);
        lgacTable.addCell(leastLgacUsedCell);

        return lgacTable;
    }

    private PdfPTable createModalitiesTable() {
        PdfPTable modalitiesTable = new PdfPTable(2);
        Font textFont = getReportFormatter().getSubtitle2Font();

        String mostModalityUsed = lblModalityMost.getText();
        String leastModalityUsed = lblModalityLeast.getText();

        PdfPCell mostModalityUsedTitleCell = new PdfPCell(new Paragraph("Modalidad más utilizada:", textFont));
        PdfPCell leastModalityUsedTitleCell = new PdfPCell(new Paragraph("Modalidad menos utilizada:", textFont));
        PdfPCell mostModalityUsedCell = new PdfPCell(new Paragraph(mostModalityUsed));
        PdfPCell leastModalityUsedCell = new PdfPCell(new Paragraph(leastModalityUsed));

        modalitiesTable.addCell(mostModalityUsedTitleCell);
        modalitiesTable.addCell(leastModalityUsedTitleCell);
        modalitiesTable.addCell(mostModalityUsedCell);
        modalitiesTable.addCell(leastModalityUsedCell);

        return modalitiesTable;
    }

    private PdfPTable createDirectorTable() {
        PdfPTable directorsTable = new PdfPTable(2);
        Font textFont = getReportFormatter().getSubtitle2Font();

        String directorMostProjects = lblDirectorMostProjects.getText();
        String directorLeastProjects = lblDirectorLeastProjects.getText();

        PdfPCell mostProjectsDirectorTitleCell = new PdfPCell(new Paragraph("Director con más anteproyectos concluidos:", textFont));
        PdfPCell leastProjectsDirectorTitleCell = new PdfPCell(new Paragraph("Director con menos anteproyectos concluidos:", textFont));
        PdfPCell mostProjectsDirectorCell = new PdfPCell(new Paragraph(directorMostProjects));
        PdfPCell leastProjectsDirectorCell = new PdfPCell(new Paragraph(directorLeastProjects));

        directorsTable.addCell(mostProjectsDirectorTitleCell);
        directorsTable.addCell(mostProjectsDirectorCell);
        directorsTable.addCell(leastProjectsDirectorTitleCell);
        directorsTable.addCell(leastProjectsDirectorCell);

        return directorsTable;
    }

    @FXML
    private void goBackClicked(MouseEvent event) {
        showPreviousView();
    }

    private void showNoProjectsAlert() {
        DialogGenerator.getDialog(new AlertMessage("No hay proyectos para mostrar un reporte", Status.WARNING));
    }

    private void showPreviousView() {
        SPGER.setRoot("SelectAcademicBodyForReport.fxml");
    }

    private void disableCreateReport() {
        btnDownloadReport.setDisable(true);
    }
}
