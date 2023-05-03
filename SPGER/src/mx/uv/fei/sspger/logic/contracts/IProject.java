package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ReceptionalWork;


public interface IProject {
    int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException;
    int addReceptionalWork(ReceptionalWork receptionalWork) throws SQLException;
    Project getProject(String idProject) throws SQLException;
    ReceptionalWork getReceptionalWork(String idReceptionalWork) throws SQLException;
    List <ReceptionalWork> getAllReceptionalWorks() throws SQLException;
    List <Project> getAllProjects() throws SQLException;
    int updateProject (int idProject, Project project) throws SQLException;
    int deleteProject (int idProject) throws SQLException;
    int updateReceptionalWork (int idReceptionalWork) throws SQLException;
    int deleteReceptionalWork (int idReceptionalWork) throws SQLException;
}
