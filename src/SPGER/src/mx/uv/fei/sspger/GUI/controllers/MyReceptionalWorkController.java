package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.UserSession;

public class MyReceptionalWorkController implements Initializable {

    @FXML
    private Pane pnNavigationBarStudent;
    @FXML
    private Label lblReceptionalWorkTitle;

    @FXML
    private Label lblDirector;
    @FXML
    private Label lblCodirector;
    @FXML
    private GridPane gpAssignments;
    @FXML
    private Label lblDirectorTitle;
    @FXML
    private Label lblCodirectorTitle;

    private final int COLUMN_START = 0;
    private final int ROW_START = 1;
    private final int ERROR = -1;
    private int column = COLUMN_START;
    private int row = ROW_START;
    private final int NOT_FOUND = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNavigationBar();
        column = COLUMN_START;
        row = ROW_START;
        setReceptionalWorkData();
    }

    private void setReceptionalWorkData() {
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        try {
            ReceptionalWork receptionalWork = receptionalWorkDao.getActiveReceptionalWorkByStudent(UserSession.getInstance().getUserId());
            if (receptionalWork.getIdReceptionalWork() != NOT_FOUND) {
                lblReceptionalWorkTitle.setText(receptionalWork.getName());
                lblDirector.setText(getDirectorName(receptionalWork.getIdProject()));
                lblCodirector.setText(getCodirectorsName(receptionalWork.getIdProject()));

                setAssignments(receptionalWork.getIdReceptionalWork());
            } else {
                showReceptionalWorkNotFound();
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private void setAssignments(int idReceptionalWork) {
        try {
            AssignmentDAO assignmentDao = new AssignmentDAO();
            List<Assignment> studentAssignments = assignmentDao.getAssignmentsPerReceptionalWork(idReceptionalWork);

            try {
                for (Assignment assignment : studentAssignments) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AssignmentStudentCard.fxml"));
                    HBox assignmentCard = fxmlLoader.load();
                    AssignmentStudentCardController assignmentCardController = fxmlLoader.getController();
                    assignmentCardController.setAssignmentData(assignment);

                    if (column == 2) {
                        column = 0;
                        row++;
                    }

                    gpAssignments.add(assignmentCard, column++, row);
                    GridPane.setMargin(assignmentCard, new Insets(10));
                }

            } catch (IOException ioException) {
                Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, ioException);
                FailAlert.showFXMLFileFailedAlert();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private void showReceptionalWorkNotFound() {
        lblReceptionalWorkTitle.setText("No tienes un trabajo recepcional asignado");
        lblDirector.setVisible(false);
        lblCodirector.setVisible(false);
        lblDirectorTitle.setVisible(false);
        lblCodirectorTitle.setVisible(false);

    }

    private String getDirectorName(int idProject) {

        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = new Professor();
        String directorName = "";
        try {
            professor = professorDao.getDirectorByProject(idProject);
            if (professor.getId() != ERROR) {
                directorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
        return directorName;
    }

    private String getCodirectorsName(int idProject) {
        ProfessorDAO professorDao = new ProfessorDAO();
        String codirectorsName = "";
        List<Professor> codirectorsList;
        try {
            codirectorsList = professorDao.getCoodirectorByProject(idProject);

            for (Professor professor : codirectorsList) {
                codirectorsName = codirectorsName + professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName() + " ";
            }

        } catch (SQLException sqlException) {
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return codirectorsName;
    }

    private void setProjectData(int idProject) {
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor director = new Professor();
        try {
            director = professorDao.getDirectorByProject(idProject);
            if (director.getId() != NOT_FOUND) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }
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
            Logger.getLogger(MyReceptionalWorkController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

}
