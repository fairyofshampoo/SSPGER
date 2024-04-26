package mx.uv.fei.sspger.GUI.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.sspger.GUI.SPGER;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.DAO.AcademicBodyDAO;
import mx.uv.fei.sspger.logic.DAO.LgacDAO;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Modality;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;
import mx.uv.fei.sspger.logic.Status;

public class ProjectRegisterController implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<AcademicBody> cbxAcademicBody;

    @FXML
    private ChoiceBox<Integer> cbxDuration;

    @FXML
    private TableColumn<LgacTable, CheckBox> tblCIdLgac;

    @FXML
    private TableColumn<LgacTable, String> tblCNameLgac;

    @FXML
    private TableView<LgacTable> tblLgac;

    @FXML
    private TextArea txtBibliography;

    @FXML
    private TextArea txtExpectedResults;

    @FXML
    private TextField txtInvestigationLine;

    @FXML
    private TextArea txtNotes;

    @FXML
    private TextField txtPladeaProjectTitle;

    @FXML
    private TextArea txtProjectRequeriments;

    @FXML
    private TextArea txtProyectDescription;

    @FXML
    private TextArea txtReceptionalWorkDescription;

    @FXML
    private TextField txtReceptionalWorkName;

    @FXML
    private Button btnAddProject;

    @FXML
    private ChoiceBox<String> cbxModality;

    @FXML
    private ChoiceBox<Professor> cbxDirector;

    @FXML
    private ChoiceBox<Professor> cbxCodirector;

    @FXML
    private Label lblReceptionalWorkNameInvalid;

    @FXML
    private TextField txtParticipantsNumber;

    @FXML
    private Label lblPladeaNameInvalid;

    @FXML
    private Label lblRequerimentsInvalid;

    @FXML
    private Label lblParticipantsNumberInvalid;

    @FXML
    private Label lblInvestigationLineInvalid;

    @FXML
    private Label lblModalityInvalid;

    @FXML
    private Label lblInvalidAcademicBody;

    @FXML
    private Label lblDurationInvalid;

    @FXML
    private Label lblProyectDescriptionInvalid;

    @FXML
    private Label lblLgacInvalid;

    @FXML
    private Label lblReceptionalWorkDescriptionInvalid;

    @FXML
    private Label lblResultsInvalid;

    @FXML
    private Label lblNotesInvalid;

    @FXML
    private Label lblBibliographyInvalid;

    @FXML
    private Label lblDirectorInvalid;

    @FXML
    private Label lblCodirector1Invalid;

    @FXML
    private ImageView imgGoBack;
    
    ObservableList<LgacTable> lgacTableObservableList = FXCollections.observableArrayList();
    ArrayList<Integer> durationList = new ArrayList<>(List.of(6, 12, 18, 24));
    private final String PROPOSED_STATUS = ProjectStatus.PROPOSED.getDisplayName();
    private final int ACTIVE_STATUS = 1;
    private final int VALUE_BY_DEFAULT = 0;
    private boolean validation;

    void fillLgacTable() {
        LgacDAO lgacDao = new LgacDAO();
        List<Lgac> lgacList = new ArrayList<>();

        try {
            lgacList = lgacDao.getAllLgac();
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
            showMyProjectsView();
        }

        if (lgacList.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay Lgac registradas", Status.WARNING));
            showMyProjectsView();
        } else {
            for (int i = 0; i < lgacList.size(); i++) {
                Lgac lgac = lgacList.get(i);
                CheckBox cbkLgac = new CheckBox(lgac.getId());
                lgacTableObservableList.add(new LgacTable(cbkLgac, lgac.getName()));
            }

            tblLgac.setItems(lgacTableObservableList);
            tblCIdLgac.setCellValueFactory(new PropertyValueFactory<LgacTable, CheckBox>("cbkLgac"));
            tblCNameLgac.setCellValueFactory(new PropertyValueFactory<LgacTable, String>("nameLgac"));
        }
    }

    private void setAcademicBodyComboBox() {
        AcademicBodyDAO academicBodyDao = new AcademicBodyDAO();
        List<AcademicBody> academicBodies = new ArrayList<>();
        ObservableList<AcademicBody> academicBodiesObservableList = FXCollections.observableArrayList();

        try {
            academicBodies = academicBodyDao.getAcademicBodiesActive();
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
            showMyProjectsView();
        }

        if (academicBodies.isEmpty()) {
            DialogGenerator.getDialog(new AlertMessage("No hay cuerpos académicos registrados", Status.WARNING));
            showMyProjectsView();
        } else {
            academicBodiesObservableList.addAll(academicBodies);
            cbxAcademicBody.setItems(academicBodiesObservableList);
        }

    }

    private void setDurationComboBox() {
        if (!durationList.isEmpty()) {
            ObservableList<Integer> durationObservableList = FXCollections.observableArrayList();
            durationObservableList.addAll(durationList);
            cbxDuration.setItems(durationObservableList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillLgacTable();
        setAcademicBodyComboBox();
        setDurationComboBox();
        setModalityChoiceBox();
        setProfessorsAvailableObservableList();
        setRestrictionsForGraphicElements();
        setListenersForChoiceBox();

    }

    private boolean isCancelConfirmed() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog(
                "¿Deseas cancelar el registro de anteproyecto?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    @FXML
    private void addProjectButtonClicked(ActionEvent event) {
        validation = true;
        desactivateDirectorAndCodirectorsErrorLabels();
        projectSetFormData();
    }

    @FXML
    private void cancelButtonClicked(ActionEvent event) {
        goToPreviousView();
    }

    private void goToPreviousView() {
        if (isCancelConfirmed()) {
            showMyProjectsView();
        }
    }

    private void projectSetFormData() {
        Project project = new Project();
        project.setStatus(PROPOSED_STATUS);
        try {
            project.setName(txtReceptionalWorkName.getText());
        } catch (IllegalArgumentException projectNameException) {
            lblReceptionalWorkNameInvalid.setVisible(true);
            validation = false;
        } finally {
            try {
                project.setDescription(txtProyectDescription.getText());
            } catch (IllegalArgumentException projectDescriptionException) {
                lblProyectDescriptionInvalid.setVisible(true);
                validation = false;
            } finally {
                try {
                    project.setExpectedResults(txtExpectedResults.getText());
                } catch (IllegalArgumentException expectedResultsException) {
                    lblResultsInvalid.setVisible(true);
                    validation = false;
                } finally {
                    try {
                        project.setNotes(txtNotes.getText());
                    } catch (IllegalArgumentException notesException) {
                        lblNotesInvalid.setVisible(true);
                        validation = false;
                    } finally {
                        try {
                            project.setRequeriments(txtProjectRequeriments.getText());
                        } catch (IllegalArgumentException requerimentsException) {
                            lblRequerimentsInvalid.setVisible(true);
                            validation = false;
                        } finally {
                            try {
                                project.setBibliography(txtBibliography.getText());
                            } catch (IllegalArgumentException bibliographyException) {
                                lblBibliographyInvalid.setVisible(true);
                                validation = false;
                            } finally {
                                continueValidations(project);
                            }
                        }
                    }
                }

            }
        }

    }

    private void continueValidations(Project project) {
        try {
            project.setReceptionalWorkDescription(txtReceptionalWorkDescription.getText());
        } catch (IllegalArgumentException workDescriptionException) {
            lblReceptionalWorkDescriptionInvalid.setVisible(true);
            validation = false;
        } finally {
            try {
                project.setPladeaFeiName(txtPladeaProjectTitle.getText());
            } catch (IllegalArgumentException pladeaNameException) {
                lblPladeaNameInvalid.setVisible(true);
                validation = false;
            } finally {
                try {
                    project.setInvestigationLine(txtInvestigationLine.getText());
                } catch (IllegalArgumentException investigationLineException) {
                    lblInvestigationLineInvalid.setVisible(true);
                    validation = false;
                } finally {

                    if (txtParticipantsNumber.getText().isEmpty()) {
                        lblParticipantsNumberInvalid.setVisible(true);
                        validation = false;
                    } else {
                        project.setSpaces(Integer.parseInt(txtParticipantsNumber.getText()));
                    }

                    if (cbxModality.getValue() == null) {
                        lblModalityInvalid.setVisible(true);
                        validation = false;
                    } else {
                        project.setModality(cbxModality.getValue());
                    }

                    if (cbxDuration.getValue() == null) {
                        lblDurationInvalid.setVisible(true);
                        validation = false;
                    } else {
                        project.setDuration(cbxDuration.getValue());
                    }

                    if (cbxAcademicBody.getValue() == null) {
                        lblInvalidAcademicBody.setVisible(true);
                        validation = false;
                    } else {
                        project.setAcademicBody(cbxAcademicBody.getValue());
                    }
                    if (verifyDirectorAndCodirectors() == false) {
                        validation = false;
                    } else {
                        project.setDirector(cbxDirector.getValue());
                        project.addCodirector(cbxCodirector.getValue());
                    }
                    verifyLgacSelection(project);
                }
            }
        }
    }

    private void verifyLgacSelection(Project project) {
        for (int i = 0; i < tblLgac.getItems().size(); i++) {
            if (tblLgac.getItems().get(i).getCbkLgac().isSelected()) {
                Lgac lgac = new Lgac();
                lgac.setId(tblLgac.getItems().get(i).getCbkLgac().getText());
                project.addLgac(lgac);
                lblLgacInvalid.setVisible(false);
            }
        }

        if (project.getLgac() == null) {
            validation = false;
            lblLgacInvalid.setVisible(true);
        }

        if (validation == true) {
            projectRegister(project);
        }
    }

    private boolean verifyDirectorAndCodirectors() {
        boolean directorValidation = true;
        if (cbxDirector.getValue() == null || cbxCodirector.getValue() == null) {
            activeDirectorAndCodirectorsErrorLabels();
            directorValidation = false;
        } else if (cbxDirector.getValue().equals(cbxCodirector.getValue())) {
            directorValidation = false;
            showDirectorDuplicatedAlert();
            activeDirectorAndCodirectorsErrorLabels();
        }
        return directorValidation;
    }

    private void activeDirectorAndCodirectorsErrorLabels() {
        lblDirectorInvalid.setVisible(true);
        lblCodirector1Invalid.setVisible(true);
    }

    private void desactivateDirectorAndCodirectorsErrorLabels() {
        lblDirectorInvalid.setVisible(false);
        lblCodirector1Invalid.setVisible(false);
    }

    private void setRestrictionsForGraphicElements() {
        UnaryOperator<TextFormatter.Change> participantsNumberFormatter = change -> {
            if (change.getControlNewText().matches("^[1-9]?$")) {
                return change;
            } else {
                return null;
            }
        };

        txtParticipantsNumber.setTextFormatter(new TextFormatter<>(participantsNumberFormatter));
    }

    private void projectRegister(Project project) {
        try {
            ProjectDAO projectDao = new ProjectDAO();
            if (projectDao.addProjectTransaction(project) > VALUE_BY_DEFAULT) {
                DialogGenerator.getDialog(new AlertMessage("Registro de anteproyecto exitoso", Status.SUCCESS));
                showMyProjectsView();
            } else {
                DialogGenerator.getDialog(new AlertMessage("No se pudo realizar el registro", Status.ERROR));
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }
    }

    private void setModalityChoiceBox() {
        for (Modality modalities : Modality.values()) {
            cbxModality.getItems().add(modalities.getDisplayName());
        }
    }

    private void setProfessorsAvailableObservableList() {
        ProfessorDAO professorDao = new ProfessorDAO();
        List<Professor> professorsList = new ArrayList<>();
        ObservableList<Professor> professorsObservableList = FXCollections.observableArrayList();

        try {
            professorsList = professorDao.getProfessorsByStatus(ACTIVE_STATUS);
            if (!professorsList.isEmpty()) {
                professorsObservableList.addAll(professorsList);
                setDirectorAndCodirectorsChoiceBox(professorsObservableList);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            FailAlert.showFailedConnectionAlert();
            showMyProjectsView();
        }

    }

    private void setDirectorAndCodirectorsChoiceBox(ObservableList<Professor> professorsObservableList) {
        cbxDirector.setItems(professorsObservableList);
        cbxCodirector.setItems(professorsObservableList);
    }

    private void showMyProjectsView() {
        SPGER.setRoot("MyProjects.fxml");
    }

    private void showDirectorDuplicatedAlert() {
        DialogGenerator.getDialog(new AlertMessage("No se puede duplicar la selección de director y codirector", Status.WARNING));
    }

    private boolean showNoCodirectorSelected() {
        Optional<ButtonType> response = DialogGenerator.getConfirmationDialog("No ha seleccionado codirectores, ¿desea continuar?");
        return (response.get() == DialogGenerator.BUTTON_YES);
    }

    @FXML
    private void typingReceptionalWorkName(KeyEvent event) {
        lblReceptionalWorkNameInvalid.setVisible(false);
    }

    @FXML
    private void typingPladeaProjectTitle(KeyEvent event) {
        lblPladeaNameInvalid.setVisible(false);
    }

    @FXML
    private void typingInvestigationLine(KeyEvent event) {
        lblInvestigationLineInvalid.setVisible(false);
    }

    @FXML
    private void typingProjectRequeriments(KeyEvent event) {
        lblRequerimentsInvalid.setVisible(false);
    }

    @FXML
    private void typingProyectDescription(KeyEvent event) {
        lblProyectDescriptionInvalid.setVisible(false);
    }

    @FXML
    private void typingReceptionalWorkDescription(KeyEvent event) {
        lblReceptionalWorkDescriptionInvalid.setVisible(false);
    }

    @FXML
    private void typingExpectedResults(KeyEvent event) {
        lblResultsInvalid.setVisible(false);
    }

    @FXML
    private void typingBibliography(KeyEvent event) {
        lblBibliographyInvalid.setVisible(false);
    }

    @FXML
    private void typingParticipantsNumber(KeyEvent event) {
        lblParticipantsNumberInvalid.setVisible(false);
    }

    @FXML
    private void typingNotes(KeyEvent event) {
        lblNotesInvalid.setVisible(false);
    }

    private void setListenersForChoiceBox() {
        academicBodyChoiceBoxListener();
        durationChoiceBoxListener();
        modalityChoiceBoxListener();
        directorChoiceBoxListener();
        codirectorChoiceBoxListener();
    }

    private void academicBodyChoiceBoxListener() {
        cbxAcademicBody.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AcademicBody>() {
            @Override
            public void changed(ObservableValue<? extends AcademicBody> observable, AcademicBody oldValue, AcademicBody newValue) {
                lblInvalidAcademicBody.setVisible(false);
            }
        });
    }

    private void durationChoiceBoxListener() {
        cbxDuration.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                lblDurationInvalid.setVisible(false);
            }
        });
    }

    private void modalityChoiceBoxListener() {
        cbxModality.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblModalityInvalid.setVisible(false);
            }
        });
    }

    private void directorChoiceBoxListener() {
        cbxDirector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Professor>() {
            @Override
            public void changed(ObservableValue<? extends Professor> observable, Professor oldValue, Professor newValue) {
                lblDirectorInvalid.setVisible(false);
            }
        });
    }

    private void codirectorChoiceBoxListener() {
        cbxCodirector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Professor>() {
            @Override
            public void changed(ObservableValue<? extends Professor> observable, Professor oldValue, Professor newValue) {
                lblCodirector1Invalid.setVisible(false);
            }
        });
    }

    @FXML
    private void goBackButtonClicked(MouseEvent event) {
        goToPreviousView();
    }

}
