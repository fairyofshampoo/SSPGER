package mx.uv.fei.sspger.GUI.controllers;


import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;


public class ReceptionalWorkCardController {
    private int idReceptionalWork;
    
    @FXML
    private Pane pnReceptionalWorkCard;
    
    @FXML
    private Label lblState;

    @FXML
    private Label lblModality;

    @FXML
    private Label lblReceptionalWorkName;

    @FXML
    private Label lblStudentNumber;
    
     @FXML
    void mouseEnteredReceptionalWorkArea(MouseEvent event) {
        pnReceptionalWorkCard.setCursor(Cursor.HAND);
    }

    @FXML
    void mouseExitedReceptionalWorkArea(MouseEvent event) {
        pnReceptionalWorkCard.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void openReceptionalWorkWindow(MouseEvent event) {
        ViewReceptionalWorkController.idReceptionalWork = idReceptionalWork;
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ViewReceptionalWork");
    }
    
    public void setReceptionalWorkCardController (int idReceptionalWork) throws SQLException{
        this.idReceptionalWork = idReceptionalWork;
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWork = receptionalWorkDao.getReceptionalWorkWithNumberOfStudents(idReceptionalWork);
        
        setGraphicElemens(receptionalWork);
    }

    private void setGraphicElemens(ReceptionalWork receptionalWork) {
        lblState.setText("Estado: " + receptionalWork.getState());
        lblModality.setText("Modalidad: " + receptionalWork.getModality());
        lblReceptionalWorkName.setText(receptionalWork.getName());
        lblStudentNumber.setText("Número de estudiantes: " + receptionalWork.getSpace());
    }
}
