package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Student;

public interface IUserManager {
    int professorAdditionTransaction(Professor professorAccount) throws SQLException;
    int studentAdditionTransaction() throws SQLException;
    List <Professor> getActiveProfessors() throws SQLException;
    List <Professor> getDisabledProfessors() throws SQLException;
}
