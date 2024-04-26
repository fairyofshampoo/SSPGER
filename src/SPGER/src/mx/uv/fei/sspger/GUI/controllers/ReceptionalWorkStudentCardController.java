package mx.uv.fei.sspger.GUI.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mx.uv.fei.sspger.logic.DAO.ProfessorDAO;
import mx.uv.fei.sspger.logic.DAO.ReceptionalWorkDAO;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.ReceptionalWork;

public class ReceptionalWorkStudentCardController {

    @FXML
    private Label lblDirector;
    @FXML
    private Label lblReceptionalWorkTitle;
    @FXML
    private Label lblCodirector;
    @FXML
    private AnchorPane apReceptionalWork;
    
    private final int ERROR = -1;
    
    public void setReceptionalWorkData(ReceptionalWork receptionalWork) {
        lblReceptionalWorkTitle.setText(receptionalWork.getName());
        lblDirector.setText(getDirectorName(receptionalWork.getIdProject()));
        lblCodirector.setText(getCodirectorsName(receptionalWork.getIdProject()));
    }
    
    private String getDirectorName(int idProject) {

        ProfessorDAO professorDao = new ProfessorDAO();
        Professor professor = new Professor();
        String directorName = "";
        try {
            professor = professorDao.getDirectorByProject(idProject);
            if(professor.getId() != ERROR){
                directorName = professor.getHonorificTitle() + " " + professor.getName() + " " + professor.getLastName();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkStudentCardController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ReceptionalWorkStudentCardController.class.getName()).log(Level.SEVERE, null, sqlException);
            FailAlert.showFailedConnectionAlert();
        }

        return codirectorsName;
    }

}
