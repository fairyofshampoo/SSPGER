package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.UserSession;

public class HomeStudentController implements Initializable {

    @FXML
    private HBox coursesCardLayout;

    @FXML
    private Label lblSeeMyWork;

    @FXML
    private Pane pnNavigationBarStudent;

    @FXML
    private HBox myReceptionalWordLayout;

    private List<Course> coursesStudentList;
    private final int RECEPTIONAL_WORK_ERROR_ID = 0;
    private ReceptionalWork receptionalWork = new ReceptionalWork();

    @FXML
    void displayMyWorkView(MouseEvent event) {
        SPGER.setRoot("MyReceptionalWork.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNavigationBar();
        displayCourses();
        setReceptionalWorkData();
    }

    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();

            pnNavigationBarStudent.getChildren().add(pnNavigationBarParent);
        } catch (IOException ex) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    public void displayCourses() {
        coursesStudentList = new ArrayList<>(getCoursesList());
        try {
            for (int i = 0; i < coursesStudentList.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCardForStudent.fxml"));
                AnchorPane apCourseCard = loader.load();
                CourseCardForStudentController courseCardController = loader.getController();
                courseCardController.setCourseData(coursesStudentList.get(i));
                coursesCardLayout.getChildren().add(apCourseCard);
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private List<Course> getCoursesList() {
        CourseDAO courseDao = new CourseDAO();
        List<Course> courseList = new ArrayList<>();

        try {
            courseList = courseDao.getCoursebyStudent(UserSession.getInstance().getUserId());

        } catch (SQLException sqlException) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return courseList;
    }

    public void setReceptionalWorkData() {
        receptionalWork = getReceptionalWork();
        try {
            if (receptionalWork.getIdReceptionalWork() != RECEPTIONAL_WORK_ERROR_ID) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/ReceptionalWorkStudentCard.fxml"));
                AnchorPane apReceptionalWork = loader.load();
                ReceptionalWorkStudentCardController workCardController = loader.getController();
                workCardController.setReceptionalWorkData(receptionalWork);
                myReceptionalWordLayout.getChildren().add(apReceptionalWork);
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private ReceptionalWork getReceptionalWork() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        ReceptionalWork receptionalWorkRetrieved = new ReceptionalWork();

        try {
            receptionalWorkRetrieved = receptionalWorkDao.getActiveReceptionalWorkByStudent(UserSession.getInstance().getUserId());
        } catch (SQLException sqlException) {
            Logger.getLogger(HomeStudentController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return receptionalWorkRetrieved;
    }
}
