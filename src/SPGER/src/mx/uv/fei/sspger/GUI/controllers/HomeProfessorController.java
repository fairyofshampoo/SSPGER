package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.DAO.CourseDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.UserSession;

public class HomeProfessorController implements Initializable {

    @FXML
    private HBox coursesCardLayout;

    @FXML
    private HBox projectsCardLayout;

    @FXML
    private Pane pnNavigationBar;

    final int CARD_SPACES = 3;

    private List<Course> coursesRecentlyAdded;
    private List<Project> projectsRecentlyAdded;
    private final String DIRECTOR_ROLE = "Director";

    @FXML
    void allCoursesClicked(MouseEvent event) {
        SPGER.setRoot("ProfessorCourseManager.fxml");
    }

    @FXML
    void allProjectsClicked(MouseEvent event) {
        SPGER.setRoot("MyProjects.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCourses();
        displayProjects();
        setNavigationBar();
    }

    public void displayCourses() {
        coursesRecentlyAdded = new ArrayList<>(coursesRecentlyAdded());
        try {
            for (int i = 0; i < coursesRecentlyAdded.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/CourseCard.fxml"));
                AnchorPane apCourseCard = loader.load();
                CourseCardController courseCardController = loader.getController();
                courseCardController.setCourseData(coursesRecentlyAdded.get(i));
                coursesCardLayout.getChildren().add(apCourseCard);
            }
        } catch (IOException ioException) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private List<Course> coursesRecentlyAdded() {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = new ArrayList<>();

        try {
            courseList = courseDAO.getCoursesPerProfessor(UserSession.getInstance().getUserId());

            if (courseList.size() > CARD_SPACES) {
                courseList = reduceCourseList(courseList);
            }

        } catch (SQLException sqlException) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return courseList;
    }

    private List<Course> reduceCourseList(List<Course> courseList) {
        List<Course> newCourseList = new ArrayList<>();

        for (int i = 0; i < CARD_SPACES; i++) {
            newCourseList.add(courseList.get(i));
        }

        return newCourseList;
    }

    public void displayProjects() {
        projectsRecentlyAdded = new ArrayList<>(projectsRecentlyAdded());
        try {
            for (int i = 0; i < projectsRecentlyAdded.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/DirectorProjectCard.fxml"));
                AnchorPane apDirectorProjectsCard = loader.load();
                DirectorProjectCardController cardController = loader.getController();
                cardController.setDirectorProjectData(projectsRecentlyAdded.get(i));
                projectsCardLayout.getChildren().add(apDirectorProjectsCard);
            }
        } catch (IOException ioException) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    private List<Project> projectsRecentlyAdded() {
        ProjectDAO projectDao = new ProjectDAO();
        List<Project> projectList = new ArrayList<>();

        try {
            projectList = projectDao.getProjectsPerDirectorCard(UserSession.getInstance().getUserId(), DIRECTOR_ROLE);

            if (projectList.size() > CARD_SPACES) {
                projectList = reduceProjectList(projectList);
            }

        } catch (SQLException sqlException) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return projectList;
    }

    private List<Project> reduceProjectList(List<Project> projectsList) {
        List<Project> newProjectList = new ArrayList<>();

        for (int i = 0; i < CARD_SPACES; i++) {
            newProjectList.add(projectsList.get(i));
        }

        return newProjectList;
    }

    private void setNavigationBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/NavigationBar.fxml"));
            Pane pnNavigationBarParent = fxmlLoader.load();
            NavigationBarController navigationBarController = fxmlLoader.getController();
            navigationBarController.setNavigationBar();

            pnNavigationBar.getChildren().add(pnNavigationBarParent);
        } catch (IOException ioException) {
            Logger.getLogger(HomeProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }
}
