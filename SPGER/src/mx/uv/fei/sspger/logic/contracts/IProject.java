package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;


public interface IProject {
    int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException;
    Project getProject(String idProject) throws SQLException;
    List <Project> getAllProjects() throws SQLException;
    int updateProject (int idProject, Project project) throws SQLException;
    int deleteProject (int idProject) throws SQLException;
    List <Project> getAvailableProjects() throws SQLException;
    int applyToProject(int idProject, int idStudent) throws SQLException;
    List<Project> getAvailableProjectCard() throws SQLException;
    List<Project> getAvailableProjectsPerDirectorCard(int idProfessor) throws SQLException;
    List<Project> getProjectsPerDirectorCard(int idProfessor) throws SQLException;
    int getProjectsCountByStatus(String projectStatus) throws SQLException;
    Lgac getLgacMostUsed() throws SQLException;
    Lgac getLgacLeastUsed() throws SQLException;
    String getModalityMostUsed() throws SQLException;
    String getModalityLeastUsed() throws SQLException;
    Professor getDirectorNameMostValidatedProjects() throws SQLException;
    Professor getDirectorNameLeastValidatedProjects() throws SQLException;
}