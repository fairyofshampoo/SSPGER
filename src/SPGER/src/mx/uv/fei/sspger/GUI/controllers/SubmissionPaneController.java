package mx.uv.fei.sspger.GUI.controllers;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.DeliverableFile;

public class SubmissionPaneController {

    @FXML
    private Pane pnSubmissionParent;
    
    @FXML
    private Label lblDeliveryDate;
    
    @FXML
    private Text ntxtDescriptionSubmission;
    
    @FXML
    private ImageView imgPdfFile;
    
    @FXML
    private ImageView imgDownload;
    
    @FXML
    private Label lblFileTitle;

    private Submission submissionForCard;
    private String currentDirectory = System.getProperty("user.dir");
    private String originalPath = currentDirectory + "/src/mx/uv/fei/sspger/files/";
    private String fileName = "";

    public void setSubmissionData(Submission submission) {
        submissionForCard = submission;
        lblDeliveryDate.setText(getDateFormated(submission.getDeliveryDate()));
        ntxtDescriptionSubmission.setText(submission.getDescription());
        DeliverableFile file = getDeliverableFile(submission.getFile().getId());
        fileName = file.getName();
        lblFileTitle.setText(fileName);
    }

    public String getDateFormated(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    @FXML
    private void downloadImageClicked(MouseEvent event) {
        submissionForCard.getFile();
        selectDirectory();
    }

    private void selectDirectory() {
        Stage DirectoryStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(DirectoryStage);
        if (selectedDirectory == null) {
            DialogGenerator.getDialog(new AlertMessage(
                    "No se seleccionó directorio para la descarga",
                    Status.WARNING));
        } else {
            copyFile(selectedDirectory.getAbsolutePath());
        }
    }

    private void copyFile(String destinationDirectory) {
        File sourceFile = new File(originalPath + fileName);
        Path sourcePath = sourceFile.toPath();
        Path targetPath = Paths.get(destinationDirectory, sourceFile.getName());
        try {
            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
            }
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            DialogGenerator.getDialog(new AlertMessage(
                    "Archivo descargado exitosamente",
                    Status.SUCCESS));
        } catch (IOException iOException) {
            Logger.getLogger(SubmissionPaneController.class.getName()).log(Level.WARNING, null, iOException);
            FailAlert.downloadFailedAlert();
        }
    }

    private DeliverableFile getDeliverableFile(int idFile) {
        DeliverableFile file = new DeliverableFile();
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
        try {
            file = submissionDao.getFile(idFile);
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionPaneController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
        return file;
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

}
