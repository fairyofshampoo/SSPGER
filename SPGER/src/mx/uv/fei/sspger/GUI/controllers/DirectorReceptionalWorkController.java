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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;


public class DirectorReceptionalWorkController implements Initializable {
    public static int idProfessor;
    private int column = 0;
    private int row = 1;
    
    @FXML
    private ComboBox<String> cbxReceptionalWorkFilter;

    @FXML
    private GridPane gpReceptionalWorks;

    @FXML
    private ImageView imgAcademy;

    @FXML
    private ImageView imgCourse;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgProject;

    @FXML
    void filterReceptionalWorks(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setReceptionalWorks();
        setGraphicElements();
    }    

    private void setReceptionalWorks() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        List<ReceptionalWork> receptionalWorksByProfessor = new ArrayList<>();
        
        try{
            receptionalWorksByProfessor = receptionalWorkDao.getReceptionalWorksByProfessor(idProfessor);
            
            for(ReceptionalWork receptionalWork : receptionalWorksByProfessor){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/ReceptionalWorkCard.fxml"));                
                Pane receptionalWorkCard = fxmlLoader.load();
                ReceptionalWorkCardController receptionalWorkCardController = fxmlLoader.getController();
                receptionalWorkCardController.setReceptionalWorkCardController(receptionalWork.getIdReceptionalWork());
                
                if (column == 2){
                    row++;
                    column = 0;
                }
                
                gpReceptionalWorks.add(receptionalWorkCard, column++, row);
                GridPane.setMargin(receptionalWorkCard, new Insets(5));
            }
            
        }catch (SQLException | IOException exception){
            Logger.getLogger(DirectorReceptionalWorkController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private void setGraphicElements() {
        
    }
    
}
