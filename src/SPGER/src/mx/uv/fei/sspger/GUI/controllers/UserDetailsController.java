package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.DAO.UserDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.UserSession;
import mx.uv.fei.sspger.logic.UserTypes;

public class UserDetailsController implements Initializable {

    @FXML
    private Label lblPrincipalHonorificTitle;

    @FXML
    private Label lblAdminPrivileges;

    @FXML
    private Label lblIsAdmin;

    @FXML
    private Label lblUserStatus;

    @FXML
    private Label lblName;

    @FXML
    private Label lblUserType;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblHonorificTitle;

    @FXML
    private Label lblEMail;

    @FXML
    private Label lblIdUser;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Button btnModifyUser;

    @FXML
    private Button btnChangeUserStatus;

    private static int idUser;
    private static String userType;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    private final int ACTIVE = 1;
    private final int NOT_ACTIVE = 0;
    private final int SUCCESS_UPDATE = 1;
    private String eMail = "user@uv.mx";
    private int accountStatus = 0;

    @FXML
    void goBack(MouseEvent event) {
        showUsersManagerView();
    }

    private void showUsersManagerView() {
        SPGER.setRoot("UsersManager.fxml");
    }

    private void verifyUserType() {
        lblUserType.setText(userType);

        if (userType.equals(STUDENT_TYPE.getDisplayName())) {
            disableProfessorDetails();
            setStudentData();
        }

        if (userType.equals(PROFESSOR_TYPE.getDisplayName())) {
            setProfessorData();
        }
    }

    private void setStudentData() {
        try {
            StudentDAO studentDao = new StudentDAO();
            Student student = studentDao.getStudent(idUser);
            lblName.setText(student.getName());
            lblIdUser.setText(student.getRegistrationTag());
            lblLastName.setText(student.getLastName());
            eMail = student.getEMail();
            lblEMail.setText(eMail);
            accountStatus = student.getStatus();
            lblUserStatus.setText(setStatusInfo(accountStatus));
        } catch (SQLException ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private String setStatusInfo(int status) {
        String statusText = "Desactivada";

        if (status == ACTIVE) {
            statusText = "Activa";
            accountStatus = ACTIVE;
            btnChangeUserStatus.setText("Desactivar cuenta");
        }

        return statusText;
    }

    private String setPrivilegesInfo(int adminPrivileges) {
        String statusText = "Desactivados";

        if (adminPrivileges == ACTIVE) {
            statusText = "Activados";
        }

        return statusText;
    }

    private void setProfessorData() {
        try {
            ProfessorDAO professorDao = new ProfessorDAO();
            Professor professor = professorDao.getProfessor(idUser);
            lblName.setText(professor.getName());
            lblIdUser.setText(professor.getPersonalNumber());
            lblLastName.setText(professor.getLastName());
            lblHonorificTitle.setText(professor.getHonorificTitle());
            eMail = professor.getEMail();
            lblEMail.setText(eMail);
            accountStatus = professor.getStatus();
            lblUserStatus.setText(setStatusInfo(accountStatus));
            lblIsAdmin.setText(setPrivilegesInfo(professor.getIsAdmin()));
        } catch (SQLException ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void disableProfessorDetails() {
        lblPrincipalHonorificTitle.setVisible(false);
        lblHonorificTitle.setVisible(false);
        lblIsAdmin.setVisible(false);
        lblAdminPrivileges.setVisible(false);
    }

    public static void setIdUser(int idUser) {
        UserDetailsController.idUser = idUser;
    }

    public static void setUserType(String userType) {
        UserDetailsController.userType = userType;
    }

    private void disableModificationButtons() {
        if (idUser == UserSession.getInstance().getUserId()) {
            btnModifyUser.setVisible(false);
            btnChangeUserStatus.setVisible(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        verifyUserType();
        disableModificationButtons();
    }

    @FXML
    private void modifyUserClicked(ActionEvent event) {
        showModifyView();
    }

    private void showModifyView() {
        UserModifierController.setIdUser(idUser);
        UserModifierController.setUserType(userType);
        SPGER.setRoot("UserModifier.fxml");
    }

    @FXML
    private void changeUserStatusClicked(ActionEvent event) {
        if (isStatusChangedConfirmed()) {
            changeStatus();
        }
    }

    private void changeStatus() {
        boolean isStatusChanged = false;

        UserDAO userDao = new UserDAO();
        try {
            if (accountStatus == ACTIVE) {
                if (userDao.updateStatus(eMail, NOT_ACTIVE) == SUCCESS_UPDATE) {
                    DialogGenerator.getDialog(new AlertMessage("Cuenta desactivada con éxito", Status.SUCCESS));
                    isStatusChanged = true;
                }
            } else {
                if (userDao.updateStatus(eMail, ACTIVE) == SUCCESS_UPDATE) {
                    DialogGenerator.getDialog(new AlertMessage("Cuenta activada con éxito", Status.SUCCESS));
                    isStatusChanged = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }

        if (isStatusChanged == false) {
            DialogGenerator.getDialog(new AlertMessage("No fue posible cambiar el estado de la cuenta", Status.ERROR));
        }
        showUsersManagerView();
    }

    private boolean isStatusChangedConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas cambiar el estado de la cuenta del usuario?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

}
