package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.DAO.UserDAO;
import mx.uv.fei.sspger.logic.HonorificTitles;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.UserTypes;

public class UserModifierController implements Initializable {

    @FXML
    private TextField txtEMail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtIdUser;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblInvalidEMail;
    @FXML
    private Label lblInvalidId;
    @FXML
    private Label lblInvalidPassword;
    @FXML
    private CheckBox chkAdmin;
    @FXML
    private Label lblInvalidHonorificTitle;
    @FXML
    private Label lblInvalidLastName;
    @FXML
    private ChoiceBox<String> cbxHonorificTitle;
    @FXML
    private TextField txtLastName;
    @FXML
    private Label lblHonorificTitle;
    @FXML
    private Label lblInvalidName;
    @FXML
    private TextField txtName;
    @FXML
    private Label lblUserType;

    public static int idUser;
    public static String userType;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    private final int ACTIVE = 1;
    private final int NOT_ACTIVE = 0;
    private final int ERROR = -1;
    private final int NO_EMAIL_FOUND = 0;
    private final int SUCESSFUL_TRANSACTION = 2;
    private String eMail = "user@uv.mx";
    boolean passwordChanged = false;

    private void setHonorificTitleComboBox() {
        for (HonorificTitles honorificTitles : HonorificTitles.values()) {
            cbxHonorificTitle.getItems().add(honorificTitles.getDisplayName());
        }
    }

    public static void setIdUser(int idUser) {
        UserModifierController.idUser = idUser;
    }

    public static void setUserType(String userType) {
        UserModifierController.userType = userType;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setHonorificTitleComboBox();
        verifyUserType();
    }

    @FXML
    void typingEMail(KeyEvent event) {
        lblInvalidEMail.setVisible(false);
    }

    @FXML
    void typingId(KeyEvent event) {
        lblInvalidId.setVisible(false);
    }

    @FXML
    void typingLastName(KeyEvent event) {
        lblInvalidLastName.setVisible(false);
    }

    @FXML
    void typingName(KeyEvent event) {
        lblInvalidName.setVisible(false);
    }

    @FXML
    void typingPassword(KeyEvent event) {
        lblInvalidPassword.setVisible(false);
    }

    @FXML
    private void acceptButtonClick(ActionEvent event) {
        if (isModificationConfirmed()) {
            if (continueModification()) {
                setDataByUserType();
            }
        }
    }

    private void setDataByUserType() {
        if (STUDENT_TYPE.getDisplayName().equals(userType)) {
            studentSetFormData();
        } else {
            professorSetFormData();
        }
    }

    private boolean continueModification() {
        boolean isModificationAuthorized = true;
        passwordChanged = isPasswordChanged();

        if (passwordChanged == true) {
            if (!isPasswordChangedConfirmed()) {
                isModificationAuthorized = false;
                passwordChanged = false;
            }
        }
        return isModificationAuthorized;
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
        if (isExitConfirmed()) {
            showUserDetailsView();
        }
    }

    private void verifyUserType() {
        lblUserType.setText(userType);

        if (userType.equals(STUDENT_TYPE.getDisplayName())) {
            disableProfessorDetails();
            getStudentData();
        }

        if (userType.equals(PROFESSOR_TYPE.getDisplayName())) {
            getProfessorData();
        }
    }

    private void getStudentData() {
        try {
            StudentDAO studentDao = new StudentDAO();
            Student student = studentDao.getStudent(idUser);
            txtName.setText(student.getName());
            txtIdUser.setText(student.getRegistrationTag());
            txtLastName.setText(student.getLastName());
            eMail = student.getEMail();
            txtEMail.setText(eMail);
        } catch (SQLException ex) {
            Logger.getLogger(UserModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void getPrivilegesInfo(int adminPrivileges) {
        chkAdmin.setSelected(false);

        if (adminPrivileges == ACTIVE) {
            chkAdmin.setSelected(true);
        }
    }

    private void getProfessorData() {
        try {
            ProfessorDAO professorDao = new ProfessorDAO();
            Professor professor = professorDao.getProfessor(idUser);
            txtName.setText(professor.getName());
            txtIdUser.setText(professor.getPersonalNumber());
            txtLastName.setText(professor.getLastName());
            cbxHonorificTitle.setValue(professor.getHonorificTitle());
            eMail = professor.getEMail();
            txtEMail.setText(eMail);
            getPrivilegesInfo(professor.getIsAdmin());
        } catch (SQLException ex) {
            Logger.getLogger(UserModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void disableProfessorDetails() {
        cbxHonorificTitle.setVisible(false);
        lblHonorificTitle.setVisible(false);
        chkAdmin.setVisible(false);
    }

    private void showUserDetailsView() {
        UserDetailsController.setIdUser(idUser);
        UserDetailsController.setUserType(userType);
        SPGER.setRoot("UserDetails.fxml");
    }

    private boolean isExitConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas salir de la modificación del usuario?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void professorModifier(Professor professor, boolean isPasswordChanged) {
        ProfessorDAO professorDao = new ProfessorDAO();
        int result = ERROR;
        try {
            if (validateEMailDuplications(professor.getEMail())) {
                if (isPasswordChanged) {
                    result = professorDao.updateProfessorWithPasswordTransaction(eMail, professor);
                } else {
                    result = professorDao.updateProfessorTransaction(eMail, professor);
                }
            }

            if (result == SUCESSFUL_TRANSACTION) {
                showModificationSucessfulAlert();
            } else {
                showModificationFailedAlert();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        } finally {
            showUserDetailsView();
        }
    }

    private void studentModifier(Student student, boolean isPasswordChanged) {
        StudentDAO studentDao = new StudentDAO();
        int result = ERROR;
        try {
            if (validateEMailDuplications(student.getEMail())) {
                if (isPasswordChanged) {
                    result = studentDao.updateStudentWithPasswordTransaction(eMail, student);
                } else {
                    result = studentDao.updateStudentTransaction(eMail, student);
                }
            }
            if (result == SUCESSFUL_TRANSACTION) {
                showModificationSucessfulAlert();
            } else {
                showModificationFailedAlert();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        } finally {
            showUserDetailsView();
        }
    }

    private void professorSetFormData() {
        Professor professor = new Professor();
        professor.setStatus(ACTIVE);
        boolean validation = true;
        try {
            professor.setEMail(txtEMail.getText());
        } catch (IllegalArgumentException eMailException) {
            lblInvalidEMail.setVisible(true);
            lblInvalidEMail.setText("Correo inválido:\n" + eMailException.getMessage());
            validation = false;
        } finally {
            try {
                professor.setName(txtName.getText());
            } catch (IllegalArgumentException nameException) {
                lblInvalidName.setVisible(true);
                lblInvalidName.setText("Nombre inválido:\n" + nameException.getMessage());
                validation = false;
            } finally {
                try {
                    professor.setLastName(txtLastName.getText());
                } catch (IllegalArgumentException lastNameException) {
                    lblInvalidLastName.setVisible(true);
                    lblInvalidLastName.setText("Apellido inválido:\n" + lastNameException.getMessage());
                    validation = false;
                } finally {
                    try {
                        professor.setPersonalNumber(txtIdUser.getText());
                    } catch (IllegalArgumentException personalNumberException) {
                        lblInvalidId.setVisible(true);
                        lblInvalidId.setText("Número personal inválido:\n" + personalNumberException.getMessage());
                        validation = false;
                    } finally {
                        if (cbxHonorificTitle.getValue() == null) {
                            lblInvalidHonorificTitle.setVisible(true);
                            validation = false;
                        } else {
                            professor.setHonorificTitle(cbxHonorificTitle.getValue());
                        }
                        if (chkAdmin.isSelected()) {
                            professor.setIsAdmin(ACTIVE);
                        } else {
                            professor.setIsAdmin(NOT_ACTIVE);
                        }
                        if (passwordChanged) {
                            try {
                                professor.setPassword(txtPassword.getText());
                            } catch (IllegalArgumentException passwordException) {
                                lblInvalidPassword.setVisible(true);
                                lblInvalidPassword.setText("Contraseña inválida:\n" + passwordException.getMessage());
                                validation = false;
                            }
                        }
                    }
                }
            }
        }
        if (validation == true) {
            professorModifier(professor, passwordChanged);
        }
    }

    private void studentSetFormData() {
        Student student = new Student();
        student.setStatus(ACTIVE);
        boolean validation = true;
        try {
            student.setEMail(txtEMail.getText());
        } catch (IllegalArgumentException eMailException) {
            lblInvalidEMail.setVisible(true);
            lblInvalidEMail.setText("Correo inválido:\n" + eMailException.getMessage());
            validation = false;
        } finally {
            try {
                student.setName(txtName.getText());
            } catch (IllegalArgumentException nameException) {
                lblInvalidName.setVisible(true);
                lblInvalidName.setText("Nombre inválido:\n" + nameException.getMessage());
                validation = false;
            } finally {
                try {
                    student.setLastName(txtLastName.getText());
                } catch (IllegalArgumentException lastNameException) {
                    lblInvalidLastName.setVisible(true);
                    lblInvalidLastName.setText("Apellido inválido:\n" + lastNameException.getMessage());
                    validation = false;
                } finally {
                    try {
                        student.setRegistrationTag(txtIdUser.getText());
                    } catch (IllegalArgumentException registrationTagException) {
                        lblInvalidId.setVisible(true);
                        lblInvalidId.setText("Matrícula inválida:\n" + registrationTagException.getMessage());
                        validation = false;
                    } finally {
                        if (passwordChanged) {
                            try {
                                student.setPassword(txtPassword.getText());
                            } catch (IllegalArgumentException passwordException) {
                                lblInvalidPassword.setVisible(true);
                                lblInvalidPassword.setText("Contraseña inválida:\n" + passwordException.getMessage());
                                validation = false;
                            }
                        }
                    }
                }
            }
        }

        if (validation == true) {
            studentModifier(student, passwordChanged);
        }
    }

    private boolean validateEMailDuplications(String eMailToValidate) {
        boolean validation = false;
        UserDAO userDAO = new UserDAO();
        if (eMail.equals(eMailToValidate)) {
            validation = true;
        } else {
            try {
                if (userDAO.searchEmailDuplication(eMailToValidate) == NO_EMAIL_FOUND) {
                    validation = true;
                } else {
                    DialogGenerator.getDialog(new AlertMessage("Este correo ya ha sido registrado en el sistema", Status.WARNING));
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserModifierController.class.getName()).log(Level.SEVERE, null, ex);
                showFailedConnectionAlert();
            }
        }
        return validation;
    }

    private boolean isPasswordChanged() {
        return (!txtPassword.getText().isEmpty() || !txtPassword.getText().isBlank());
    }

    private boolean isPasswordChangedConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "La contraseña del usuario se va a modificar, ¿desea continuar?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private boolean isModificationConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "Los datos del usuario se modificaran, ¿desea continuar?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void showModificationSucessfulAlert() {
        DialogGenerator.getDialog(new AlertMessage("Modificación exitosa", Status.SUCCESS));
    }

    private void showModificationFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Modificación fallida", Status.ERROR));
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

}
