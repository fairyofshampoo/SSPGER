package mx.uv.fei.sspger.logic.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.contracts.IUserManager;

/**
 *
 * @author miche
 */
public class UserManagerDAO implements IUserManager{
    
    private final String ADD_ACCESS_ACCOUNT_QUERY = "insert into cuenta_acceso"
            + "(correo_institucional, (SHA2(password)), privilegios, estado) values"
            + "(?,?,?,?)";
    private final String ADD_PROFESSOR_QUERY = "insert into profesor(correo, "
            + "nombre, apellido, numPersonal, honorifico) values(?,?,?,?,?)";
    private final String GET_ACTIVE_PROFESSORS = "SELECT correo_institucional, "
            + "profesor.nombre, profesor.apellido, profesor.numPersonal, "
            + "profesor.honorifico, profesor.idUsuarioProfesor FROM "
            + "cuenta_acceso INNER JOIN profesor ON "
            + "cuenta_acceso.correo_institucional = profesor.correo "
            + "WHERE cuenta_acceso.estado = 1";
    private final String GET_DISABLED_PROFESSORS = "SELECT "
            + "correo_institucional, profesor.nombre, profesor.apellido, "
            + "profesor.numPersonal, profesor.honorifico, "
            + "profesor.idUsuarioProfesor FROM cuenta_acceso INNER JOIN "
            + "profesor ON cuenta_acceso.correo_institucional = "
            + "profesor.correo WHERE cuenta_acceso.estado = 0";
    @Override
    public int professorAdditionTransaction(Professor professorAccount) throws SQLException {
    int response = -1;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            
            PreparedStatement accessAccountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_QUERY, Statement.RETURN_GENERATED_KEYS);
            
            accessAccountStatement.setString(1, professorAccount.getEMail());
            accessAccountStatement.setString(2, professorAccount.getPassword());
            accessAccountStatement.setInt(3, professorAccount.getPrivileges());
            accessAccountStatement.setInt(4, professorAccount.getUserStatus());
            accessAccountStatement.executeUpdate();
            DataBaseManager.getConnection().commit();
            ResultSet rs = accessAccountStatement.getGeneratedKeys();
            while(rs.next()){
                response = rs.getInt(1);
            }
        } catch (SQLException ex) {
            DataBaseManager.getConnection().rollback();
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }
    
    
    @Override
    public List<Professor> getActiveProfessors() throws SQLException {
    DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet professorResult = statement.executeQuery(
                GET_ACTIVE_PROFESSORS);
        
        List<Professor> professorList = new ArrayList<>();
        
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo_institucional"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString(
                    "apellido"));
            professor.setPersonalNumber(professorResult.getString(
                    "numPersonal"));
            professor.setHonorificTitle(professorResult.getString(
                    "honorifico"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public List<Professor> getDisabledProfessors() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet professorResult = statement.executeQuery(
                GET_DISABLED_PROFESSORS);
        
        List<Professor> professorList = new ArrayList<>();
        
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo_institucional"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString(
                    "apellido"));
            professor.setPersonalNumber(professorResult.getString(
                    "numPersonal"));
            professor.setHonorificTitle(professorResult.getString(
                    "honorifico"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public int studentAdditionTransaction() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
