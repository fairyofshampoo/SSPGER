package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Student;


public interface IProject {
    int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException;
    List <Project> getAllProjects() throws SQLException;
    Project getProjectByStudent(int studentId) throws SQLException;
    List<Project> getProjectsByStatus(String status) throws SQLException;
    int applyToProject(int idProject, int idStudent) throws SQLException;
    List<Project> getAvailableProjectsPerDirectorCard(int idProfessor) throws SQLException;
    List<Project> getProjectsPerDirectorCard(int idProfessor, String role) throws SQLException;
    int getProjectsCountByStatus(String projectStatus) throws SQLException;
    Lgac getLgacMostUsed() throws SQLException;
    Lgac getLgacLeastUsed() throws SQLException;
    String getModalityMostUsed() throws SQLException;
    String getModalityLeastUsed() throws SQLException;
    int deleteStudentPostuledToProject(int idStudent) throws SQLException;
    int updateStudentStatusToAccepted(int idStudent, int idProject) throws SQLException;
    int getExistenceApplicationToProject(int idStudent, int idProject) throws SQLException;
    int getAvailableSpacesByProject(int idProject) throws SQLException;
    int getTotalProjectSelectedByStudent(int idStudent) throws SQLException;
    int expellStudent(int idProject, List<Student> studentList) throws SQLException;
    int acceptToProject(int idProject, List<Student> studentList) throws SQLException;
    Project getProject(int idProject) throws SQLException;
    List<Lgac> getLgacByProject(int idProject) throws SQLException;
    AcademicBody getAcademicBodyByProject(int idProject) throws SQLException;
    int changeProjectStatus(String status, int idAnteproyecto) throws SQLException;
    List<Project> getProjectsByStatusPerDirectorCard(int idProfessor, String role, String status) throws SQLException;
}
