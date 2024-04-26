package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.AcademicBodyMemberRole;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;

public class AcademicBodyModifierController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label lblInvalidKey;

    @FXML
    private Label lblInvalidName;

    @FXML
    private TableView<TableAcademicBodyMember> tblAcademicBodyMembers;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCEmail;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCMembers;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCName;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCResponsible;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;

    private static String academicBodyKey;
    private final AcademicBodyMemberRole RESPONSIBLE_ROLE = AcademicBodyMemberRole.RESPONSIBLE;
    private final AcademicBodyMemberRole MEMBER_ROLE = AcademicBodyMemberRole.MEMBER;
    ObservableList<TableAcademicBodyMember> oblMembersList = FXCollections.observableArrayList();
    private final int VALUE_BY_DEFAULT = 0;
    private final int NOT_FOUND_INT = -1;

    public static void setAcademicBodyKey(String key) {
        AcademicBodyModifierController.academicBodyKey = key;
    }

    @FXML
    void disableInvalidKey(KeyEvent event) {
        lblInvalidKey.setVisible(false);
    }

    @FXML
    void disableInvalidName(KeyEvent event) {
        lblInvalidName.setVisible(false);
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        if (close()) {
            AcademicBodyRegisterSavedController.setAcademicBodyKey(academicBodyKey);
            goToAcademicBodyRegisterSaved();
        }
    }

    @FXML
    void saveClicked(ActionEvent event) {
        if (save()) {
            academicBodySetFormData();
        }
    }

    @FXML
    void addMembers(ActionEvent event) {
        addMembers();
    }

    @FXML
    void deleteMembers(ActionEvent event) {
        if (delete()) {
            expellMembers();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAcademicBodyData();
        setAcademicBodyMemberTable();
    }

    private void academicBodySetFormData() {
        AcademicBody academicBody = new AcademicBody();
        boolean isValid = true;

        try {
            academicBody.setKey(txtKey.getText());
        } catch (IllegalArgumentException exception) {
            lblInvalidKey.setText(exception.getMessage());
            lblInvalidKey.setVisible(true);
            isValid = false;
        } finally {
            try {
                academicBody.setName(txtName.getText());
            } catch (IllegalArgumentException exception) {
                lblInvalidName.setText(exception.getMessage());
                lblInvalidName.setVisible(true);
                isValid = false;
            }
        }

        if (isValid) {
            updateAcademicBody(academicBody);
        }
    }

    private boolean existenceKeyValidation(AcademicBody academicBody) {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        boolean keyExists = false;
        int existences = VALUE_BY_DEFAULT;

        try {
            existences = academicBodyDao.getExistenceAcademicBody(academicBody.getKey());
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        if (existences > VALUE_BY_DEFAULT && !academicBody.getKey().equals(academicBodyKey)) {
            keyExists = true;
        }

        return keyExists;
    }

    private void updateAcademicBody(AcademicBody academicBody) {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        if (!existenceKeyValidation(academicBody)) {
            int result = VALUE_BY_DEFAULT;

            try {
                result = academicBodyDao.updateAcademicBody(academicBodyKey, academicBody);
            } catch (SQLException sqlException) {
                Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, sqlException);
                FailAlert.showFailedConnectionAlert();
            }

            if (result > VALUE_BY_DEFAULT) {
                DialogGenerator.getDialog(new AlertMessage("Cuerpo Académico modificado correctamente", Status.SUCCESS));
                setAcademicBodyKey(academicBody.getKey());
                AcademicBodyRegisterSavedController.setAcademicBodyKey(academicBodyKey);
                goToAcademicBodyRegisterSaved();
            } else {
                DialogGenerator.getDialog(new AlertMessage("No se pudo modificar el Cuerpo Académico", Status.ERROR));
            }
        } else {
            DialogGenerator.getDialog(new AlertMessage("Clave repetida, no se puede modificar el Cuerpo Académico", Status.ERROR));
        }

    }

    private List<AcademicBodyMember> getAllAcademicBodyMembers() {
        List<AcademicBodyMember> members = new ArrayList<>();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        try {
            members = academicBodyDao.getAllAcademicBodyMembers(academicBodyKey);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return members;
    }

    private List<Professor> getAllProfessors() {
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();

        try {
            professorList = professorDao.getAllProfessors();
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return professorList;
    }

    private void setAcademicBodyMemberTable() {
        List<AcademicBodyMember> memberList = getAllAcademicBodyMembers();

        if (memberList.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay miembros registrados en el sistema.", Status.WARNING));
        } else {
            for (int i = 0; i < memberList.size(); i++) {
                AcademicBodyMember academicBodyMember = memberList.get(i);
                CheckBox chkMember = new CheckBox("" + memberList.get(i).getIdAcademicBodyMember());
                TableAcademicBodyMember tblAcademicBodyMemberNew = new TableAcademicBodyMember();

                tblAcademicBodyMemberNew.setEmail(academicBodyMember.getEMail());
                tblAcademicBodyMemberNew.setName(academicBodyMember.getHonorificTitle() + " " + academicBodyMember.getName() + " " + academicBodyMember.getLastName());
                tblAcademicBodyMemberNew.setCheckBoxMember(chkMember);
                chkMember.setSelected(true);

                if (academicBodyMember.getRole().equals(RESPONSIBLE_ROLE.getDisplayName())) {
                    tblAcademicBodyMemberNew.setResponsible(memberList.get(i).getRole());
                }

                oblMembersList.add(tblAcademicBodyMemberNew);
            }
        }
        tblAcademicBodyMembers.setItems(oblMembersList);
        tblCEmail.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("email"));
        tblCName.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("name"));
        tblCMembers.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxMember"));
        tblCResponsible.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("responsible"));
    }

    private void setAcademicBodyData() {
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        try {
            academicBody = academicBodyDao.getAcademicBody(academicBodyKey);
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        txtKey.setText(academicBodyKey);
        txtName.setText(academicBody.getName());
    }

    private List<AcademicBodyMember> getMembersToExpell() {
        List<AcademicBodyMember> memberList = new ArrayList<>();

        for (int i = 0; i < oblMembersList.size(); i++) {
            if (!tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().isSelected()) {
                AcademicBodyMember member = new AcademicBodyMember();

                member.setIdAcademicBodyMember(Integer.parseInt(tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().getText()));
                memberList.add(member);
            }
        }

        return memberList;
    }

    private void expellMembers() {
        List<AcademicBodyMember> memberList = getMembersToExpell();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        if (!memberList.isEmpty()) {
            int result = VALUE_BY_DEFAULT;

            try {
                result = academicBodyDao.deleteAcademicBodyMember(memberList);
            } catch (SQLException sqlException) {
                Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, sqlException);
                FailAlert.showFailedConnectionAlert();
            }

            if (result > VALUE_BY_DEFAULT) {
                DialogGenerator.getDialog(new AlertMessage("Participantes eliminados correctamente", Status.SUCCESS));
                reloadTable();
            }
        }
    }

    private void addMembers() {
        try {
            MemberRegisterController.setIdAcademicBody(academicBodyKey);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/sspger/GUI/MemberRegister.fxml"));
            AnchorPane popup = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("");

            Scene scene = new Scene(popup);
            popupStage.setScene(scene);
            popupStage.showAndWait();

        } catch (IOException ioException) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }

        reloadTable();
    }

    private void reloadTable() {
        tblAcademicBodyMembers.getItems().clear();
        oblMembersList.clear();
        setAcademicBodyMemberTable();
    }

    private boolean close() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas cancelar la modificación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean save() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas guardar la modificación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean delete() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas eliminar a los participantes?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    private void goToAcademicBodyRegisterSaved() {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegisterSaved.fxml");
    }
}
