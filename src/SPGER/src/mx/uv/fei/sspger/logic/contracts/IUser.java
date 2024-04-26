package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;

public interface IUser {

    int login(String email, String password) throws SQLException;

    int isProfessor(String email) throws SQLException;

    int isStudent(String email) throws SQLException;

    boolean usersAvailables() throws SQLException;

    int updateStatus(String email, int status) throws SQLException;

    int professorPrivileges(int id) throws SQLException;

    int searchEmailDuplication(String email) throws SQLException;
}
