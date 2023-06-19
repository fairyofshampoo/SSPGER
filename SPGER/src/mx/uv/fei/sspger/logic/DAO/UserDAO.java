package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IUser;

public class UserDAO implements IUser{
    private final int ERROR_ADDITION = -1;
    private final String LOGIN_QUERY = "SELECT * FROM cuenta_acceso WHERE correo = ? AND password = SHA1(?) AND estado = ?";
    private final String PROFESSOR_LOGIN_QUERY = "SELECT idUsuarioProfesor FROM profesor WHERE correo = ?";
    private final String STUDENT_LOGIN_QUERY = "SELECT idUsuarioEstudiante FROM estudiante WHERE correo_institucional = ?";
    private final String PROFESSOR_PRIVILEGES_QUERY = "SELECT isAdmin FROM profesor WHERE idUsuarioProfesor = ?";
    private final String ANY_AVAILABLE_ACCOUNT_QUERY = "SELECT * FROM cuenta_acceso WHERE estado = ?";
    private final int ACTIVE_STATUS = 1;
    private final String SEARCH_EMAIL_QUERY = "SELECT COUNT(*) FROM cuenta_acceso where correo = ?";
    
    @Override
    public int login(String email, String password) throws SQLException {
        int response = ERROR_ADDITION;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(LOGIN_QUERY);
        
        statement.setString(1, email);
        statement.setString(2, password);
        statement.setInt(3,ACTIVE_STATUS);
        
        ResultSet loginResult = statement.executeQuery();
        
        if(loginResult.next()){
            response = 1;
        }
        
        DataBaseManager.closeConnection();
        return response;
    }

    @Override
    public int isProfessor(String email) throws SQLException {
        int response = ERROR_ADDITION;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(PROFESSOR_LOGIN_QUERY);
        
        statement.setString(1, email);
        
        ResultSet professorResult = statement.executeQuery();
        
        if(professorResult.next()){
            response = professorResult.getInt("idUsuarioProfesor");
        }
        
        DataBaseManager.closeConnection();
        return response;
    }

    @Override
    public int isStudent(String email) throws SQLException {
        int response = ERROR_ADDITION;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(STUDENT_LOGIN_QUERY);
        
        statement.setString(1, email);
        
        ResultSet studentResult = statement.executeQuery();
        
        if(studentResult.next()){
            response = studentResult.getInt("idUsuarioEstudiante");
        }
        
        DataBaseManager.closeConnection();
        return response;
    }

    @Override
    public boolean usersAvailables() throws SQLException {
        boolean response = false;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ANY_AVAILABLE_ACCOUNT_QUERY);
        
        statement.setInt(1, ACTIVE_STATUS);
        ResultSet studentResult = statement.executeQuery();
        
        if(studentResult.next()){
            response = true;
        }
        
        DataBaseManager.closeConnection();
        return response;
    }

    @Override
    public int professorPrivileges(int id) throws SQLException {
        int response = ERROR_ADDITION;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(PROFESSOR_PRIVILEGES_QUERY);
        
        statement.setInt(1, id);
        
        ResultSet privilegesResult = statement.executeQuery();
        
        if(privilegesResult.next()){
            response = privilegesResult.getInt("isAdmin");
        }
        
        DataBaseManager.closeConnection();
        return response;
    }

    @Override
    public int searchEmailDuplication(String email) throws SQLException {
        int response = ERROR_ADDITION;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_EMAIL_QUERY);
        
        statement.setString(1, email);
        ResultSet emailCountResult = statement.executeQuery();
        
        if(emailCountResult.next()){
            response = emailCountResult.getInt(1);
        }
        
        return response;
    }
    
}
