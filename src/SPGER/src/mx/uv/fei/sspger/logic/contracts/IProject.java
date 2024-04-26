package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Student;

public interface IProject {

    int addProjectTransaction(Project project) throws SQLException;

    int modifyProjectTransaction(Project project) throws SQLException;

    List<Project> getAvailableProjectsPerDirectorCard(int idProfessor) throws SQLException;

    List<Project> getProjectsPerDirectorCard(int idProfessor, String role) throws SQLException;

    int totalStudentAccepted(int idStudent) throws SQLException;

    int validateProject(int idProject, int idProfessor) throws SQLException;

    int rejectProject(int idProject, int idProfessor) throws SQLException;

    String getProjectStatus(int idProject) throws SQLException;

    List<Project> getProjectsByStatusPerDirectorCard(int idProfessor, String role, String status) throws SQLException;

    int applyToProject(int idProject, int idStudent) throws SQLException;

    int getProjectsCountByStatus(String projectStatus, String idCuerpoAcademico) throws SQLException;

    Lgac getLgacMostUsed(String idCuerpoAcademico) throws SQLException;

    Lgac getLgacLeastUsed(String idCuerpoAcademico) throws SQLException;

    String getModalityMostUsed(String idCuerpoAcademico) throws SQLException;

    String getModalityLeastUsed(String idCuerpoAcademico) throws SQLException;

    Project getProject(int idProject) throws SQLException;

    List<Lgac> getLgacByProject(int idProject) throws SQLException;

    AcademicBody getAcademicBodyByProject(int idProject) throws SQLException;

    int getTotalProjectSelectedByStudent(int idStudent) throws SQLException;

    int getAvailableSpacesByProject(int idProject) throws SQLException;

    int getExistenceApplicationToProject(int idStudent, int idProject) throws SQLException;

    int changeProjectStatus(String status, int idAnteproyecto) throws SQLException;

    List<Project> getProjectsByStatusSearched(String projectName, String status) throws SQLException;

    String getProfessorWithMostProjects(String idCuerpoAcademico) throws SQLException;

    String getProfessorWithLeastProjects(String idCuerpoAcademico) throws SQLException;

    List<Project> getProjectsByAcademicBody(String idAcademicBody) throws SQLException;

}
