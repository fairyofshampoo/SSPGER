package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Professor;


public interface IProfessor {
    int addProfessor(Professor professor) throws SQLException;
    Professor getProfessor(String email) throws SQLException;
    List <Professor> getAllProfessors() throws SQLException;
    int updateProfessor (String email, Professor professor) throws SQLException;
    int deleteProfessor (String email) throws SQLException;
}
