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
import mx.uv.fei.sspger.GUI.controllers.AcademicBodyModifierController;
import mx.uv.fei.sspger.GUI.controllers.AlertMessage;
import mx.uv.fei.sspger.GUI.controllers.DialogGenerator;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Status;


public class AcademicBodyRegisterSavedController implements Initializable {
    @FXML
    private Button btnDelete;

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
    private VBox vboxAcademicBodyMembersContent;
    
    private static String academicBodyKey;
    private final String RESPONSIBLE_ROLE = "Responsable";
    private final int VALUE_BY_DEFAULT = 0;
    
    @FXML
    void goBackAction(MouseEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyManager.fxml");
    }
    
    @FXML
    void deleteAcademicBody(ActionEvent event) {
        if(deleteClicked()){
            delete();
        }
    }

    @FXML
    void modifyAcademicBody(ActionEvent event) {
        AcademicBodyModifierController.setAcademicBodyKey(academicBodyKey);
        
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyModifier.fxml");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAcademicBodyData();
    }
    
    public static void setAcademicBodyKey(String key){
        AcademicBodyRegisterSavedController.academicBodyKey = key;
    }
    
    private void setAcademicBodyData(){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        
        try {
            AcademicBody academicBody = academicBodyDao.getAcademicBody(academicBodyKey);
            lblAcademicBodyKeyData.setText(academicBodyKey);
            lblAcademicBodyNameData.setText(academicBody.getName());
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setAcademicBodyMembersData();
    }
    
   private void setAcademicBodyMembersData(){
        /*AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBodyMember> memberList = new ArrayList<>();
        
        try {
            memberList = academicBodyDao.getAllAcademicBodyMember(academicBodyKey);
            
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean responsibleExistence = false;
        
        for(int i = VALUE_BY_DEFAULT; i < memberList.size(); i++){
            if(memberList.get(i).getRole().equals(RESPONSIBLE_ROLE)){
                lblAcademicBodyResponsible.setText(memberList.get(i).getHonorificTitle() + " " + memberList.get(i).getName() + " " + memberList.get(i).getLastName());
                responsibleExistence = true;
            }else{
                Label lblMember = new Label();
                lblMember.setText(memberList.get(i).getHonorificTitle() + " " + memberList.get(i).getName() + " " + memberList.get(i).getLastName());
                vboxAcademicBodyMembersContent.getChildren().add(lblMember);
            }
        }
        
        if(!responsibleExistence){
            lblAcademicBodyResponsible.setText("No hay un responsable registrado");
        }
        
        if(memberList.isEmpty() || responsibleExistence){
            Label lblMember = new Label();
            lblMember.setText("No hay miembros registrados");
            vboxAcademicBodyMembersContent.getChildren().add(lblMember);
        }*/
    }
    
    private boolean deleteClicked(){
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("¿Deseas eliminar el Cuerpo Académico?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }
    
    private void delete(){
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        int result = VALUE_BY_DEFAULT;
        
        try {
            result = academicBodyDao.deleteAcademicBody(academicBodyKey);
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyRegisterSavedController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(result > VALUE_BY_DEFAULT){
            DialogGenerator.getDialog(new AlertMessage ("Cuerpo Académico eliminado correctamente",Status.SUCCESS));
            SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyManager.fxml");
        }else{
            DialogGenerator.getDialog(new AlertMessage ("No se pudo eliminar el Cuerpo Académico",Status.ERROR));
        }
    }
}
