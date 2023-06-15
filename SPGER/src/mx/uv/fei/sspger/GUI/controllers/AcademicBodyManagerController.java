package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.GUI.controllers.AlertMessage;
import mx.uv.fei.sspger.GUI.controllers.DialogGenerator;
import mx.uv.fei.sspger.GUI.controllers.ImagesSetter;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Status;


public class AcademicBodyManagerController implements Initializable {
    @FXML
    private Button btnAdd;
    
    @FXML
    private GridPane gpCardLayout;

    @FXML
    private Label lblAcademicBody;

    @FXML
    private Label lblTitleSystem;
    
    @FXML
    private ImageView imgAddAcademicBody;

    @FXML
    private ImageView imgAddCourse;

    @FXML
    private ImageView imgAddUser;

    @FXML
    private ImageView imgHome;
    
    @FXML
    void addButtonEvent(ActionEvent event) {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegister.fxml");
    }
    
    @FXML
    void onMouseEnteredAction(MouseEvent event) {
        
    }
    
    public void showAcademicBodyCards(){
        List<AcademicBody> academicBodyList = new ArrayList<>();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        int column = 0;
        int row = 0;
        
        try {
            academicBodyList = academicBodyDao.getAllAcademicBody();
        } catch (SQLException ex) {
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            for(int card = 0; card < academicBodyList.size(); card++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AcademicBodyCard.fxml"));
                AnchorPane apAcademicBody = loader.load();
                AcademicBodyCardController cardController = loader.getController();
                cardController.setAcademicBody(academicBodyList.get(card));
                
                if(column == 2){
                    column = 0;
                    row++;
                }
                gpCardLayout.add(apAcademicBody, column++, row);
                GridPane.setMargin(apAcademicBody, new Insets(10));
            }
        } catch (IOException ex) {
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage ("Error en la conexión al sistema.",Status.FATAL));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        showAcademicBodyCards();
    }
    
    public void displayImages(){
        imgHome.setImage(ImagesSetter.getHomeImage());
        imgAddAcademicBody.setImage(ImagesSetter.getAcademicBodyImage());
        imgAddCourse.setImage(ImagesSetter.getCoursesImage());
        imgAddUser.setImage(ImagesSetter.getUsersImage()); 
    }
    
}
