/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ProjectModifierController implements Initializable {

    @FXML
    private Label lblTitleSystem;
    
    @FXML
    private TextField txtReceptionalWorkName;
    
    @FXML
    private Label lblAddProject;
    
    @FXML
    private Label lblInstruction;
    
    @FXML
    private TextField txtPladeaProjectTitle;
    
    @FXML
    private Button btnAccept;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private TextField txtInvestigationLine;
    
    @FXML
    private TextArea txtProjectRequeriments;
    
    @FXML
    private TextArea txtProyectDescription;
    
    @FXML
    private TextArea txtReceptionalWorkDescription;
    
    @FXML
    private TextArea txtExpectedResults;
    
    @FXML
    private TextArea txtBibliography;
    
    @FXML
    private ChoiceBox<?> cbxAcademicBody;
    
    @FXML
    private TextField txtPartcipantsNumber;
    
    @FXML
    private TextArea txtNotes;
    
    @FXML
    private ChoiceBox<?> cbxDuration;
    
    @FXML
    private TableView<?> tblLGAC;
    
    @FXML
    private TableColumn<?, ?> tblCIdLGAC;
    
    @FXML
    private TableColumn<?, ?> tblCNameLGAC;
    
    @FXML
    private ChoiceBox<?> cbxModality;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void acceptButtonClick(ActionEvent event) {
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) {
    }
    
}
