package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Professor;


public interface IProfessor {
    int addProfessorTransaction(Professor professor) throws SQLException;
    Professor getProfessor(String email) throws SQLException;
    List <Professor> getAllProfessors() throws SQLException;
    List <Professor> getProfessorsByStatus(int status) throws SQLException;
    int updateProfessorTransaction (String email, Professor professor) throws SQLException;
    int changeProfessorStatus (String email, int status) throws SQLException;
}
