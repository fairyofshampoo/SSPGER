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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.ReceptionalWorkStatus;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class StudentsPostuledCatalogController implements Initializable {

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnExpell;

    @FXML
    private ImageView imgGoBack;

    @FXML
    private Label lblSpaces;

    @FXML
    private Text ntxtProjectTitle;

    @FXML
    private VBox vboxAcceptedContent;

    @FXML
    private VBox vboxPostuledContent;

    private static int idProject;
    private final int VALUE_BY_DEFAULT = 0;
    private final String NOT_FOUND_STRING = "-1";
    private final int UPPER_LIMIT_COLUMN = 2;
    private final int INSET_SIZE_CARD = 10;
    private List<StudentPostulatedCardController> studentPostuledCardControllerList = new ArrayList<>();
    private List<StudentPostulatedCardController> studentAcceptedCardControllerList = new ArrayList<>();
    private final String RECEPTIONAL_WORK_ACTIVE_STATUS = ReceptionalWorkStatus.ACTIVE.getDisplayName();
    private final String PROJECT_VALIDATED_STATUS = ProjectStatus.VALIDATED.getDisplayName();
    private final String PROJECT_ASSIGNED_STATUS = ProjectStatus.ASSIGNED.getDisplayName();
    private final String POSTULATED_STUDENT_ROLE = "POSTULADO";
    private final String ACCEPTED_STUDENT_ROLE = "ACEPTADO";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showData();
    }

    @FXML
    void acceptClicked(ActionEvent event) {
        if (isValidAccepted()) {
            if (isReceptionalWorkActive(idProject)) {
                acceptStudentsToProject();
                close();
            } else {
                acceptStudentsAddingReceptionalWork();
                close();
            }
        } else {
            DialogGenerator.getDialog(new AlertMessage("Selección no válida, revisa el cupo.", Status.WARNING));
        }
    }

    @FXML
    void expellClicked(ActionEvent event) {
        expellStudentsToProject();
        close();
    }

    @FXML
    void goBack(MouseEvent event) {
        close();
    }

    public static void setIdProject(int idProject) {
        StudentsPostuledCatalogController.idProject = idProject;
    }

    private void showData() {
        Project project = getProject();
        List<Student> studentPostulatedList = new ArrayList<>();
        List<Student> studentAcceptedList = new ArrayList<>();
        StudentDAO studentDAO = new StudentDAO();

        try {
            studentPostulatedList = studentDAO.getStudentsByProjectByStatus(idProject, POSTULATED_STUDENT_ROLE);
            studentAcceptedList = studentDAO.getStudentsByProjectByStatus(idProject, ACCEPTED_STUDENT_ROLE);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        ntxtProjectTitle.setText(project.getName());
        lblSpaces.setText(project.getSpaces() + " participante(s) en total");

        if (studentAcceptedList.isEmpty()) {
            Label lblNotFound = new Label("No hay estudiantes aceptados.");
            lblNotFound.setStyle("-fx-font-size: 14px");
            vboxAcceptedContent.getChildren().add(lblNotFound);
        } else {
            setStudentsAcceptedCards(studentAcceptedList);
        }

        if (studentPostulatedList.isEmpty()) {
            Label lblNotFound = new Label("No hay estudiantes postulados.");
            lblNotFound.setStyle("-fx-font-size: 14px");
            vboxPostuledContent.getChildren().add(lblNotFound);
        } else {
            setStudentsPostulatedCards(studentPostulatedList);
        }
    }

    private void setStudentsPostulatedCards(List<Student> studentList) {
        GridPane gpStudentsContent = new GridPane();
        int column = VALUE_BY_DEFAULT;
        int row = VALUE_BY_DEFAULT;

        for (int i = 0; i < studentList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentPostulatedCard.fxml"));
                AnchorPane apStudentCard = loader.load();
                StudentPostulatedCardController cardController = loader.getController();
                cardController.setStudent(studentList.get(i));

                if (column == UPPER_LIMIT_COLUMN) {
                    column = VALUE_BY_DEFAULT;
                    row++;
                }

                gpStudentsContent.add(apStudentCard, column++, row);
                GridPane.setMargin(apStudentCard, new Insets(INSET_SIZE_CARD));
                studentPostuledCardControllerList.add(cardController);
            } catch (IOException ex) {
                Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFXMLFileFailedAlert();
            }
        }

        vboxPostuledContent.getChildren().add(gpStudentsContent);
    }

    private void setStudentsAcceptedCards(List<Student> studentList) {
        GridPane gpStudentsContent = new GridPane();
        int column = VALUE_BY_DEFAULT;
        int row = VALUE_BY_DEFAULT;

        for (int i = 0; i < studentList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/StudentPostulatedCard.fxml"));
                AnchorPane apStudentCard = loader.load();
                StudentPostulatedCardController cardController = loader.getController();
                cardController.setStudent(studentList.get(i));
                cardController.setSelection(true);

                if (column == UPPER_LIMIT_COLUMN) {
                    column = VALUE_BY_DEFAULT;
                    row++;
                }

                gpStudentsContent.add(apStudentCard, column++, row);
                GridPane.setMargin(apStudentCard, new Insets(INSET_SIZE_CARD));
                studentAcceptedCardControllerList.add(cardController);
            } catch (IOException ex) {
                Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFXMLFileFailedAlert();
            }
        }

        vboxAcceptedContent.getChildren().add(gpStudentsContent);
    }

    private Project getProject() {
        ProjectDAO projectDAO = new ProjectDAO();
        Project project = new Project();

        try {
            project = projectDAO.getProject(idProject);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return project;
    }

    private List<Student> getStudentSelected() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < studentPostuledCardControllerList.size(); i++) {
            if (studentPostuledCardControllerList.get(i).isSelected()) {
                Student student = new Student();
                student.setId(studentPostuledCardControllerList.get(i).getIdStudent());
                studentList.add(student);
            }
        }

        return studentList;
    }

    private List<Student> getStudentDeselected() {
        List<Student> studentList = new ArrayList<>();

        for (int i = 0; i < studentAcceptedCardControllerList.size(); i++) {
            if (!studentAcceptedCardControllerList.get(i).isSelected()) {
                Student student = new Student();
                student.setId(studentAcceptedCardControllerList.get(i).getIdStudent());
                studentList.add(student);
            }
        }

        return studentList;
    }

    private int getSpacesAvailable() {
        int spacesAvailable = VALUE_BY_DEFAULT;
        ProjectDAO projectDAO = new ProjectDAO();

        try {
            spacesAvailable = projectDAO.getAvailableSpacesByProject(idProject);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return spacesAvailable;
    }

    private boolean isValidAccepted() {
        List<Student> studentsSelectedList = getStudentSelected();
        int spacesAvailable = getSpacesAvailable();

        return studentsSelectedList.size() <= spacesAvailable;
    }

    private void acceptStudentsAddingReceptionalWork() {
        int result = VALUE_BY_DEFAULT;
        List<Student> studentList = getStudentSelected();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        Project project = getProject();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        receptionalWork.setName(project.getName());
        receptionalWork.setDescription(project.getReceptionalWorkDescription());
        receptionalWork.setModality(project.getModality());
        receptionalWork.setState(RECEPTIONAL_WORK_ACTIVE_STATUS);
        receptionalWork.setResults(project.getExpectedResults());
        receptionalWork.setIdProject(idProject);

        try {
            result = receptionalWorkDAO.addReceptionalWorkTransaction(studentList, receptionalWork, idProject);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (result > VALUE_BY_DEFAULT) {
            if (project.getStatus().equals(PROJECT_VALIDATED_STATUS)) {
                int resultChange = VALUE_BY_DEFAULT;
                resultChange = updateProjectStatus(PROJECT_ASSIGNED_STATUS);

                if (resultChange > VALUE_BY_DEFAULT) {
                    DialogGenerator.getDialog(new AlertMessage("Estudiante(s) aceptado(s) correctamente", Status.SUCCESS));
                } else {
                    DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de aceptar. Intente más tarde.", Status.ERROR));
                }
            } else {
                DialogGenerator.getDialog(new AlertMessage("Estudiante(s) aceptado(s) correctamente", Status.SUCCESS));
            }
        } else {
            DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de aceptar. Intente más tarde.", Status.ERROR));
        }
    }

    public void acceptStudentsToProject() {
        ReceptionalWork receptionalWork = getActiveReceptionalWork();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        List<Student> studentList = getStudentSelected();
        int result = VALUE_BY_DEFAULT;
        String status = getProjectStatus();

        if (!studentList.isEmpty()) {
            try {
                result = receptionalWorkDAO.addStudentsToProjectTransaction(studentList, receptionalWork.getIdReceptionalWork(), idProject);
            } catch (SQLException ex) {
                Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFailedConnectionAlert();
            }

            if (result > VALUE_BY_DEFAULT) {
                if (status.equals(PROJECT_VALIDATED_STATUS)) {
                    int resultChange = VALUE_BY_DEFAULT;
                    resultChange = updateProjectStatus(PROJECT_ASSIGNED_STATUS);

                    if (resultChange > VALUE_BY_DEFAULT) {
                        DialogGenerator.getDialog(new AlertMessage("Estudiante(s) aceptado(s) correctamente", Status.SUCCESS));
                    } else {
                        DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de aceptar. Intente más tarde.", Status.ERROR));
                    }
                } else if (status.equals(PROJECT_ASSIGNED_STATUS)) {
                    DialogGenerator.getDialog(new AlertMessage("Estudiante(s) aceptado(s) correctamente", Status.SUCCESS));
                } else {
                    DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de aceptar. Intente más tarde.", Status.ERROR));
                }
            } else {
                DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de aceptar. Intente más tarde.", Status.ERROR));
            }
        }
    }

    public void expellStudentsToProject() {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        List<Student> studentList = getStudentDeselected();
        ReceptionalWork receptionalWork = getActiveReceptionalWork();
        Project project = getProject();
        int result = VALUE_BY_DEFAULT;

        if (!studentList.isEmpty()) {
            try {
                result = receptionalWorkDAO.expellStudent(idProject, studentList, receptionalWork.getIdReceptionalWork());

            } catch (SQLException ex) {
                Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
                FailAlert.showFailedConnectionAlert();
            }

            if (result > VALUE_BY_DEFAULT) {
                if (project.getStatus().equals(PROJECT_ASSIGNED_STATUS) && getSpacesAvailable() == project.getSpaces()) {
                    int resultChange = VALUE_BY_DEFAULT;
                    resultChange = updateProjectStatus(PROJECT_VALIDATED_STATUS);

                    if (resultChange > VALUE_BY_DEFAULT) {
                        DialogGenerator.getDialog(new AlertMessage("Estudiante(s) desasignado(s) correctamente", Status.SUCCESS));
                    } else {
                        DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de desasignar. Intente más tarde.", Status.ERROR));
                    }
                } else {
                    DialogGenerator.getDialog(new AlertMessage("Estudiante(s) desasignado(s) correctamente", Status.SUCCESS));
                }
            } else {
                DialogGenerator.getDialog(new AlertMessage("Hubo un problema al momento de desasignar. Intente más tarde.", Status.ERROR));
            }
        }
    }

    private int updateProjectStatus(String status) {
        ProjectDAO projectDAO = new ProjectDAO();
        int result = VALUE_BY_DEFAULT;

        try {
            result = projectDAO.changeProjectStatus(status, idProject);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return result;
    }

    private String getProjectStatus() {
        ProjectDAO projectDAO = new ProjectDAO();
        String status = NOT_FOUND_STRING;

        try {
            status = projectDAO.getProjectStatus(idProject);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return status;
    }

    private boolean isReceptionalWorkActive(int idProject) {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        int total = VALUE_BY_DEFAULT;
        boolean existence = false;

        try {
            total = receptionalWorkDAO.getTotalActiveReceptionalWorks(idProject, RECEPTIONAL_WORK_ACTIVE_STATUS);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        if (total > VALUE_BY_DEFAULT) {
            existence = true;
        }

        return existence;
    }

    private ReceptionalWork getActiveReceptionalWork() {
        ReceptionalWork receptionalWork = new ReceptionalWork();
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();

        try {
            receptionalWork = receptionalWorkDAO.getReceptionalWorkByStatus(idProject, RECEPTIONAL_WORK_ACTIVE_STATUS);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsPostuledCatalogController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
        }

        return receptionalWork;
    }

    private void close() {
        ProjectDataController.setIdProject(idProject);
        SPGER.setRoot("/mx/uv/fei/sspger/GUI/ProjectData.fxml");
    }
}
