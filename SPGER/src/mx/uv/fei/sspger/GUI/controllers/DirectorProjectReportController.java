package mx.uv.fei.sspger.GUI.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class DirectorProjectReportController implements Initializable {
    @FXML
    private Button btnDownload;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblCodirectorName;

    @FXML
    private Label lblDirectorName;

    @FXML
    private Label lblGeneratedReport;

    @FXML
    private Label lblProjectName;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
