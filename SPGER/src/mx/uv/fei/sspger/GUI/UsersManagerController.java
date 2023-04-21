package mx.uv.fei.sspger.GUI;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class UsersManagerController implements Initializable {

    @FXML
    private Button btnAddUser;

    @FXML
    private Label lblTitleSystem;

    @FXML
    private Label lblUsers;

    @FXML
    private TextField txtSearchBar;
    
    @FXML
    void addUserButtonClicked(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserRegister.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Stage myStage = (Stage) this.btnAddUser.getScene().getWindow();
            myStage.close();
            
        } catch (IOException ex) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

