package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Status;

public class AcademicBodyRegisterSavedController implements Initializable {

    @FXML
    private Button btnUpdateStatus;

    @FXML
    private ImageView btnGoBack;

    @FXML
    private Button btnModify;

    @FXML
    private Label lblAcademicBodyKeyData;

    @FXML
    private Label lblAcademicBodyNameData;

    @FXML
    private Label lblAcademicBodyResponsible;

    @FXML
    private Label lblStatus;

    @FXML
    private VBox vboxAcademicBodyMembersContent;

    private static String academicBodyKey;
    private final int VALUE_BY_DEFAULT = 0;
    private final int NOT_FOUND_INT = -1;
    private final int ACTIVE_STATUS = 1;

    @FXML
    void goBackClicked(MouseEvent event) {
        goToAcademicBodyManager();
    }

    @FXML
    void updateStatusClicked(ActionEvent event) {
        if (confirmUpdateMessage()) {
            changeStatus();
        }
    }

    @FXML
    void modifyClicked(ActionEvent event) {
        AcademicBodyModifierController.setAcademicBodyKey(academicBodyKey);
        goToAcademicBodyModifier();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setButtonStatusMessage();
        setAcademicBodyData();
        setAcademicBodyMembersData();
    }

    public static void setAcademicBodyKey(String key) {
        AcademicBodyRegisterSavedController.academicBodyKey = key;
    }

    private void setAcademicBodyData() {
        AcademicBody academicBody = getAcademicBody();

        lblAcademicBodyKeyData.setText(academicBodyKey);
        lblAcademicBodyNameData.setText(academicBody.getName());

        if (academicBody.getStatus() == ACTIVE_STATUS) {
            lblStatus.setText("ACTIVO");
        } else {
            lblStatus.setText("INACTIVO");
        }
    }

    private void setAcademicBodyMembersData() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBodyMember> memberList = new ArrayList<>();
        AcademicBodyMember responsible = new AcademicBodyMember();

        try {
            memberList = academicBodyDao.getAcademicBodyMembers(academicBodyKey);
            responsible = academicBodyDao.getAcademicBodyResponsible(academicBodyKey);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        for (int i = VALUE_BY_DEFAULT; i < memberList.size(); i++) {
            Label lblMember = new Label();
            lblMember.setText(" - " + memberList.get(i).getHonorificTitle() + " " + memberList.get(i).getName() + " " + memberList.get(i).getLastName());
            vboxAcademicBodyMembersContent.getChildren().add(lblMember);
        }

        if (memberList.isEmpty()) {
            Label lblMember = new Label();
            lblMember.setText("No hay miembros registrados");
            vboxAcademicBodyMembersContent.getChildren().add(lblMember);
        }

        if (responsible.getIdAcademicBodyMember() == NOT_FOUND_INT) {
            lblAcademicBodyResponsible.setText("No hay un responsable registrado");
        } else {
            lblAcademicBodyResponsible.setText(responsible.getHonorificTitle() + " " + responsible.getName() + " " + responsible.getLastName());
        }
    }

    private AcademicBody getAcademicBody() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        AcademicBody academicBody = new AcademicBody();

        try {
            academicBody = academicBodyDao.getAcademicBody(academicBodyKey);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return academicBody;
    }

    private void setButtonStatusMessage() {
        AcademicBody academicBody = getAcademicBody();
        if (academicBody.getStatus() == ACTIVE_STATUS) {
            btnUpdateStatus.setText("Desactivar");
        } else {
            btnUpdateStatus.setText("Activar");
        }
    }

    private boolean confirmUpdateMessage() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas " + btnUpdateStatus.getText() + " el Cuerpo Académico?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void changeStatus() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        AcademicBody academicBody = getAcademicBody();
        int result = VALUE_BY_DEFAULT;

        if (academicBody.getStatus() == ACTIVE_STATUS) {
            academicBody.setStatus(VALUE_BY_DEFAULT);
        } else {
            academicBody.setStatus(ACTIVE_STATUS);
        }

        try {
            result = academicBodyDao.updateAcademicBodyStatus(academicBody.getStatus(), academicBodyKey);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Cuerpo Académico actualizado correctamente", Status.SUCCESS));
            goToAcademicBodyManager();
        } else {
            DialogGenerator.getDialog(new AlertMessage("No se pudo actualizar el estado del Cuerpo Académico", Status.ERROR));
        }
    }

    private void goToAcademicBodyManager() {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyManager.fxml");
    }

    private void goToAcademicBodyModifier() {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyModifier.fxml");
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
