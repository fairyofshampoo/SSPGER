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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.AcademicBodyMemberRole;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.Status;


public class AcademicBodyRegisterController implements Initializable {
    @FXML
    private Button btnAcept;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblInvalidKey;

    @FXML
    private Label lblInvalidName;

    @FXML
    private TableView<TableAcademicBodyMember> tblAcademicBodyMember;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCProfessorName;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCProfessorResponsible;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCProfessorSelect;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCProfessorEmail;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;
    
    private final int VALUE_BY_DEFAULT = 0;
    private final int ACTIVE_STATUS = 1;
    private final AcademicBodyMemberRole RESPONSIBLE_ROLE = AcademicBodyMemberRole.RESPONSIBLE;
    private final AcademicBodyMemberRole MEMBER_ROLE = AcademicBodyMemberRole.MEMBER;
    ObservableList<TableAcademicBodyMember> oblProfessorsList = FXCollections.observableArrayList();
    
    @FXML
    void acceptClicked(ActionEvent event) {
        academicBodySetFormData();
    }
    
    @FXML
    void cancelClicked(ActionEvent event) {
        if(cancel()){
            goToAcademicBodyManager();
        }
    }
    
    @FXML
    void disableInvalidLabelKey(KeyEvent event) {
        lblInvalidKey.setVisible(false);
    }
    
    @FXML
    void disableInvalidLabelName(KeyEvent event) {
        lblInvalidName.setVisible(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTableAcademicMembers();
    }
    
    private List<Professor> getAllProfessors(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        
        try {
            professorList = professorDAO.getAllProfessors();
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
        
        tblAcademicBodyMember.setItems(oblProfessorsList);
        tblCProfessorEmail.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("email"));
        tblCProfessorName.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("name"));
        tblCProfessorSelect.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxMember"));
        tblCProfessorResponsible.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxResponsible") );
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
            academicBody.setStatus(ACTIVE_STATUS);
            academicBody.setMember(getAcademicBodyMembers());
            addAcademicBody(academicBody);
        }
    }
    
    private boolean existenceKeyValidation(String key){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        boolean keyExists = false;
        int existences = VALUE_BY_DEFAULT;

        try {
            existences = academicBodyDao.getExistenceAcademicBody(key);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }
        
        if(existences > VALUE_BY_DEFAULT){
            keyExists = true;
        }
        
        return keyExists;
    }
    
    private void addAcademicBody(AcademicBody academicBody){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        if(!existenceKeyValidation(academicBody.getKey())){
            int result = VALUE_BY_DEFAULT;
            
            try {
                result = academicBodyDao.addAcademicBodyTransaction(academicBody);
            } catch (SQLException ex) {
                Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                showFailedConnectionAlert();
            }
                
            if(result > VALUE_BY_DEFAULT){
                DialogGenerator.getDialog(new AlertMessage ("Cuerpo Académico registrado exitosamente",Status.SUCCESS));
                goToAcademicBodyManager();
            }else{
                DialogGenerator.getDialog(new AlertMessage ("No se pudo registrar el Cuerpo Académico",Status.ERROR));
            }            
        }else{
            DialogGenerator.getDialog(new AlertMessage ("Clave repetida, no se puede registrar el Cuerpo Académico",Status.ERROR));
        }
    }
    
    private List<AcademicBodyMember> getAcademicBodyMembers(){
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();
        
        for(int i = 0; i < tblAcademicBodyMember.getItems().size(); i++){
            if(tblAcademicBodyMember.getItems().get(i).getCheckBoxResponsible().isSelected()){
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();
                
                try {
                    academicBodyMember = academicBodyDAO.getProfessor(tblAcademicBodyMember.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    showFailedConnectionAlert();
                }
                
                academicBodyMember.setRole(RESPONSIBLE_ROLE.getDisplayName());
                academicBodyMemberList.add(academicBodyMember);
            }
            
            if(tblAcademicBodyMember.getItems().get(i).getCheckBoxMember().isSelected() && !tblAcademicBodyMember.getItems().get(i).getCheckBoxResponsible().isSelected()){
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();
                
                try {
                    academicBodyMember = academicBodyDAO.getProfessor(tblAcademicBodyMember.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    showFailedConnectionAlert();
                }
                
                academicBodyMember.setRole(MEMBER_ROLE.getDisplayName());
                academicBodyMemberList.add(academicBodyMember);
            }
        }
        
        return academicBodyMemberList;
    }
    
    private boolean cancel(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas cancelar el registro?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private void showFailedConnectionAlert(){
        DialogGenerator.getDialog(new AlertMessage ("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.",Status.FATAL));
    }
    
    private void goToAcademicBodyManager(){
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyManager.fxml");
    }
}
