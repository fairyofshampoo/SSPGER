/*package mx.uv.fei.sspger.GUI.controllers;


import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
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
    private Label lblAddAcademicBody;
    
    @FXML
    private Label lblAdvice;

    @FXML
    private Label lblInformationInsert;
    
    @FXML
    private Label lblInvalidKey;

    @FXML
    private Label lblInvalidName;

    @FXML
    private Label lblKey;
    
    @FXML
    private Label lblMember;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCProfessorEmail;

    @FXML
    private TableColumn<TableAcademicBodyMember, String> tblCProfessorName;

    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCProfessorSelect;
    
    @FXML
    private TableColumn<TableAcademicBodyMember, CheckBox> tblCProfessorResponsible;
    
    @FXML
    private TableView<TableAcademicBodyMember> tblAcademicBodyMember;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;
    
    @FXML
    void addButtonAceptEvent(ActionEvent event) {
         addAcademicBody();
         addAcademicBodyMembers();
    }
    
    ObservableList<TableAcademicBodyMember> list = FXCollections.observableArrayList();

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
            
            checkBoxResponsible.setOnAction(event -> {
                if (checkBoxResponsible.isSelected()) {
                    for (TableAcademicBodyMember member : list) {
                        if (member.getCheckBoxResponsible() != checkBoxResponsible) {
                            member.getCheckBoxResponsible().setSelected(false);
                        }
                    }
                }
            });
            
            list.add(new TableAcademicBodyMember(professor.getEMail(), professor.getName(), checkBoxMember, checkBoxResponsible));
        }
        
        tblAcademicBodyMember.setItems(list);
        tblCProfessorEmail.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("email"));
        tblCProfessorName.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, String>("name"));
        tblCProfessorSelect.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxMember"));
        tblCProfessorResponsible.setCellValueFactory(new PropertyValueFactory<TableAcademicBodyMember, CheckBox>("checkBoxResponsible") );
    }
    
    public void addAcademicBody(){
        try {
            AcademicBody academicBody = new AcademicBody();
            AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
            
            academicBody.setKey(txtKey.getText());
            academicBody.setName(txtName.getText());
            
            if(!isEmptyField()){
               if(!ExistenceValidation.existenceAcademicBody(academicBody)){
                    academicBodyDAO.addAcademicBody(academicBody); //y si junto los métodos de miembro y responsable aquí??
                    DialogGenerator.getDialog(new AlertMessage ("El Cuerpo Académico fue registrado exitosamente",Status.SUCCESS));
                } 
            } 
            
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
        }
    }
       
    public void addAcademicBodyMembers(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        
        for (int i = 0; i < tblAcademicBodyMember.getItems().size(); i++){
            Professor professor;
            try {
                professor = professorDAO.getProfessor(tblAcademicBodyMember.getItems().get(i).getEmail());
                AcademicBodyMember academicBodyMember = new AcademicBodyMember();
                academicBodyMember.setIdAcademicBody(txtKey.getText());
                academicBodyMember.setIdUserProfessor(professor.getId());
                academicBodyMember.setRole("Miembro");
                
                if(tblAcademicBodyMember.getItems().get(i).getCheckBoxMember().isSelected()){
                    academicBodyDAO.addAcademicBodyMember(academicBodyMember);
                }

                if(tblAcademicBodyMember.getItems().get(i).getCheckBoxResponsible().isSelected()){
                    if(ExistenceValidation.existenceAcademicBodyResponsible(academicBodyMember)){ //verificar el rol. si existe, actualizo el rol, sino lo creo
                            academicBodyMember.setRole("Responsable"); //falta validar que sólo sea uno. Tal vez lo pueda hacer dentro de existence
                    }else{
                            academicBodyDAO.addAcademicBodyMember(academicBodyMember);
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(AcademicBodyRegisterController.class.getName()).log(Level.SEVERE, null, ex);
                DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
            }    
        }
    }

    private boolean isEmptyField() {
        boolean isEmpty = false;
        
        lblInvalidKey.setVisible(false);
        lblInvalidName.setVisible(false);
        
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
    
    
}*/