package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class AcademicBodyModifierController implements Initializable {
    
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<?> tblAcademicBodyMembers;

    @FXML
    private TableColumn<?, ?> tblCEmail;

    @FXML
    private TableColumn<?, ?> tblCMembers;

    @FXML
    private TableColumn tblCName;

    @FXML
    private TableColumn tblCResponsible;

    @FXML
    private TextField txtKey;

    @FXML
    private TextField txtName;
    
    private static String academicBodyKey;
    
    public static void setAcademicBodyKey(String key){
        AcademicBodyModifierController.academicBodyKey = key;
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void saveData(ActionEvent event) {

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
