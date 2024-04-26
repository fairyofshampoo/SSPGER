package mx.uv.fei.sspger.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;

public class StudentReportCardController {

    private final int RECEPTIONAL_WORK_ERROR_ID = 0;

    @FXML
    private GridPane gpLabels;

    @FXML
    private ImageView imgStudent;

    @FXML
    private Text ntxtReceptionalWork;

    @FXML
    private Text ntxtStudentName;

    @FXML
    private Label lblAssignmentCount;

    @FXML
    private Label lblSubmissionAssignmentCount;

    public void setStudentReportCard(int idStudent) {
        StudentDAO studentDao = new StudentDAO();
        ReceptionalWorkDAO receptionalWorkDao = new ReceptionalWorkDAO();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        SubmissionManagerDAO submissionManagerDao = new SubmissionManagerDAO();

        Student student = new Student();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        int assignmentCount = 0;
        int submissionCount = 0;

        try {
            student = studentDao.getStudent(idStudent);
            receptionalWork = receptionalWorkDao.getActiveReceptionalWorkByStudent(idStudent);
            assignmentCount = assignmentDao.getCountAssignmentPerReceptionalWork(receptionalWork.getIdReceptionalWork());
            submissionCount = submissionManagerDao.getCountSubmissionsPerReceptionalWork(receptionalWork.getIdReceptionalWork());

            setGraphicElements(student, assignmentCount, submissionCount, receptionalWork);

        } catch (SQLException sqlException) {
            Logger.getLogger(StudentReportCardController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

    }

    private void setGraphicElements(Student student, int assignmentCount, int submissionCount, ReceptionalWork receptionalWork) {
        gpLabels.setVgap(10);

        lblSubmissionAssignmentCount.setText("Avances entregados: " + submissionCount);
        lblAssignmentCount.setText("Total de avances: " + assignmentCount);
        ntxtStudentName.setText("Nombre: " + student.getName() + " " + student.getLastName());

        if (receptionalWork.getIdReceptionalWork() == RECEPTIONAL_WORK_ERROR_ID) {
            ntxtReceptionalWork.setText("Sin trabajo recepcional");
        } else {
            ntxtReceptionalWork.setText("Trabajo recepcional: " + receptionalWork.getName());
        }

        imgStudent.setImage(ImagesSetter.getUserIconImage());
    }
}
