package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.DAO.UserDAO;
import mx.uv.fei.sspger.logic.UserTypes;
import mx.uv.fei.sspger.logic.HonorificTitles;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class UserRegisterController implements Initializable {

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<String> cbxHonorificTitle;

    @FXML
    private ChoiceBox<String> cbxUserType;

    @FXML
    private TextField txtEMail;

    @FXML
    private TextField txtIdUser;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label lblInvalidEMail;

    @FXML
    private Label lblInvalidHonorificTitle;

    @FXML
    private Label lblInvalidId;

    @FXML
    private Label lblInvalidLastName;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblInvalidPassword;

    @FXML
    private Label lblInvalidUserType;

    @FXML
    private CheckBox chkAdmin;

    @FXML
    private Label lblHonorificTitle;

    private final UserTypes STUDENT_TYPE = UserTypes.STUDENT;
    private final UserTypes PROFESSOR_TYPE = UserTypes.PROFESSOR;
    private final int ACTIVE = 1;
    private final int SUCESSFUL_TRANSACTION = 2;
    private final int NO_EMAIL_FOUND = 0;

    @FXML
    void acceptButtonClick(ActionEvent event) {
        if (cbxUserType.getValue() != null) {
            registerUser(cbxUserType.getValue());
        } else {
            lblInvalidUserType.setVisible(true);
            DialogGenerator.getDialog(new AlertMessage(
                    "No ha seleccionado tipo de usuario a registrar",
                    Status.WARNING));
        }
    }

    void registerUser(String userType) {
        if (STUDENT_TYPE.getDisplayName().equals(userType)) {
            studentSetFormData();
        } else {
            professorSetFormData();
        }
    }

    private void disableProfessorOptions(String userType) {
        if (STUDENT_TYPE.getDisplayName().equals(userType)) {
            cbxHonorificTitle.setVisible(false);
            chkAdmin.setVisible(false);
            lblHonorificTitle.setVisible(false);

        }
        if (PROFESSOR_TYPE.getDisplayName().equals(userType)) {
            cbxHonorificTitle.setVisible(true);
            lblHonorificTitle.setVisible(true);
            chkAdmin.setVisible(true);
        }
        lblInvalidUserType.setVisible(false);
        lblInvalidHonorificTitle.setVisible(false);

    }

    private boolean validateEMailDuplications(String email) {
        boolean validation = false;
        UserDAO userDAO = new UserDAO();
        try {
            if (userDAO.searchEmailDuplication(email) == NO_EMAIL_FOUND) {
                validation = true;
            } else {
                DialogGenerator.getDialog(new AlertMessage("Este correo ya ha sido registrado en el sistema", Status.WARNING));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        return validation;
    }

    private void professorRegister(Professor professor) {
        ProfessorDAO professorDAO = new ProfessorDAO();
        try {
            if (validateEMailDuplications(professor.getEMail())) {
                if (professorDAO.addProfessorTransaction(professor) == SUCESSFUL_TRANSACTION) {
                    DialogGenerator.getDialog(new AlertMessage(
                            "Profesor registrado con éxito",
                            Status.SUCCESS));
                    goToUsersManagerView();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
    }

    private void studentRegister(Student student) {
        try {
            StudentDAO accessAccountDAO = new StudentDAO();
            if (validateEMailDuplications(student.getEMail())) {
                if (accessAccountDAO.addStudentTransaction(student) == SUCESSFUL_TRANSACTION) {
                    DialogGenerator.getDialog(new AlertMessage(
                            "Estudiante registrado con éxito",
                            Status.SUCCESS));
                    goToUsersManagerView();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
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
                        professor.setPassword(txtPassword.getText());
                    } catch (IllegalArgumentException passwordException) {
                        lblInvalidPassword.setVisible(true);
                        lblInvalidPassword.setText("Contraseña inválida:\n" + passwordException.getMessage());
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
                            }
                        }
                    }
                }
            }
        }
        if (validation == true) {
            professorRegister(professor);
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
                        student.setPassword(txtPassword.getText());
                    } catch (IllegalArgumentException passwordException) {
                        lblInvalidPassword.setVisible(true);
                        lblInvalidPassword.setText("Contraseña inválida:\n" + passwordException.getMessage());
                        validation = false;
                    } finally {
                        try {
                            student.setRegistrationTag(txtIdUser.getText());
                        } catch (IllegalArgumentException registrationTagException) {
                            lblInvalidId.setVisible(true);
                            lblInvalidId.setText("Matrícula inválida:\n" + registrationTagException.getMessage());
                            validation = false;
                        }
                    }
                }
            }
        }
        if (validation == true) {
            studentRegister(student);
        }
    }

    private void setUserTypeChoiceBox() {
        for (UserTypes userType : UserTypes.values()) {
            cbxUserType.getItems().add(userType.getDisplayName());
        }
    }

    private void setHonorificTitleChoiceBox() {
        for (HonorificTitles honorificTitles : HonorificTitles.values()) {
            cbxHonorificTitle.getItems().add(honorificTitles.getDisplayName());
        }
    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        if (isExitConfirmed()) {
            goToUsersManagerView();
        }
    }

    private boolean isExitConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas salir del registro de usuario?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void goToUsersManagerView() {
        SPGER.setRoot("UsersManager.fxml");
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

    private void userTypeChoiceBoxListener() {
        cbxUserType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                disableProfessorOptions(newValue);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUserTypeChoiceBox();
        setHonorificTitleChoiceBox();
        userTypeChoiceBoxListener();
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

}
