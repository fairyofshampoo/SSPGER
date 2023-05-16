package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;

public interface IUser{
    
    int login(String email, String password) throws SQLException;
    int isProfessor(String email) throws SQLException;
    int isStudent(String email) throws SQLException;
    int usersAvailables() throws SQLException;
    int professorPrivileges(int id) throws SQLException;
}
