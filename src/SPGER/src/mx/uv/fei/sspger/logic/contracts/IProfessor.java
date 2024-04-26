package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Professor;

public interface IProfessor {

    int addProfessorTransaction(Professor professor) throws SQLException;

    Professor getProfessor(int idUser) throws SQLException;

    List<Professor> getActiveProfessors() throws SQLException;

    List<Professor> getProfessorsByStatus(int status) throws SQLException;

    int updateProfessorTransaction(String email, Professor professor) throws SQLException;

    int updateProfessorWithPasswordTransaction(String email, Professor professor) throws SQLException;

    List<Professor> searchProfessorsbyName(String name) throws SQLException;

    Professor getProfessorsNameByCourse(String courseId) throws SQLException;

    Professor getProfessorByCourse(String courseId) throws SQLException;

    Professor getDirectorByProject(int proyectId) throws SQLException;

    List<Professor> getCoodirectorByProject(int projectId) throws SQLException;

    List<Professor> getAllProfessors() throws SQLException;

    List<Professor> getDirectorsPerProjectStatus(String projectStatus) throws SQLException;

    List<Professor> getProfessorsNotSelectedByAcademicBody(String academicBodyKey) throws SQLException;
}
