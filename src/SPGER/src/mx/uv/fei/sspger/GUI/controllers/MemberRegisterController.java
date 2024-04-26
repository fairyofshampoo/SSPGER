package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.AcademicBodyMemberRole;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;

public class MemberRegisterController implements Initializable {

    @FXML
    private TableView<TableAcademicBodyMember> tblAcademicBodyMembers;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCEmail;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCMember;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCName;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCResponsible;

    private final AcademicBodyMemberRole RESPONSIBLE_ROLE = AcademicBodyMemberRole.RESPONSIBLE;
    private final AcademicBodyMemberRole MEMBER_ROLE = AcademicBodyMemberRole.MEMBER;
    private final int VALUE_BY_DEFAULT = 0;
    private static String idAcademicBody;
    ObservableList<TableAcademicBodyMember> oblProfessorsList = FXCollections.observableArrayList();

    @FXML
    void addMembersClicked(ActionEvent event) {
        if (responsibleRegistered()) {
            if (validateMembersAdded()) {
                addMembers();
                closeWindow();
            } else {
                DialogGenerator.getDialog(new AlertMessage("No puedes agregar a más de un responsable", Status.ERROR));
            }
        } else {
            addMembers();
            closeWindow();
        }
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        closeWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTableAcademicMembers();
    }

    public static void setIdAcademicBody(String idAcademicBody) {
        MemberRegisterController.idAcademicBody = idAcademicBody;
    }

    private void closeWindow() {
        Stage stage = (Stage) tblAcademicBodyMembers.getScene().getWindow();
        stage.close();
    }

    private void initializeTableAcademicMembers() {
        List<Professor> professorList = getAllProfessors();

        if (professorList.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay profesores registrados en el sistema.", Status.WARNING));
        } else {
            for (int i = 0; i < professorList.size(); i++) {
                Professor professor = professorList.get(i);
                CheckBox chkMember = new CheckBox("" + professorList.get(i).getId());
                CheckBox chkResponsible = new CheckBox("");

                chkResponsible.setOnAction((ActionEvent event) -> {
                    if (chkResponsible.isSelected()) {
                        for (Iterator<TableAcademicBodyMember> itProfessor = oblProfessorsList.iterator(); itProfessor.hasNext();) {
                            TableAcademicBodyMember tblMember = itProfessor.next();
                            if (tblMember.getCheckBoxResponsible() != chkResponsible) {
                                tblMember.getCheckBoxResponsible().setSelected(false);
                            }
                        }
                    }
                });

                TableAcademicBodyMember tblAcademicBodyMemberNew = new TableAcademicBodyMember();
                tblAcademicBodyMemberNew.setEmail(professor.getEMail());
                tblAcademicBodyMemberNew.setName(professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName());
                tblAcademicBodyMemberNew.setCheckBoxMember(chkMember);
                tblAcademicBodyMemberNew.setCheckBoxResponsible(chkResponsible);

                oblProfessorsList.add(tblAcademicBodyMemberNew);
            }

            tblAcademicBodyMembers.setItems(oblProfessorsList);
            tblCEmail.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("email"));
            tblCName.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("name"));
            tblCMember.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxMember"));
            tblCResponsible.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxResponsible"));
        }
    }

    private List<Professor> getAllProfessors() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();

        try {
            professorList = professorDAO.getProfessorsNotSelectedByAcademicBody(idAcademicBody);
        } catch (SQLException exception) {
            Logger.getLogger(MemberRegisterController.class.getName()).log(Level.SEVERE, null, exception);
            showFailedConnectionAlert();
        }

        return professorList;
    }

    private List<AcademicBodyMember> getAcademicBodyMembers() {
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();

        for (int i = 0; i < tblAcademicBodyMembers.getItems().size(); i++) {
            if (tblAcademicBodyMembers.getItems().get(i).getCheckBoxResponsible().isSelected()) {
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();

                academicBodyMember.setId(Integer.parseInt(tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().getText()));
                academicBodyMember.setRole(RESPONSIBLE_ROLE.getDisplayName());
                academicBodyMemberList.add(academicBodyMember);
            }

            if (tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().isSelected() && !tblAcademicBodyMembers.getItems().get(i).getCheckBoxResponsible().isSelected()) {
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();

                academicBodyMember.setId(Integer.parseInt(tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().getText()));
                academicBodyMember.setRole(MEMBER_ROLE.getDisplayName());
                academicBodyMemberList.add(academicBodyMember);
            }
        }

        return academicBodyMemberList;
    }

    private boolean responsibleRegistered() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        int total = VALUE_BY_DEFAULT;
        boolean existence = false;

        try {
            total = academicBodyDao.responsibleExistence(idAcademicBody);
        } catch (SQLException ex) {
            Logger.getLogger(MemberRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }

        if (total > VALUE_BY_DEFAULT) {
            existence = true;
        }

        return existence;
    }

    private boolean validateMembersAdded() {
        List<AcademicBodyMember> memberList = getAcademicBodyMembers();
        boolean validate = true;
        int position = VALUE_BY_DEFAULT;

        while (position < memberList.size() && validate) {
            if (memberList.get(position).getRole().equals(RESPONSIBLE_ROLE.getDisplayName())) {
                validate = false;
            }

            position++;
        }

        return validate;
    }

    private void addMembers() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBodyMember> memberList = getAcademicBodyMembers();
        int result = VALUE_BY_DEFAULT;

        try {
            result = academicBodyDao.addAcademicBodyMember(memberList, idAcademicBody);
        } catch (SQLException ex) {
            Logger.getLogger(MemberRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            DialogGenerator.getDialog(new AlertMessage("Se han agregado correctamente.", Status.SUCCESS));
        }
    }

    private boolean accept() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas añadirlos como miembros del cuerpo académico?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
