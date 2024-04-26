package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import java.sql.Date;
import java.util.Calendar;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Status;

public class ModifyAssignmentController implements Initializable {

    private static int receptionalWorkId;
    private static int idAssignment;
    private Assignment assignment = new Assignment();
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    private DatePicker dtpDeadline;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private Spinner<Integer> spnStartMinute;

    @FXML
    private Spinner<Integer> spnStartHour;

    @FXML
    private Spinner<Integer> spnEndHour;

    @FXML
    private Spinner<Integer> spnEndMinute;

    @FXML
    private Label lblProyectName;

    @FXML
    private Label lblRemainingCharacters;

    @FXML
    private Label lblMissingAssignmentTitle;

    @FXML
    private Label lblMissingStartDate;

    @FXML
    private Label lblMissingDeadline;

    @FXML
    private TextArea txtAssignmentDescription;

    @FXML
    private TextField txtAssignmentTitle;

    public static void setReceptionalWorkId(int receptionalWorkId) {
        ModifyAssignmentController.receptionalWorkId = receptionalWorkId;
    }

    public static void setAssignment(int idAssignment) {
        ModifyAssignmentController.idAssignment = idAssignment;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRestrictionForFields();
        setReceptionalWorkName();
        setGraphicElements();
        setSpinners();
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> valueFactoryStartHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, getHour(assignment.getStartDate()));
        SpinnerValueFactory<Integer> valueFactoryEndHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, getHour(assignment.getDeadline()));

        spnStartHour.setValueFactory(valueFactoryStartHours);
        spnEndHour.setValueFactory(valueFactoryEndHours);

        SpinnerValueFactory<Integer> valueFactoryStartMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, getMinute(assignment.getStartDate()));
        SpinnerValueFactory<Integer> valueFactoryEndMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, getMinute(assignment.getDeadline()));

        spnEndMinute.setValueFactory(valueFactoryEndMinutes);
        spnStartMinute.setValueFactory(valueFactoryStartMinutes);
    }

    private int getHour(java.sql.Date sqlDate) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utilDate);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return hour;
    }

    private int getMinute(java.sql.Date sqlDate) {
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utilDate);

        int minute = calendar.get(Calendar.MINUTE);

        return minute;
    }

    private void setGraphicElements() {
        AssignmentDAO assignmentDao = new AssignmentDAO();

        try {
            assignment = assignmentDao.getAssignmentById(idAssignment);
        } catch (SQLException sqlException) {
            Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        txtAssignmentDescription.setText(assignment.getDescription());
        txtAssignmentTitle.setText(assignment.getTitle());

        Date dateDeadline = assignment.getDeadline();
        LocalDate deadline = dateDeadline.toLocalDate();

        dtpDeadline.setValue(deadline);

        Date dateStartDate = assignment.getStartDate();
        LocalDate startDate = dateStartDate.toLocalDate();

        dtpStartDate.setValue(startDate);
    }

    private void setReceptionalWorkName() {
        try {
            ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
            ReceptionalWork receptionalWork = new ReceptionalWork();

            receptionalWork = receptionalWorkDao.getReceptionalWorkName(receptionalWorkId);
            lblProyectName.setText(receptionalWork.getName());
        } catch (SQLException sqlException) {
            Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private void setRestrictionForFields() {
        UnaryOperator<TextFormatter.Change> descriptionRestriction = change -> {
            if (change.getControlNewText().matches("^[\\p{L}\\p{M}\\p{P}\\p{S}\\p{N}\\p{Z}\\p{C}]{0,4999}$")) {
                return change;
            } else {
                return null;
            }
        };

        UnaryOperator<TextFormatter.Change> titleRestriction = change -> {
            if (change.getControlNewText().matches("^[\\p{L}\\p{M}\\p{P}\\p{S}\\p{N}\\p{Z}\\p{C}]{0,99}$")) {
                return change;
            } else {
                return null;
            }
        };

        txtAssignmentDescription.setTextFormatter(new TextFormatter<>(descriptionRestriction));
        txtAssignmentTitle.setTextFormatter(new TextFormatter<>(titleRestriction));
    }

    @FXML
    void cancelAssignmentCreation(ActionEvent event) throws IOException {
        if (isConfirmedExit()) {
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork.fxml");
        }
    }

    private boolean isConfirmedExit() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de modificar asignación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    @FXML
    void updateAssignment(ActionEvent event) throws SQLException {
        if (validFields()) {
            AssignmentDAO assignmentDao = new AssignmentDAO();

            setAssignment();

            try {
                if (assignmentDao.updateAssignment(assignment) == 1) {
                    SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork.fxml");

                    DialogGenerator.getDialog(new AlertMessage(
                            "Asignación modificada exitosamente.",
                            Status.SUCCESS));
                } else {
                    DialogGenerator.getDialog(new AlertMessage(
                            "Algo salio mal. \n\t:/",
                            Status.WARNING));
                }

            } catch (SQLException sqlException) {
                DialogGenerator.getDialog(new AlertMessage(
                        "Problema en la conexion a la base de datos.",
                        Status.FATAL));
                Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
            }
        }
    }

    private boolean validFields() {
        boolean result = true;

        if (FieldValidation.doesNotExceedLenghtTxtArea(txtAssignmentDescription, 4999)) {
            result = false;
        }
        if (FieldValidation.isNullOrEmptyTxtField(txtAssignmentTitle)) {
            result = false;
            lblMissingAssignmentTitle.setVisible(true);
        }
        if (dtpDeadline.getValue() == null) {
            result = false;
            lblMissingDeadline.setVisible(true);
        }
        if (dtpStartDate.getValue() == null) {
            result = false;
            lblMissingStartDate.setVisible(true);
        }
        if (dtpStartDate.getValue() != null && dtpDeadline != null) {

            java.util.Date startDate = setUtilDateStart();
            java.util.Date deadline = setUtilDateEnd();
            java.util.Date publicationDate = new java.util.Date(assignment.getPublicationDate().getTime());

            if (deadline.compareTo(startDate) < 0) {
                result = false;
                DialogGenerator.getDialog(new AlertMessage(
                        "La fecha de inicio debe ir antes del fin de la actividad.",
                        Status.ERROR));
            }
            if (startDate.compareTo(publicationDate) < 0) {
                result = false;
                DialogGenerator.getDialog(new AlertMessage(
                        "La fecha de inicio debe ir despues de la fecha de publicación.",
                        Status.ERROR));
            }
        } else {
            result = false;
        }

        return result;
    }

    private java.util.Date setUtilDateStart() {
        LocalDate localDate = dtpStartDate.getValue();
        java.util.Date utilDate = Date.valueOf(localDate);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(utilDate);
        calendar.add(Calendar.MINUTE, spnStartMinute.getValue());
        calendar.add(Calendar.HOUR_OF_DAY, spnStartHour.getValue());

        utilDate = calendar.getTime();

        return utilDate;
    }

    private java.util.Date setUtilDateEnd() {
        LocalDate localDate = dtpDeadline.getValue();
        java.util.Date utilDate = Date.valueOf(localDate);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(utilDate);
        calendar.add(Calendar.MINUTE, spnEndMinute.getValue());
        calendar.add(Calendar.HOUR_OF_DAY, spnEndHour.getValue());

        utilDate = calendar.getTime();

        return utilDate;
    }

    private void setAssignment() {
        assignment.setTitle(txtAssignmentTitle.getText());
        assignment.setDescription(txtAssignmentDescription.getText());
        assignment.setStartDate(setDateStart());
        assignment.setDeadline(setDateEnd());
        assignment.setIdProject(receptionalWorkId);
    }

    private java.sql.Date setDateStart() {
        LocalDate localDate = dtpStartDate.getValue();
        java.util.Date utilDate = Date.valueOf(localDate);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(utilDate);
        calendar.add(Calendar.MINUTE, spnStartMinute.getValue());
        calendar.add(Calendar.HOUR_OF_DAY, spnStartHour.getValue());

        utilDate = calendar.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    private java.sql.Date setDateEnd() {
        LocalDate localDate = dtpDeadline.getValue();
        java.util.Date utilDate = Date.valueOf(localDate);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(utilDate);
        calendar.add(Calendar.MINUTE, spnEndMinute.getValue());
        calendar.add(Calendar.HOUR_OF_DAY, spnEndHour.getValue());

        utilDate = calendar.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return sqlDate;
    }

    @FXML
    private void remainingCharactersInDescription(KeyEvent keyEvent) {
        txtAssignmentDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            int remainingCharacters = 4999 - txtAssignmentDescription.getText().length();
            lblRemainingCharacters.setText("Caracteres disponibles: " + remainingCharacters);
        });
        if (FieldValidation.doesNotExceedLenghtTxtArea(txtAssignmentDescription, 4500)) {
            lblRemainingCharacters.setStyle("-fx-text-fill: red");
        } else {
            lblRemainingCharacters.setStyle("-fx-text-fill: black");
        }
    }

    @FXML
    private void setMissingAssignmentTitleInvisible(KeyEvent keyEvent) {
        lblMissingAssignmentTitle.setVisible(false);
    }

    @FXML
    private void setMissingStartDateInvisible(ActionEvent event) {
        lblMissingStartDate.setVisible(false);
    }

    @FXML
    private void setMissingDeadlineInvisible(ActionEvent event) {
        lblMissingDeadline.setVisible(false);
    }

    @FXML
    void removeAssignment(ActionEvent event) {
        AssignmentDAO assignmentDao = new AssignmentDAO();

        try {
            if (assignmentDao.deleteAssignment(assignment) == 1) {

                DialogGenerator.getDialog(new AlertMessage(
                        "Asignación eliminada exitosamente.",
                        Status.SUCCESS));
            } else {
                DialogGenerator.getDialog(new AlertMessage(
                        "Algo salio mal. \n\t:/",
                        Status.WARNING));
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ModifyAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

}
