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
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.UserSession;

public class ProfessorCourseManagerController implements Initializable {

    private int professorId = 0;
    private int column = 0;
    private int row = 0;

    @FXML
    private ComboBox<String> cbxCourseFilter;

    @FXML
    private Pane pnNavigationBar;

    @FXML
    private GridPane gpCourseTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setProfessorId();
        setGraphicElements();
        setNavigationBar();
        setCoursesCards();
    }

    private void setProfessorId() {
        professorId = UserSession.getInstance().getUserId();
    }

    private void setGraphicElements() {
        cbxCourseFilter.setItems(FXCollections.observableArrayList(getPosibleStates()));
    }

    private List<String> getPosibleStates() {
        List<String> allStates = new ArrayList<>();

        for (CourseStates courseState : CourseStates.values()) {
            allStates.add(courseState.getCourseState());
        }

        return allStates;
    }

    private List<Course> getProfessorCourses() {
        CourseDAO courseDao = new CourseDAO();
        List<Course> courses = new ArrayList<>();

        try {
            courses = courseDao.getCoursesPerProfessor(professorId);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return courses;
    }

    private List<Course> getProfessorCoursesPerState() {
        CourseDAO courseDao = new CourseDAO();
        List<Course> courses = new ArrayList<>();

        try {
            courses = courseDao.getCoursesPerStateAndProfessor(cbxCourseFilter.getValue(), professorId);
        } catch (SQLException sqlException) {
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return courses;
    }

    @FXML
    void filterCourses(ActionEvent event) {
        gpCourseTable.getChildren().clear();
        column = 0;
        row = 0;

        List<Course> professorCoursesPerState = getProfessorCoursesPerState();
        try {
            for (Course course : professorCoursesPerState) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCard.fxml"));
                AnchorPane courseCard = fxmlLoader.load();
                CourseCardController courseCardController = fxmlLoader.getController();
                courseCardController.setCourseData(course);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpCourseTable.add(courseCard, column++, row);
                GridPane.setMargin(courseCard, new Insets(10));

            }
        } catch (IOException ioException) {
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
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
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ex);
            DialogGenerator.getDialog(new AlertMessage(
                    "Archivo FXML corrupto",
                    Status.FATAL));
        }
    }

    private void setCoursesCards() {
        List<Course> professorCourses = getProfessorCourses();
        try {
            for (int card = 0; card < professorCourses.size(); card++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCard.fxml"));
                AnchorPane courseCard = fxmlLoader.load();
                CourseCardController courseCardController = fxmlLoader.getController();
                courseCardController.setCourseData(professorCourses.get(card));

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpCourseTable.add(courseCard, column++, row);
                GridPane.setMargin(courseCard, new Insets(10));

            }
        } catch (IOException ioException) {
            Logger.getLogger(ProfessorCourseManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }
}
