package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.ReceptionalWorkStatus;
import mx.uv.fei.sspger.logic.Student;

public class ViewReceptionalWorkController implements Initializable {

    private static int idReceptionalWork;
    private final String ABANDONED_STATUS = ReceptionalWorkStatus.ABANDONED.getDisplayName();
    private final String CONCLUDED_STATUS = ReceptionalWorkStatus.CONCLUDED.getDisplayName();

    @FXML
    private ImageView imgGoBack;

    @FXML
    private GridPane gpAssignments;

    @FXML
    private Label lblReceptionalWorkName;

    @FXML
    private Text ntxtCoodirectorsName;

    @FXML
    private Text ntxtDirectorName;

    @FXML
    private Text ntxtStudentsName;

    @FXML
    private Button btnAddAssignment;

    @FXML
    private Button btnModifyReceptionalWork;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGraphicElements();

    }

    public static void setIdReceptionalWork(int idReceptionalWork) {
        ViewReceptionalWorkController.idReceptionalWork = idReceptionalWork;
    }

    private void setGraphicElements() {
        String studentsName;
        String directorName;
        String codirectorsName;
        String receptionalWorkName;

        try {
            ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
            ReceptionalWork receptionalWork = receptionalWorkDao.getRecepetionalWorkById(idReceptionalWork);
            studentsName = getStudentsName();
            directorName = getDirectorName(receptionalWork.getIdProject());
            codirectorsName = getCodirectorsName(receptionalWork.getIdProject());
            receptionalWorkName = receptionalWork.getName();

            if (receptionalWork.getState().equals(ABANDONED_STATUS) || receptionalWork.getState().equals(CONCLUDED_STATUS)) {
                btnAddAssignment.setDisable(true);
                btnModifyReceptionalWork.setDisable(true);
            }

            lblReceptionalWorkName.setText(receptionalWorkName);
            ntxtStudentsName.setText("Estudiantes: " + studentsName);
            ntxtCoodirectorsName.setText("Coodirectores: " + codirectorsName);
            ntxtDirectorName.setText("Director: " + directorName);
            setAssignments();

        } catch (SQLException sqlException) {
            Logger.getLogger(ViewReceptionalWorkController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private String getStudentsName() throws SQLException {
        StudentDAO studentDao = new StudentDAO();
        List<Student> studentsInReceptionalWork = studentDao.getStudentPerReceptionalWork(idReceptionalWork);
        String studentsName = "";

        for (Student student : studentsInReceptionalWork) {
            studentsName = studentsName + student.getName() + " " + student.getLastName() + ", ";
        }

        return studentsName;
    }

    private String getDirectorName(int idProject) throws SQLException {
        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = professorDao.getDirectorByProject(idProject);

        String directorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();

        return directorName;
    }

    private String getCodirectorsName(int idProject) throws SQLException {
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> codirectorsList = professorDao.getCoodirectorByProject(idProject);
        String codirectorsName = "";

        for (Professor professor : codirectorsList) {
            codirectorsName = codirectorsName + professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName() + " ";
        }

        return codirectorsName;
    }

    private void setAssignments() throws SQLException {
        int column = 0;
        int row = 1;

        AssignmentDAO assignmentDao = new AssignmentDAO();
        List<Assignment> studentAssignments = assignmentDao.getAssignmentsPerReceptionalWork(idReceptionalWork);

        try {
            for (Assignment assignment : studentAssignments) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/AssignmentCard.fxml"));
                Pane assignmentCard = fxmlLoader.load();
                AssignmentCardController assignmentCardController = fxmlLoader.getController();
                assignmentCardController.setAssignmentCard(assignment, idReceptionalWork);
                assignmentCardController.setDirectorView();

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpAssignments.add(assignmentCard, column++, row);
                GridPane.setMargin(assignmentCard, new Insets(10));
            }

        } catch (IOException ioException) {
            Logger.getLogger(ViewProjectForProfessorController.class.getName()).log(Level.SEVERE, null, ioException);
            FailAlert.showFXMLFileFailedAlert();
        }
    }

    @FXML
    void addAssignment(ActionEvent event) {
        AddAssignmentController.setReceptionalWorkId(idReceptionalWork);
        SPGER.setRoot("AddAssignment.fxml");
    }

    @FXML
    void createReport(ActionEvent event) {
        ReceptionalWorkReportController.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("ReceptionalWorkReport.fxml");
    }

    @FXML
    void modifyReceptionalWork(ActionEvent event) {
        ReceptionalWorkModifierController.setIdReceptionalWork(idReceptionalWork);
        SPGER.setRoot("ReceptionalWorkModifier.fxml");
    }

    @FXML
    void mouseEnteredGoBackArea(MouseEvent event) {
        imgGoBack.setCursor(Cursor.HAND);
    }

    @FXML
    void mouseExitedGoBackArea(MouseEvent event) {
        imgGoBack.setCursor(Cursor.DEFAULT);
    }

    @FXML
    void goBack(MouseEvent event) {
        SPGER.setRoot("MyReceptionalWorks.fxml");
    }

}
