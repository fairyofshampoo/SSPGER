package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class AddAssignmentController implements Initializable {

    private final int PROFESSOR_ID = UserSession.getInstance().getUserId();
    private static int receptionalWorkId;
    private Assignment assignment = new Assignment();
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @FXML
    private DatePicker dtpDeadline;

    @FXML
    private DatePicker dtpStartDate;

    @FXML
    private Spinner<Integer> spnEndHour;

    @FXML
    private Spinner<Integer> spnStartHour;

    @FXML
    private Spinner<Integer> spnStartMinute;

    @FXML
    private Spinner<Integer> spnEndMinute;

    @FXML
    private Label lblReceptionalWorkName;

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
        AddAssignmentController.receptionalWorkId = receptionalWorkId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setRestrictionForFields();
        setReceptionalWorkName();
        setSpinners();
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> valueFactoryStartHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> valueFactoryEndHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);

        spnStartHour.setValueFactory(valueFactoryStartHours);
        spnEndHour.setValueFactory(valueFactoryEndHours);

        SpinnerValueFactory<Integer> valueFactoryStartMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> valueFactoryEndMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        spnEndMinute.setValueFactory(valueFactoryEndMinutes);
        spnStartMinute.setValueFactory(valueFactoryStartMinutes);
    }

    private void setReceptionalWorkName() {
        try {
            ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
            ReceptionalWork receptionalWork = new ReceptionalWork();

            receptionalWork = receptionalWorkDao.getReceptionalWorkName(receptionalWorkId);
            lblReceptionalWorkName.setText(receptionalWork.getName());
        } catch (SQLException sqlException) {
            Logger.getLogger(AddAssignmentController.class.getName()).log(Level.SEVERE, null, sqlException);
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
    void registerAssignment(ActionEvent event) throws SQLException {
        if (validFields()) {
            AssignmentDAO assignmentDao = new AssignmentDAO();

            setAssignment();

            try {
                if (assignmentDao.registerAssignment(assignment, PROFESSOR_ID) == 1) {
                    SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork.fxml");

                    DialogGenerator.getDialog(new AlertMessage(
                            "Asignación registrada exitosamente.",
                            Status.SUCCESS));
                } else {
                    DialogGenerator.getDialog(new AlertMessage(
                            "Algo salio mal. \n\t:/",
                            Status.WARNING));
                }

            } catch (SQLException sqlException) {
                FailAlert.showFailedConnectionAlert();
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
            java.util.Date now = new java.util.Date();

            if (deadline.compareTo(startDate) < 0) {
                result = false;
                DialogGenerator.getDialog(new AlertMessage(
                        "La fecha de inicio debe ir antes del fin de la actividad.",
                        Status.ERROR));
            }
            if (startDate.compareTo(now) < 0) {
                result = false;
                DialogGenerator.getDialog(new AlertMessage(
                        "La fecha de inicio debe ir despues de la fecha actual.",
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
}
