package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Modality;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.ReceptionalWorkStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class ReceptionalWorkModifierController implements Initializable {

    @FXML
    private Button btnAbandoned;

    @FXML
    private Button btnConcluded;

    @FXML
    private Button btnSaved;

    @FXML
    private ChoiceBox<String> cbxModality;

    @FXML
    private Label lblInvalidDescription;

    @FXML
    private Label lblInvalidModality;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblInvalidResults;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtResults;

    @FXML
    private VBox vboxCheckContent;

    private final int VALUE_BY_DEFAULT = 0;
    private final String ABANDONED_STATUS = ReceptionalWorkStatus.ABANDONED.getDisplayName();
    private final String CONCLUDED_STATUS = ReceptionalWorkStatus.CONCLUDED.getDisplayName();
    private static int idReceptionalWork;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setReceptionalWorkData();
        modalityChoiceBoxListener();
        setModalityChoiceBox();
    }

    @FXML
    void abandonedClicked(ActionEvent event) {
        if (abandonedConfirmed()) {
            abandoneReceptionalWork();
        }
    }

    @FXML
    void concludedClicked(ActionEvent event) {
        if (concludedConfirmed()) {
            concludeReceptionalWork();
        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        if (cancelConfirmed()) {
            goBack();
        }
    }

    @FXML
    void saveClicked(ActionEvent event) {
        setReceptionalWorkFormat();
    }

    public static void setIdReceptionalWork(int idReceptionalWork) {
        ReceptionalWorkModifierController.idReceptionalWork = idReceptionalWork;
    }

    private void setReceptionalWorkFormat() {
        ReceptionalWork receptionalWork = new ReceptionalWork();
        boolean validation = true;

        receptionalWork.setIdReceptionalWork(idReceptionalWork);

        try {
            receptionalWork.setName(txtName.getText());
        } catch (IllegalArgumentException projectNameException) {
            lblInvalidName.setVisible(true);
            validation = false;
        } finally {
            try {
                receptionalWork.setDescription(txtDescription.getText());
            } catch (IllegalArgumentException projectDescriptionException) {
                lblInvalidDescription.setVisible(true);
                validation = false;
            } finally {
                try {
                    receptionalWork.setResults(txtResults.getText());
                } catch (IllegalArgumentException expectedResultsException) {
                    lblInvalidResults.setVisible(true);
                    validation = false;
                } finally {
                    if (cbxModality.getValue() == null) {
                        lblInvalidModality.setVisible(true);
                        validation = false;
                    } else {
                        receptionalWork.setModality(cbxModality.getValue());
                    }
                }
            }
        }

        if (validation) {
            if (saveConfirmed()) {
                updateReceptionalWork(receptionalWork);
            }
        }
    }

    private void updateReceptionalWork(ReceptionalWork receptionalWork) {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        int result = VALUE_BY_DEFAULT;

        try {
            result = receptionalWorkDao.updateReceptionalWork(receptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkModifierController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Trabajo Recepcional actualizado correctamente", Status.SUCCESS));
            goBack();
        } else {
            DialogGenerator.getDialog(new AlertMessage("Ha ocurrido un error", Status.ERROR));
        }
    }

    private void setReceptionalWorkData() {
        ReceptionalWork receptionalWork = getReceptionalWork();

        txtName.setText(receptionalWork.getName());
        txtDescription.setText(receptionalWork.getDescription());
        txtResults.setText(receptionalWork.getResults());

        String modality = receptionalWork.getModality();
        cbxModality.setValue(modality);

        if (receptionalWork.getState().equals(ABANDONED_STATUS)) {
            btnAbandoned.setVisible(false);
            btnAbandoned.setDisable(true);
            btnSaved.setDisable(true);
            btnConcluded.setDisable(true);
            btnConcluded.setVisible(false);

            Label lblAbandonedMessage = new Label("Trabajo Recepcional Abandonado");
            lblAbandonedMessage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vboxCheckContent.getChildren().add(lblAbandonedMessage);
        }

        if (receptionalWork.getState().equals(CONCLUDED_STATUS)) {
            btnAbandoned.setVisible(false);
            btnAbandoned.setDisable(true);
            btnSaved.setDisable(true);
            btnConcluded.setDisable(true);
            btnConcluded.setVisible(false);

            Label lblConcludedMessage = new Label("Trabajo Recepcional Concluido");
            lblConcludedMessage.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            vboxCheckContent.getChildren().add(lblConcludedMessage);
        }
    }

    private void setModalityChoiceBox() {
        for (Modality modalities : Modality.values()) {
            cbxModality.getItems().add(modalities.getDisplayName());
        }
    }

    private void modalityChoiceBoxListener() {
        cbxModality.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblInvalidModality.setVisible(false);
            }
        });
    }

    private List<Student> getStudentsRelated() {
        List<Student> studentList = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();

        try {
            studentList = studentDao.getStudentPerReceptionalWork(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkModifierController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return studentList;
    }

    private ReceptionalWork getReceptionalWork() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        try {
            receptionalWork = receptionalWorkDao.getRecepetionalWorkById(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkModifierController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return receptionalWork;
    }

    private void abandoneReceptionalWork() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWork = getReceptionalWork();
        List<Student> studentList = getStudentsRelated();
        int result = VALUE_BY_DEFAULT;

        try {
            result = receptionalWorkDao.abandoneReceptionalWork(studentList, receptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkModifierController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Se ha abandonado correctamente el trabajo recepcional", Status.SUCCESS));
            goBack();
        } else {
            DialogGenerator.getDialog(new AlertMessage("Hubo un error al establecer como abandonado", Status.SUCCESS));
        }
    }

    private void concludeReceptionalWork() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWork = getReceptionalWork();
        int result = VALUE_BY_DEFAULT;

        try {
            result = receptionalWorkDao.concludeReceptionalWork(receptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkModifierController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Se ha concluido correctamente el trabajo recepcional", Status.SUCCESS));
            goBack();
        } else {
            DialogGenerator.getDialog(new AlertMessage("Hubo un error al establecer como concluido", Status.SUCCESS));
        }
    }

    @FXML
    void txtDescriptionTyped(KeyEvent event) {
        lblInvalidDescription.setVisible(false);
    }

    @FXML
    void txtNameTyped(KeyEvent event) {
        lblInvalidName.setVisible(false);
    }

    @FXML
    void txtResultsTyped(KeyEvent event) {
        lblInvalidResults.setVisible(false);
    }

    private boolean saveConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas guardar el registro?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean cancelConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas cancelar el registro?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean abandonedConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas abandonar el trabajo recepcional? Este cambio es permanente");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean concludedConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas concluir el trabajo recepcional? Este cambio es permanente");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void goBack() {
        ViewReceptionalWorkController.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork.fxml");
    }

}
