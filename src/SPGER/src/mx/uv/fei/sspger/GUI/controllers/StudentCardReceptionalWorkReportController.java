package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import mx.uv.fei.sspger.logic.DAO.SubmissionManagerDAO;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class StudentCardReceptionalWorkReportController {

    @FXML
    private ImageView imgUserIcon;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblSubmissionSended;

    @FXML
    private Label lblTotalAssignments;

    private static int idStudent;
    private final int NOT_FOUND_INT = -1;

    public static void setIdStudent(int idStudent) {
        StudentCardReceptionalWorkReportController.idStudent = idStudent;
    }

    public void setData(Student student, int idReceptionalWork) {
        lblName.setText(student.getName() + " " + student.getLastName());
        lblEmail.setText(student.getEMail());
        setIdStudent(student.getId());

        int submissions = getSubmissionPerReceptionalWork(idReceptionalWork);
        int assignments = getAssignmentsPerReceptionalWork(idReceptionalWork);

        if (submissions != NOT_FOUND_INT) {
            lblSubmissionSended.setText(Integer.toString(submissions));
        }

        if (assignments != NOT_FOUND_INT) {
            lblTotalAssignments.setText(Integer.toString(assignments));
        }
    }

    private int getSubmissionPerReceptionalWork(int idReceptionalWork) {
        SubmissionManagerDAO submissionManagerDao = new SubmissionManagerDAO();
        int result = NOT_FOUND_INT;

        try {
            result = submissionManagerDao.getCountSubmissionsPerReceptionalWork(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(StudentCardReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }

        return result;
    }

    private int getAssignmentsPerReceptionalWork(int idReceptionalWork) {
        AssignmentDAO assignmentDao = new AssignmentDAO();
        int result = NOT_FOUND_INT;

        try {
            result = assignmentDao.getCountAssignmentPerReceptionalWork(idReceptionalWork);
        } catch (SQLException ex) {
            Logger.getLogger(StudentCardReceptionalWorkReportController.class.getName()).log(Level.SEVERE, null, ex);
            showFailedConnectionAlert();
        }

        return result;
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }
}
