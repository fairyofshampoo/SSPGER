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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCResponsible;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;
    
    private static String academicBodyKey;
    private final AcademicBodyMemberRole RESPONSIBLE_ROLE = AcademicBodyMemberRole.RESPONSIBLE;
    private final AcademicBodyMemberRole MEMBER_ROLE = AcademicBodyMemberRole.MEMBER; 
    ObservableList<TableAcademicBodyMember> oblProfessorsList = FXCollections.observableArrayList();
    private final int VALUE_BY_DEFAULT = 0;
    private final int NOT_FOUND_INT = -1;
    
    public static void setAcademicBodyKey(String key){
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
        if(close()){
            AcademicBodyRegisterSavedController.setAcademicBodyKey(academicBodyKey);
            goToAcademicBodyRegisterSaved();
        }        
    }

    @FXML
    void saveClicked(ActionEvent event) {
        if(save()){
            academicBodySetFormData();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAcademicBodyData();
        initializeTableAcademicMembers();
        setAcademicBodyMembersData();
    }
    
    private void academicBodySetFormData(){
        AcademicBody academicBody = new AcademicBody();
        boolean isValid = true;
        
        try{
            academicBody.setKey(txtKey.getText());
        }catch(IllegalArgumentException exception){
            lblInvalidKey.setText(exception.getMessage());
            lblInvalidKey.setVisible(true);
            isValid = false;
        }finally{
            try{
                academicBody.setName(txtName.getText());
            }catch(IllegalArgumentException exception){
                lblInvalidName.setText(exception.getMessage());
                lblInvalidName.setVisible(true);
                isValid = false;
            } 
        }
        
        if(isValid){
            academicBody.setMember(getNewAcademicBodyMembers());
            updateAcademicBody(academicBody);
        }
    }
    
    private boolean existenceKeyValidation(AcademicBody academicBody){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        boolean keyExists = false;
        int existences = VALUE_BY_DEFAULT;

        try {
            existences = academicBodyDao.getExistenceAcademicBody(academicBody.getKey());
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        if(existences > VALUE_BY_DEFAULT && !academicBody.getKey().equals(academicBodyKey)){
            keyExists = true;
        }
        
        return keyExists;
    }
    
    private void updateAcademicBody(AcademicBody academicBody){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBodyMember> oldMembers = getOldAcademicBodyMembers();
        
        if(!existenceKeyValidation(academicBody)){
            int result = VALUE_BY_DEFAULT;
        
            try {
                result = academicBodyDao.updateAcademicBodyTransaction(academicBodyKey, academicBody, oldMembers);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
                showFailedConnectionAlert();
            }

            if(result > VALUE_BY_DEFAULT){
                DialogGenerator.getDialog(new AlertMessage ("Cuerpo Académico modificado correctamente",Status.SUCCESS));
                setAcademicBodyKey(academicBody.getKey());
                AcademicBodyRegisterSavedController.setAcademicBodyKey(academicBodyKey);
                goToAcademicBodyRegisterSaved();
            }else{
                DialogGenerator.getDialog(new AlertMessage ("No se pudo modificar el Cuerpo Académico",Status.ERROR));
            }
        }else{
            DialogGenerator.getDialog(new AlertMessage ("Clave repetida, no se puede modificar el Cuerpo Académico",Status.ERROR));
        }
        
    }
    
    private List<AcademicBodyMember> getNewAcademicBodyMembers(){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBodyMember> memberList = new ArrayList<>();
        AcademicBodyMember responsible = new AcademicBodyMember();
        
        for(int i = VALUE_BY_DEFAULT; i < oblProfessorsList.size(); i++){
            if(tblAcademicBodyMembers.getItems().get(i).getCheckBoxMember().isSelected()){
                AcademicBodyMember member = new AcademicBodyMember();
                try {
                    member = academicBodyDao.getProfessor(tblAcademicBodyMembers.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
                    showFailedConnectionAlert();
                }
                member.setRole(MEMBER_ROLE.getDisplayName());
                memberList.add(member);
            }
            
            if(tblAcademicBodyMembers.getItems().get(i).getCheckBoxResponsible().isSelected()){
                try {
                    responsible = academicBodyDao.getProfessor(tblAcademicBodyMembers.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
                    showFailedConnectionAlert();
                }
                responsible.setRole(RESPONSIBLE_ROLE.getDisplayName());
                memberList.add(responsible);
            }
        }
        
        return memberList;
    }
    
    private List<AcademicBodyMember> getOldAcademicBodyMembers(){
        List<AcademicBodyMember> oldMembers = getAcademicBodyMembers();
        AcademicBodyMember oldResponsible = getAcademicBodyResponsible();
        
        if(oldResponsible.getId() != NOT_FOUND_INT){
            oldMembers.add(oldResponsible);
        }
        
        return oldMembers;
    }
    
    private List<AcademicBodyMember> getAcademicBodyMembers(){
        List<AcademicBodyMember> memberList = new ArrayList<>();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        try {
            memberList = academicBodyDao.getAcademicBodyMembers(academicBodyKey);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        return memberList;
    }
    
    private AcademicBodyMember getAcademicBodyResponsible(){
        AcademicBodyMember responsible = new AcademicBodyMember();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        try {
            responsible = academicBodyDao.getAcademicBodyResponsible(academicBodyKey);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        return responsible;
    }
    
    private List<Professor> getAllProfessors(){
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        
        try {
            professorList = professorDao.getAllProfessors();
        } catch (SQLException exception) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, exception);
            showFailedConnectionAlert();
        }
        
        return professorList;
    }
    
    private void initializeTableAcademicMembers(){
        List<Professor> professorList = getAllProfessors();
        
        if(professorList.isEmpty()){
            DialogGenerator.getDialog(new AlertMessage ("No hay profesores registrados en el sistema.",Status.WARNING));
        }
        
        for(int i = 0; i < professorList.size(); i++){
            Professor professor = professorList.get(i);           
            CheckBox chkMember = new CheckBox("" + professorList.get(i));
            CheckBox chkResponsible = new CheckBox("" + professorList.get(i));
            
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
        tblCMembers.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxMember"));
        tblCResponsible.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxResponsible") );
    }
    
    private void setAcademicBodyData(){
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        try {
            academicBody = academicBodyDao.getAcademicBody(academicBodyKey);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyModifierController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        txtKey.setText(academicBodyKey);
        txtName.setText(academicBody.getName());
    }
    
    private void setAcademicBodyMembersData(){
        AcademicBodyMember responsible = getAcademicBodyResponsible();
        List<AcademicBodyMember> memberList = getAcademicBodyMembers();
        
        for(int i = 0; i < oblProfessorsList.size(); i++){
            if(oblProfessorsList.get(i).email.equals(responsible.getEMail())){
                oblProfessorsList.get(i).getCheckBoxResponsible().setSelected(true);
            }
            
            for(int j = 0; j < memberList.size(); j++){
                if(oblProfessorsList.get(i).email.equals(memberList.get(j).getEMail())){
                    oblProfessorsList.get(i).getCheckBoxMember().setSelected(true);
                }
            }
        }
    }
    
    private boolean close(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas cancelar la modificación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private boolean save(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas guardar la modificación?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private void showFailedConnectionAlert(){
        DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.",Status.FATAL));
    }
    
    private void goToAcademicBodyRegisterSaved(){
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegisterSaved.fxml");
    }
}
