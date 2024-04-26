package mx.uv.fei.sspger.GUI.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Status;
import mx.uv.fei.sspger.logic.Student;

public class UsersManagerController implements Initializable {

    @FXML
    private Button btnAddUser;

    @FXML
    private GridPane gpUsers;

    @FXML
    private ImageView imgSearchBar;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private Pane pnNavigationBar;

    private final int ACTIVE_STATUS = 1;
    private final int COLUMN_START = 0;
    private final int ROW_START = 1;
    private int column = COLUMN_START;
    private int row = ROW_START;

    @FXML
    void addUserButtonClicked(ActionEvent event) {
        SPGER.setRoot("UserRegister.fxml");
    }

    void homeClicked(MouseEvent mouseEvent) {
        SPGER.setRoot("HomeProfessor.fxml");
    }

    @FXML
    void searchUser(MouseEvent event) {
        gpUsers.getChildren().clear();
        column = COLUMN_START;
        row = ROW_START;
        if (txtSearchBar.getText().isEmpty()) {
            setStudentCards(getAllStudents());
            setProfessorCards(getAllProfessors());
        } else {
            continueSearching();
        }
    }

    private void continueSearching() {
        boolean isSearchSucessful = false;
        List<Student> studentList = createStudentSearchList();
        if (!studentList.isEmpty()) {
            setStudentCards(studentList);
            isSearchSucessful = true;
        }
        List<Professor> professorList = createProfessorSearchList();
        if (!professorList.isEmpty()) {
            setProfessorCards(professorList);
            isSearchSucessful = true;
        }
        showSearchNotFoundAlert(isSearchSucessful);
    }

    private void showSearchNotFoundAlert(boolean isSearchSucessful) {
        if (isSearchSucessful == false) {
            DialogGenerator.getDialog(new AlertMessage("No hay coincidencias en la búsqueda", Status.WARNING));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayImages();
        setStudentCards(getAllStudents());
        setProfessorCards(getAllProfessors());
        setNavigationBar();
    }

    List<Student> createStudentSearchList() {
        List<Student> studentsList = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();

        try {
            studentsList = studentDao.searchStudentbyName(txtSearchBar.getText());
        } catch (SQLException sqlException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }
        return studentsList;
    }

    List<Professor> createProfessorSearchList() {
        List<Professor> professorsList = new ArrayList<>();
        ProfessorDAO professorDao = new ProfessorDAO();
        try {
            professorsList = professorDao.searchProfessorsbyName(txtSearchBar.getText());
        } catch (SQLException sqlException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }
        return professorsList;
    }

    private void displayImages() {
        imgSearchBar.setImage(ImagesSetter.getSearchBarImage());
    }

    private List<Student> getAllStudents() {
        List<Student> studentsList = new ArrayList<>();
        StudentDAO studentDao = new StudentDAO();

        try {
            studentsList = studentDao.getAllStudents();
        } catch (SQLException sqlException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }
        return studentsList;
    }

    private void setStudentCards(List<Student> studentsList) {
        try {
            for (int card = 0; card < studentsList.size(); card++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/UsersCard.fxml"));
                HBox studentCard = fxmlLoader.load();
                UsersCardController usersCardController = fxmlLoader.getController();
                usersCardController.setUserStudentData(studentsList.get(card));

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpUsers.add(studentCard, column++, row);
                GridPane.setMargin(studentCard, new Insets(10));
            }
        } catch (IOException ioException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
        }
    }

    private List<Professor> getAllProfessors() {
        List<Professor> professorsList = new ArrayList<>();
        ProfessorDAO professorDao = new ProfessorDAO();

        try {
            professorsList = professorDao.getAllProfessors();
        } catch (SQLException sqlException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, sqlException);
            showFailedConnectionAlert();
        }
        return professorsList;
    }

    private void setProfessorCards(List<Professor> professorsList) {
        try {
            for (int card = 0; card < professorsList.size(); card++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/mx/uv/fei/sspger/GUI/UsersCard.fxml"));
                HBox professorCard = fxmlLoader.load();
                UsersCardController usersCardController = fxmlLoader.getController();
                usersCardController.setUserProfessorData(professorsList.get(card));

                if (column == 2) {
                    column = 0;
                    row++;
                }

                gpUsers.add(professorCard, column++, row);
                GridPane.setMargin(professorCard, new Insets(10));
            }
        } catch (IOException ioException) {
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ioException);
            showFXMLFileFailedAlert();
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
            Logger.getLogger(UsersManagerController.class.getName()).log(Level.SEVERE, null, ex);
            showFXMLFileFailedAlert();
        }
    }

    private void showFailedConnectionAlert() {
        DialogGenerator.getDialog(new AlertMessage("Error de conexión con la base de datos. Intente nuevamente o regrese más tarde.", Status.FATAL));
    }

    private void showFXMLFileFailedAlert() {
        DialogGenerator.getDialog(new AlertMessage("Archivo FXML corrupto.", Status.FATAL));
    }

}
