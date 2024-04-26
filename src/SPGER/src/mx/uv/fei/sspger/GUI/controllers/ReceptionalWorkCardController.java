package mx.uv.fei.sspger.GUI.controllers;

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
    private ReceptionalWork receptionalWork;
    private final int RECEPTIONAL_WORK_ERROR_ID = 0;

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
        ViewReceptionalWorkController.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("ViewReceptionalWork.fxml");
    }

    public void setReceptionalWorkCardController(int idReceptionalWork) {
        try {
            this.idReceptionalWork = idReceptionalWork;
            ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
            receptionalWork = receptionalWorkDao.getReceptionalWorkWithNumberOfStudents(idReceptionalWork);

            setGraphicElemens();
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkCardController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
    }

    public boolean isValid() {
        return receptionalWork.getIdReceptionalWork() != RECEPTIONAL_WORK_ERROR_ID;
    }

    private void setGraphicElemens() {
        lblState.setText("Estado: " + receptionalWork.getState());
        lblModality.setText("Modalidad: " + receptionalWork.getModality());
        lblReceptionalWorkName.setText(receptionalWork.getName());
        lblStudentNumber.setText("Número de estudiantes: " + receptionalWork.getSpace());
    }
}
