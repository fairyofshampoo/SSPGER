package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.ReceptionalWorkStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class MyReceptionalWorksController implements Initializable {

    private int professorId = 0;

    @FXML
    private GridPane gpReceptionalWorkTable;

    @FXML
    private Pane pnNavigationBar;

    @FXML
    private ComboBox<String> cbxFilter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
    }

    private void setGraphicElements() {
        cbxFilter.setItems(FXCollections.observableArrayList(getPosibleStates()));
        setProfessorId();
        setReceptionalWorks();
        setNavigationBar();
    }

    private List<String> getPosibleStates() {
        List<String> allStates = new ArrayList<>();

        for (ReceptionalWorkStatus receptionalWorkStatus : ReceptionalWorkStatus.values()) {
            allStates.add(receptionalWorkStatus.getDisplayName());
        }

        allStates.add("TODOS");

        return allStates;
    }

    private void setProfessorId() {
        professorId = UserSession.getInstance().getUserId();
    }

    private void setReceptionalWorks() {
        List<ReceptionalWork> professorReceptionalWorks = getProfessorReceptionalWorks();
        int column = 0;
        int row = 1;

        try {
            for (int card = 0; card < professorReceptionalWorks.size(); card++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/ReceptionalWorkCard.fxml"));
                AnchorPane receptionalWorkCard = fxmlLoader.load();
                ReceptionalWorkCardController receptionalWorkCardController = fxmlLoader.getController();
                receptionalWorkCardController.setReceptionalWorkCardController(professorReceptionalWorks.get(card).getIdReceptionalWork());

                if (receptionalWorkCardController.isValid()) {
                    if (column == 2) {
                        column = 0;
                        row++;
                    }

                    gpReceptionalWorkTable.add(receptionalWorkCard, column++, row);
                    GridPane.setMargin(receptionalWorkCard, new Insets(10));
                }
            }
        } catch (IOException ioException) {
            Logger.getLogger(MyReceptionalWorksController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private List<ReceptionalWork> getProfessorReceptionalWorks() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        List<ReceptionalWork> receptionalWorks = new ArrayList<>();

        try {
            if ("TODOS".equals(cbxFilter.getValue()) || cbxFilter.getValue() == null) {
                receptionalWorks = receptionalWorkDao.getReceptionalWorksByProfessor(professorId);
            } else {
                receptionalWorks = receptionalWorkDao.getReceptionalWorksByProfessorAndState(professorId, cbxFilter.getValue());
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(MyReceptionalWorksController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }

        return receptionalWorks;
    }

    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();

            pnNavigationBar.getChildren().add(pnNavigationBarParent);
        } catch (IOException ex) {
            Logger.getLogger(MyReceptionalWorksController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage(
                    "Archivo FXML corrupto",
                    Status.FATAL));
        }
    }

    @FXML
    private void filterCourses(ActionEvent event) {
        gpReceptionalWorkTable.getChildren().clear();
        setReceptionalWorks();
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
