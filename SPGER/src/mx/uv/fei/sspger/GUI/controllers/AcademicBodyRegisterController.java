package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
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
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.Status;


public class AcademicBodyRegisterController implements Initializable {
    
    private final int VALUE_BY_DEFAULT = 0;
    
    @FXML
    private Button btnAcept;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblAcademicBody;

    @FXML
    private Label lblAcademicBodyInfo;

    @FXML
    private Label lblInvalidKey;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblKey;

    @FXML
    private Label lblMembers;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private TableView<TableAcademicBodyMember> tblAcademicBodyMember;

    @FXML
    private TableColumn tblCProfessorName;

    @FXML
    private TableColumn tblCProfessorResponsible;

    @FXML
    private TableColumn tblCProfessorSelect;

    @FXML
    private TableColumn tblCProfessorEmail;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;
    
    ObservableList<TableAcademicBodyMember> oblProfessorsList = FXCollections.observableArrayList();
    
    @FXML
    void addButtonAceptEvent(ActionEvent event) {
        if(!isEmptyField()){
            addAcademicBody();
        }
    }
    
    @FXML
    void cancelButtonAction(ActionEvent event) {
        if(cancel()){
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyManager.fxml");
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
    
    public void initializeTableAcademicMembers(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> professorList = new ArrayList<>();
        
        try {
            professorList = professorDAO.getAllProfessors();
        } catch (SQLException exception) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, exception);
            DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
        }
        
        for(int i = 0; i < professorList.size(); i++){
            Professor professor = professorList.get(i);           
            CheckBox checkBoxMember = new CheckBox("" + professorList.get(i));
            CheckBox checkBoxResponsible = new CheckBox("" + professorList.get(i));
            
            checkBoxResponsible.setOnAction((ActionEvent event) -> {
                if (checkBoxResponsible.isSelected()) {
                    for (Iterator<TableAcademicBodyMember> itProfessor = oblProfessorsList.iterator(); itProfessor.hasNext();) {
                        TableAcademicBodyMember tblMember = itProfessor.next();
                        if (tblMember.getCheckBoxResponsible() != checkBoxResponsible) {
                            tblMember.getCheckBoxResponsible().setSelected(false);
                        }
                    }
                }
            });
            
            TableAcademicBodyMember tblAcademicBodyMemberNew = new TableAcademicBodyMember();
            tblAcademicBodyMemberNew.setEmail(professor.getEMail());
            tblAcademicBodyMemberNew.setName(professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName());
            tblAcademicBodyMemberNew.setCheckBoxMember(checkBoxMember);
            tblAcademicBodyMemberNew.setCheckBoxResponsible(checkBoxResponsible);
            
            
            oblProfessorsList.add(tblAcademicBodyMemberNew);
        }
        
        tblAcademicBodyMember.setItems(oblProfessorsList);
        tblCProfessorEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tblCProfessorName.setCellValueFactory(new PropertyValueFactory("name"));
        tblCProfessorSelect.setCellValueFactory(new PropertyValueFactory("checkBoxMember"));
        tblCProfessorResponsible.setCellValueFactory(new PropertyValueFactory("checkBoxResponsible") );
    }
    
    public void addAcademicBody(){
        AcademicBody academicBody = new AcademicBody();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        academicBody.setKey(txtKey.getText());
        academicBody.setName(txtName.getText());

        List<AcademicBodyMember> academicBodyMemberList = getAcademicBodyMembers();
        academicBody.setMember(academicBodyMemberList);

        int existence = VALUE_BY_DEFAULT;

        try {
            existence = academicBodyDao.getExistenceAcademicBody(academicBody);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(existence == VALUE_BY_DEFAULT){
            try {
                int result = academicBodyDao.addAcademicBodyTransaction(academicBody);
                if(result > 0){
                    DialogGenerator.getDialog(new AlertMessage ("Cuerpo Académico registrado exitosamente",Status.SUCCESS));
                }else{
                    DialogGenerator.getDialog(new AlertMessage ("No se pudo registrar el Cuerpo Académico",Status.ERROR));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
            }
        }else{
            DialogGenerator.getDialog(new AlertMessage ("Clave repetida, no se puede registrar el Cuerpo Académico",Status.ERROR));
        }
    }
    
    public List<AcademicBodyMember> getAcademicBodyMembers(){
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();
        
        for(int i = 0; i < tblAcademicBodyMember.getItems().size(); i++){
            if(tblAcademicBodyMember.getItems().get(i).getCheckBoxResponsible().isSelected()){
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();
                try {
                    academicBodyMember = academicBodyDAO.getProfessor(tblAcademicBodyMember.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
                }
                academicBodyMember.setRole("Responsable");
                academicBodyMemberList.add(academicBodyMember);
            }
            if(tblAcademicBodyMember.getItems().get(i).getCheckBoxMember().isSelected()){
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();
                try {
                    academicBodyMember = academicBodyDAO.getProfessor(tblAcademicBodyMember.getItems().get(i).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                    DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
                }
                academicBodyMember.setRole("Miembro");
                academicBodyMemberList.add(academicBodyMember);
            }
        }
        return academicBodyMemberList;
    }

    private boolean isEmptyField() {
        boolean isEmpty = false;
        
        if(FieldValidation.isNullOrEmptyTxtField(txtKey)){
            lblInvalidKey.setVisible(true);
            isEmpty = true;
        }
        if(FieldValidation.isNullOrEmptyTxtField(txtName)){
            lblInvalidName.setVisible(true);
            isEmpty = true;
        }
        
        return isEmpty;
    }
    
    public boolean cancel(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas cancelar el registro?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
}
