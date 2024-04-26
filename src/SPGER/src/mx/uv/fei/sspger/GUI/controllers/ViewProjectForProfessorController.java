package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;

public class ViewProjectForProfessorController implements Initializable {

    public static Student student;
    private ReceptionalWork receptionalWork;
    private final int RECEPTIONAL_WORK_ERROR_ID = 0;
    private static String PROFESSOR_NAME_ERROR = "Sin profesor asignado.";
    private static String WITHOUT_RECEPTIONAL_WORK = "Sin trabajo recepcional.";

    @FXML
    private GridPane gpAssignmentContainer;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgStudent;

    @FXML
    private Label lblCoodirector;

    @FXML
    private Label lblDirector;

    @FXML
    private Label lblReceptionalWorkName;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblStudentRegistrationTag;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();
        setAssignments();
    }

    private void setGraphicElements() {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor director = new Professor();
        List<Professor> coodirectors = new ArrayList<>();
        String coodirectorsName = "Coodirector(es): ";
        String directorName = "Director: ";
        String receptionalWorkName = WITHOUT_RECEPTIONAL_WORK;

        try {
            receptionalWork = receptionalWorkDAO.getActiveReceptionalWorkByStudent(student.getId());
            director = professorDao.getDirectorByProject(receptionalWork.getIdProject());
            coodirectors = professorDao.getCoodirectorByProject(receptionalWork.getIdProject());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        if (director.getName() != null) {
            directorName = directorName + director.getHonorificTitle() + " " + director.getName() + " " + director.getLastName();
        } else {
            directorName = directorName + PROFESSOR_NAME_ERROR;
        }

        if (coodirectors.size() < 1) {
            coodirectorsName = coodirectorsName + PROFESSOR_NAME_ERROR;
        } else {
            for (Professor coodirector : coodirectors) {
                coodirectorsName = coodirectorsName + coodirector.getHonorificTitle() + " " + coodirector.getName() + " " + coodirector.getLastName() + " ";
            }
        }

        if (receptionalWork.getIdReceptionalWork() != RECEPTIONAL_WORK_ERROR_ID) {
            receptionalWorkName = receptionalWork.getName();
        }

        lblReceptionalWorkName.setText(receptionalWorkName);
        lblDirector.setText(directorName);
        lblCoodirector.setText(coodirectorsName);
        lblStudentName.setText(student.getName() + " " + student.getLastName());
        lblStudentRegistrationTag.setText(student.getRegistrationTag());
        imgBack.setImage(ImagesSetter.getGoBackImage());
        imgStudent.setImage(ImagesSetter.getUserIconImage());

    }

    private void setAssignments() {
        int column = 0;
        int row = 1;
        List<Assignment> studentAssignments = getStudentAssignments();

        try {
            for (Assignment assignment : studentAssignments) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AssignmentCard.fxml"));
                Pane assignmentCard = fxmlLoader.load();
                AssignmentCardController assignmentCardController = fxmlLoader.getController();
                assignmentCardController.setAssignmentCard(assignment, receptionalWork.getIdReceptionalWork());
                assignmentCardController.setProfessorView();

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpAssignmentContainer.add(assignmentCard, column++, row);
                GridPane.setMargin(assignmentCard, new Insets(10));
            }

        } catch (IOException ioException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
        }
    }

    private List<Assignment> getStudentAssignments() {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        List<Assignment> studentAssignments = new ArrayList<>();

        try {
            studentAssignments = assignmentDao.getAssignmentsPerReceptionalWork(receptionalWork.getIdReceptionalWork());
        } catch (SQLException sqlException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, sqlException);
        }

        return studentAssignments;
    }

    @FXML
    private void goBack(MouseEvent event) {
        SPGER.setRoot("ProfessorCourseView.fxml");
    }

    @FXML
    private void mouseEnteredBackImageArea(MouseEvent event) {
        imgBack.setCursor(Cursor.HAND);
    }

    @FXML
    private void mouseExitedBackImageArea(MouseEvent event) {
        imgBack.setCursor(Cursor.DEFAULT);
    }

}
