package mx.uv.fei.sspger.GUI.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Submission;

public class SubmissionConcludedDetailsController implements Initializable {

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblAssignmentTitle;

    @FXML
    private Label lblDeadline;

    @FXML
    private Label lblPublicationDate;

    @FXML
    private Label lblStartDate;

    @FXML
    private Label lblState;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Text ntxtDescriptionAssignment;

    @FXML
    private Pane pnNewSubmission;

    @FXML
    private Button btnAddSubmission;

    @FXML
    private Label lblFileName;

    @FXML
    private ImageView imgDeleteFile;

    @FXML
    private Pane pnAssignmentNotStarted;

    @FXML
    private Pane pnAssignmentConcludedNoSubmission;

    @FXML
    private Pane pnConcludedDelivered;

    @FXML
    private Pane pnSubmission;

    @FXML
    private TextArea txtSubmissionDescription;

    @FXML
    private Label lblDescriptionInvalid;

    @FXML
    private Group groupUploadFile;

    @FXML
    private Pane pnModifySubmission;

    @FXML
    private Button btnModifySubmission;

    @FXML
    private TextArea txtSubmissionDescriptionModify;

    @FXML
    private Group groupUploadFileModify;

    @FXML
    private Label lblDescriptionInvalidModify;

    @FXML
    private Label lblFileNameModify;

    @FXML
    private ImageView imgDeleteFileModify;

    @FXML
    private Button btnViewFeedback;

    public static int idAssignment;
    private final int NOT_FOUND = -4;
    private final int ERROR_ADDITION = -1;
    private final int UPDATE_SUCCESS = 1;
    private final String AVAILABLE_STATUS = "Disponible";
    private final String NOT_STARTED_STATUS = "Por iniciar";
    private final String FINISH_STATUS = "Finalizada";
    private String fileSourcePath = "";
    private final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    private final String FINAL_PATH = CURRENT_DIRECTORY + "/src/mx/uv/fei/sspger/files/";
    private String fileName = "";
    private String fileExtention = "";
    private String newFileName = "";
    private boolean validation = true;
    private String pathSubmissionDelivered = "";
    private int submissionId = 0;
    private int fileId = 0;
    private boolean isModificationActived = false;
    Submission submissionDelivered = new Submission();

    public static void setIdAssignment(int idAssignment) {
        SubmissionConcludedDetailsController.idAssignment = idAssignment;
    }

    void goBack(MouseEvent event) {
        showMyReceptionalWorkView();
    }

    private void displayAssignmentInfo() {
        try {
            AssignmentDAO assignmentDAO = new AssignmentDAO();
            Assignment assignment = assignmentDAO.getAssignmentById(idAssignment);
            lblAssignmentTitle.setText(assignment.getTitle());
            ntxtDescriptionAssignment.setText(assignment.getDescription());
            lblPublicationDate.setText(formatDate(assignment.getPublicationDate()));
            lblStartDate.setText(formatDate(assignment.getStartDate()));
            lblDeadline.setText(formatDate(assignment.getDeadline()));
            lblState.setText(assignment.getState());
            searchSubmission(assignment.getState());

        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }

    private void searchSubmission(String assignmentStatus) {
        try {
            SubmissionManagerDAO submissionDAO = new SubmissionManagerDAO();
            submissionDelivered = submissionDAO.getSubmissionPerAssignment(idAssignment);
            submissionId = submissionDelivered.getId();
            if (submissionId == NOT_FOUND) {
                if (assignmentStatus.equals(AVAILABLE_STATUS)) {
                    addSubmission();
                }
                if (assignmentStatus.equals(FINISH_STATUS)) {
                    pnAssignmentConcludedNoSubmission.setVisible(true);
                }

            } else {
                if (assignmentStatus.equals(AVAILABLE_STATUS)) {
                    modifySubmission(submissionDelivered);
                }
                if (assignmentStatus.equals(FINISH_STATUS)) {
                    pnConcludedDelivered.setVisible(true);
                    btnViewFeedback.setVisible(true);
                    int idFile = submissionDelivered.getFile().getId();
                    DeliverableFile originalFile = getDeliverableFile(idFile);
                    originalFile.setId(idFile);
                    submissionDelivered.setFile(originalFile);
                    showSubmissionPane();
                }
            }

            if (assignmentStatus.equals(NOT_STARTED_STATUS)) {
                pnAssignmentNotStarted.setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void showSubmissionPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/SubmissionPane.fxml"));
            Pane pnSubmissionParent = fxmlLoader.load();
            SubmissionPaneController submissionController = fxmlLoader.getController();
            submissionController.setSubmissionData(submissionDelivered);

            pnSubmission.getChildren().add(pnSubmissionParent);
        } catch (IOException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFXMLFileFailedAlert();
        }
    }

    private void addSubmission() {
        pnNewSubmission.setVisible(true);
    }

    private void modifySubmission(Submission submission) {
        setSubmissionDataToModify(submission);
    }

    private void setSubmissionDataToModify(Submission submission) {
        txtSubmissionDescriptionModify.setText(submission.getDescription());
        fileId = submission.getFile().getId();
        DeliverableFile originalFile = getDeliverableFile(submission.getFile().getId());
        newFileName = originalFile.getName();
        fileName = originalFile.getName();
        fileExtention = originalFile.getExtension();
        setLabelsNewFile(newFileName);
        submissionDelivered.setFile(originalFile);
        pathSubmissionDelivered = FINAL_PATH + newFileName;
        pnModifySubmission.setVisible(true);

    }

    private DeliverableFile getDeliverableFile(int idFile) {
        DeliverableFile file = new DeliverableFile();
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
        try {
            file = submissionDao.getFile(idFile);
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        return file;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayAssignmentInfo();
    }

    @FXML
    private void goBackClicked(MouseEvent event) {
        showMyReceptionalWorkView();
    }

    private void showMyReceptionalWorkView() {
        SPGER.setRoot("MyReceptionalWork.fxml");
    }

    @FXML
    private void uploadFileClicked(MouseEvent event) {
        if (isThereAnyFile() == true) {
            if (isFileModificationConfirmed()) {
                deleteFile(pathSubmissionDelivered);
                uploadFile();
            }
        } else {
            uploadFile();
        }
    }

    private boolean isFileModificationConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "El archivo existente se reemplazará, ¿desea continuar");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void uploadFile() {

        Stage window = (Stage) groupUploadFile.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");
        java.io.File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            fileSourcePath = selectedFile.getAbsolutePath();
            fileName = selectedFile.getName();
            fileExtention = fileName.substring(fileName.lastIndexOf(".") + 1);

            copyFile();
        } else {
            DialogGenerator.getDialog(new AlertMessage(
                    "No se seleccionó ningún archivo",
                    Status.WARNING));
        }
    }

    private void copyFile() {
        File sourceFile = new File(fileSourcePath);
        Path destinationPath = Paths.get(FINAL_PATH);

        try {
            Path sourcePath = sourceFile.toPath();
            String fileNameWithoutExtension = removeFileExtension(sourcePath.getFileName().toString());
            String fileExtension = getFileExtension(sourcePath.getFileName().toString());

            newFileName = fileNameWithoutExtension + "_" + idAssignment + "." + fileExtension;
            fileName = newFileName;
            Path targetPath = destinationPath.resolve(newFileName);

            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
            }
            setLabelsNewFile(newFileName);
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            DialogGenerator.getDialog(new AlertMessage(
                    "Archivo copiado exitosamente",
                    Status.SUCCESS));

            pathSubmissionDelivered = FINAL_PATH + newFileName;
        } catch (IOException iOException) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.WARNING, null, iOException);
            DialogGenerator.getDialog(new AlertMessage(
                    "Error al copiar el archivo",
                    Status.ERROR));
        }
    }

    private void setLabelsNewFile(String newFileName) {
        lblFileName.setText(newFileName);
        lblFileName.setVisible(true);
        imgDeleteFile.setVisible(true);

        lblFileNameModify.setText(newFileName);
        lblFileNameModify.setVisible(true);
        imgDeleteFileModify.setVisible(true);
    }

    private String getFileExtension(String fileName) {
        String extension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
        }

        return extension;
    }

    private String removeFileExtension(String fileName) {
        String newFileNameWithoutExtension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            newFileNameWithoutExtension = fileName.substring(0, dotIndex);
        }
        return newFileNameWithoutExtension;
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    @FXML
    private void addSubmissionClicked(ActionEvent event) {
        validation = true;
        if (isThereAnyFile()) {
            setDataNewSubmissionForm();
        } else {
            DialogGenerator.getDialog(new AlertMessage("No hay archivos en su entrega", Status.WARNING));
        }
    }

    private boolean isThereAnyFile() {
        if (newFileName.equals("")) {
            validation = false;
        }
        return validation;
    }

    private void setDataNewSubmissionForm() {
        Submission submission = new Submission();
        try {
            submission.setDescription(txtSubmissionDescription.getText());
        } catch (IllegalArgumentException descriptionException) {
            lblDescriptionInvalid.setVisible(true);
            validation = false;
        }

        if (validation == true) {
            registerFile(submission);
        }
    }

    private void setModificationDataSubmissionForm() {
        try {
            submissionDelivered.setDescription(txtSubmissionDescriptionModify.getText());
        } catch (IllegalArgumentException descriptionException) {
            lblDescriptionInvalidModify.setVisible(true);
            validation = false;
        }

        if (validation == true) {
            registerFile(submissionDelivered);
        }
    }

    private void registerFile(Submission submission) {
        boolean validateFile = true;
        DeliverableFile file = new DeliverableFile();
        file.setId(fileId);
        try {
            file.setExtension(fileExtention);
            file.setName(fileName);
            file.setPath(FINAL_PATH);
        } catch (IllegalArgumentException fileFullNameException) {
            validateFile = false;
            DialogGenerator.getDialog(new AlertMessage("Reduzca el tamaño del nombre del archivo, es muy largo", Status.WARNING));
        }

        if (validateFile == true && validation == true) {
            Date currentDate = new Date();
            submission.setFile(file);
            submission.setDeliveryDate(currentDate);
            submission.getAssignment().setId(idAssignment);

            if (isModificationActived) {
                registerModification(submission);
            } else {
                registerSubmission(submission);
            }
        }
    }

    private void registerSubmission(Submission submission) {
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
        try {
            if (submissionDao.addSubmission(submission) != ERROR_ADDITION) {
                DialogGenerator.getDialog(new AlertMessage("Entrega registrada", Status.SUCCESS));
                showMyReceptionalWorkView();
            } else {
                DialogGenerator.getDialog(new AlertMessage("Entrega no registrada", Status.ERROR));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void registerModification(Submission submission) {
        SubmissionManagerDAO submissionDao = new SubmissionManagerDAO();
        try {
            int result = submissionDao.modifySubmission(submission);
            if (result == UPDATE_SUCCESS) {
                DialogGenerator.getDialog(new AlertMessage("Entrega modificada", Status.SUCCESS));
                showMyReceptionalWorkView();
            } else {
                DialogGenerator.getDialog(new AlertMessage("Entrega no modificada", Status.ERROR));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubmissionConcludedDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    @FXML
    private void typingDescription(KeyEvent event) {
        lblDescriptionInvalidModify.setVisible(false);
        lblDescriptionInvalid.setVisible(false);
    }

    private void desactivateLabels() {
        lblFileName.setVisible(false);
        imgDeleteFile.setVisible(false);

        lblFileNameModify.setVisible(false);
        imgDeleteFileModify.setVisible(false);
    }

    @FXML
    private void deleteFileClicked(MouseEvent event) {
        deleteFile(pathSubmissionDelivered);
    }

    private void deleteFile(String filePath) {
        File deleteFile = new File(filePath);
        newFileName = "";
        desactivateLabels();
        if (deleteFile.delete()) {
            DialogGenerator.getDialog(new AlertMessage("Archivo eliminado", Status.SUCCESS));
        }
    }

    @FXML
    private void uploadFileButtonClicked(ActionEvent event) {
        uploadFile();
    }

    @FXML
    private void modifySubmissionClicked(ActionEvent event) {
        validation = true;
        isModificationActived = true;
        if (isThereAnyFile()) {
            setModificationDataSubmissionForm();
        } else {
            DialogGenerator.getDialog(new AlertMessage("No hay archivos en su entrega", Status.WARNING));
        }
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

    @FXML
    private void viewFeedbackButtonClicked(ActionEvent event) {
        StudentViewFeedbackController.setIdSubmission(submissionId);
        SPGER.setRoot("StudentViewFeedback.fxml");
    }
}
