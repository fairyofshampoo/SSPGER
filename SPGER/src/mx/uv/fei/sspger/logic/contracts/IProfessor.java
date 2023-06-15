package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Professor;


public interface IProfessor {
    int addProfessorTransaction(Professor professor) throws SQLException;
    Professor getProfessor(int idUser) throws SQLException;
    List<Professor> getProfessorsByStatus(int status) throws SQLException;
    int updateProfessorTransaction(String email, Professor professor) throws SQLException;
    int changeProfessorStatus(String email, int status) throws SQLException;
    Professor getProfessorByPersonalNumber(String personalNumber) throws SQLException;
    Professor getProfessor(String email) throws SQLException;
    Professor getProfessorByCourse (String courseId) throws SQLException;
    Professor getDirectorByProject (int proyectId) throws SQLException;
    List<Professor> getCoodirectorByProject (int projectId) throws SQLException;
    Professor getProfessorById (int professorId) throws SQLException;
    List <Professor> getAllProfessors() throws SQLException;
    List<Professor> getDirectorsPerProjectStatus(String projectStatus) throws SQLException;
}
