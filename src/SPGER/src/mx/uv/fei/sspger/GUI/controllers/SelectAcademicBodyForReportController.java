package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Status;

public class SelectAcademicBodyForReportController implements Initializable {

    @FXML
    private ChoiceBox<AcademicBody> cbxAcademicBody;
    @FXML
    private Button btnGoBack;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAcademicBodyComboBox();
    }

    private void setAcademicBodyComboBox() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBody> academicBodies = new ArrayList<>();
        ObservableList<AcademicBody> academicBodiesObservableList = FXCollections.observableArrayList();

        try {
            academicBodies = academicBodyDao.getAcademicBodiesActive();
        } catch (SQLException sqlException) {
            Logger.getLogger(SelectAcademicBodyForReportController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
            showAcademicBodyProjectsView();
        }

        if (academicBodies.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay cuerpos académicos registrados", Status.WARNING));
            showAcademicBodyProjectsView();
        } else {
            academicBodiesObservableList.addAll(academicBodies);
            cbxAcademicBody.setItems(academicBodiesObservableList);
        }
    }

    @FXML
    private void createReportClicked(ActionEvent event) {
        if (isAcademicBodySelected()) {
            showReportView();
        } else {
            DialogGenerator.getDialog(new AlertMessage("No ha seleccionado un cuerpo académico", Status.WARNING));
        }
    }

    private boolean isAcademicBodySelected() {
        return (cbxAcademicBody.getValue() != null);
    }

    private void showReportView() {
        GeneralProjectReportController.setAcademicBody(cbxAcademicBody.getValue());
        SPGER.setRoot("GeneralProjectReport.fxml");
    }

    @FXML
    private void goBackButtonClicked(ActionEvent event) {
        showAcademicBodyProjectsView();
    }

    private void showAcademicBodyProjectsView() {
        SPGER.setRoot("AcademicBodyProjects.fxml");
    }

}
