package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Date;
import java.text.SimpleDateFormat;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
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
    private Label lblTitleSystem;

    @FXML
    private Label lblValidatedProjects;
    
    private final ProjectStatus VALIDATED_STATUS = ProjectStatus.VALIDATED;
    private final ProjectStatus REJECTED_STATUS = ProjectStatus.REJECTED;
    private final ProjectStatus PROPOSED_STATUS = ProjectStatus.PROPOSED;
    
    @FXML
    void downloadReport(ActionEvent event) {
        try {
            Document reportDocument = new Document();
            
            String path = System.getProperty("user.dir");
            PdfWriter.getInstance(reportDocument, new FileOutputStream(path + "\\reporte.pdf"));
            reportDocument.open();
            
            String reportDate = getReportDate();
            Font titleFont = new Font();
            Font subtitleFont = new Font();
            
            titleFont.setColor(45, 82, 100);
            titleFont.setSize(20);
            titleFont.setStyle(Font.BOLD);
            subtitleFont.setSize(14);
            subtitleFont.setStyle(Font.BOLD);
            
            Paragraph title = new Paragraph("Reporte General de Anteproyectos", titleFont);
            Paragraph dateLine = new Paragraph("Fecha de generación de reporte: " + reportDate, subtitleFont);
            Paragraph lgacSubtitle = new Paragraph("Información de las LGAC", subtitleFont);
            Paragraph modalitiesSubtitle = new Paragraph("Información de las modalidades", subtitleFont);
            Paragraph directorsSubtitle = new Paragraph("Información de los directores", subtitleFont);
            
            reportDocument.add(title);
            reportDocument.add(Chunk.NEWLINE);
            reportDocument.add(dateLine);
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
            DialogGenerator.getDialog(new AlertMessage (
                "Descarga de reporte exitosa",
                Status.SUCCESS));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private PdfPTable createProjectTable(){
        PdfPTable projectTable = new PdfPTable(2);
        Font textFont = new Font();
        
        textFont.setStyle(Font.BOLD);
        
        String numberValidatedProjects = lblValidatedProjects.getText();
        String numberProposedProjects = lblProposedProjects.getText();
        String numberRejectedProjects = lblRejectedProjects.getText();
        
        PdfPCell validatedProjectsTitleCell = new PdfPCell(new Paragraph("Anteproyectos validados en total:", textFont));
        PdfPCell proposedProjectsTitleCell = new PdfPCell(new Paragraph("Anteproyectos propuestos en total:", textFont));
        PdfPCell rejectedProjectsTitle = new PdfPCell(new Paragraph("Anteproyectos rechazados en total:", textFont));
        PdfPCell validatedProjectsCell = new PdfPCell(new Paragraph(numberValidatedProjects));
        PdfPCell proposedProjectsCell = new PdfPCell(new Paragraph(numberProposedProjects));
        PdfPCell rejectedProjectsCell = new PdfPCell(new Paragraph(numberRejectedProjects));
        
        projectTable.addCell(validatedProjectsTitleCell);
        projectTable.addCell(validatedProjectsCell);
        projectTable.addCell(proposedProjectsTitleCell);
        projectTable.addCell(proposedProjectsCell);
        projectTable.addCell(rejectedProjectsTitle);
        projectTable.addCell(rejectedProjectsCell);
        
        return projectTable;
    }
    
    private PdfPTable createLgacTable(){
        PdfPTable lgacTable = new PdfPTable(2);
        Font textFont = new Font();
        
        textFont.setStyle(Font.BOLD);
        
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
    
    private PdfPTable createModalitiesTable(){
        PdfPTable modalitiesTable = new PdfPTable(2);
        Font textFont = new Font();
        
        textFont.setStyle(Font.BOLD);
        
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
    
    private PdfPTable createDirectorTable(){
        PdfPTable directorsTable = new PdfPTable(2);
        Font textFont = new Font();
        
        textFont.setStyle(Font.BOLD);
        
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
    private String getReportDate() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(currentDate);
    }
    private void displayReport(){
        try {
            ProjectDAO projectDAO = new ProjectDAO();
            lblPublicationDate.setText(getReportDate());
            lblValidatedProjects.setText(Integer.toString(projectDAO.getProjectsCountByStatus(VALIDATED_STATUS.getDisplayName())));
            lblRejectedProjects.setText(Integer.toString(projectDAO.getProjectsCountByStatus(REJECTED_STATUS.getDisplayName())));
            lblProposedProjects.setText(Integer.toString(projectDAO.getProjectsCountByStatus(PROPOSED_STATUS.getDisplayName())));
            lblLGACMost.setText(projectDAO.getLgacMostUsed().getId() +" " + projectDAO.getLgacMostUsed().getName());
            lblLGACLeast.setText(projectDAO.getLgacLeastUsed().getId() +" " + projectDAO.getLgacLeastUsed().getName());
        } catch (SQLException ex) {
            Logger.getLogger(GeneralProjectReportController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayReport();
    }    
    
}
