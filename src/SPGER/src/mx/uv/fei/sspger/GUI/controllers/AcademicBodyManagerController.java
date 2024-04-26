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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.Status;

public class AcademicBodyManagerController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private GridPane gpCardLayout;

    @FXML
    private Pane pnNavigationBar;

    @FXML
    void addClicked(ActionEvent event) {
        goToAcademicBodyRegister();
    }

    private final int VALUE_BY_DEFAULT = 0;
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSENT_SIZE_CARD = 10;

    public void showAcademicBodyCards() {
        List<AcademicBody> academicBodyList = getAllAcademicBody();
        int column = VALUE_BY_DEFAULT;
        int row = VALUE_BY_DEFAULT;

        if (academicBodyList.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay Cuerpos Académicos registrados", Status.WARNING));
        } else {
            try {
                for (int card = VALUE_BY_DEFAULT; card < academicBodyList.size(); card++) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AcademicBodyCard.fxml"));
                    AnchorPane apAcademicBody = loader.load();
                    AcademicBodyCardController cardController = loader.getController();
                    cardController.setAcademicBody(academicBodyList.get(card));

                    if (column == UPPER_LIMIT_COLUMN) {
                        column = VALUE_BY_DEFAULT;
                        row++;
                    }

                    gpCardLayout.add(apAcademicBody, column++, row);
                    GridPane.setMargin(apAcademicBody, new Insets(INSENT_SIZE_CARD));
                }
            } catch (IOException ex) {
                Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFXMLFileFailedAlert();
            }
        }
    }

    public List<AcademicBody> getAllAcademicBody() {
        List<AcademicBody> academicBodyList = new ArrayList<>();
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();

        try {
            academicBodyList = academicBodyDao.getAllAcademicBody();
        } catch (SQLException sqlException) {
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return academicBodyList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showAcademicBodyCards();
        setNavigationBar();
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
            Logger.getLogger(AcademicBodyManagerController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private void goToAcademicBodyRegister() {
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/AcademicBodyRegister.fxml");
    }

}
