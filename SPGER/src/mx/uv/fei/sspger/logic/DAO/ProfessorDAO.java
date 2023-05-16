package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IProfessor;
import mx.uv.fei.sspger.logic.Professor;


public class ProfessorDAO implements IProfessor{
    private final int ERROR_ADDITION = -1;
    private final String ADD_PROFESSOR_COMMAND = "insert into profesor(correo, nombre, apellido, numPersonal, honorifico, isAdmin) values(?,?,?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_PROFESSOR_QUERY = "SELECT * FROM profesor WHERE correo = ?";
    private final String GET_ALL_PROFESSORS_QUERY = "SELECT * FROM profesor";
    private final String GET_PROFESSORS_BY_STATUS_QUERY = "SELECT profesor.* FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String UPDATE_PROFESSOR_COMMAND = "UPDATE profesor SET nombre = ?, apellido = ?, numPersonal = ?, honorifico = ?, isAdmin = ? WHERE correo = ?";
    private final String CHANGE_PROFESSOR_STATUS_QUERY = "UPDATE cuenta_acceso SET estado = ? WHERE correo = ?";

    
    @Override
    public int addProfessorTransaction(Professor professor) throws SQLException {
        int response = ERROR_ADDITION;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, professor.getEMail());
            accountStatement.setString(2, professor.getPassword());
            accountStatement.setInt(3, professor.getStatus());
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR_ADDITION){
                PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROFESSOR_COMMAND);
        
                professorStatement.setString(1, professor.getEMail());
                professorStatement.setString(2, professor.getName());
                professorStatement.setString(3, professor.getLastName());
                professorStatement.setString(4, professor.getPersonalNumber());
                professorStatement.setString(5, professor.getHonorificTitle());
                professorStatement.setInt(6, professor.getIsAdmin());
            
                response = response + professorStatement.executeUpdate();
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    @Override
    public Professor getProfessor(String email) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_PROFESSOR_QUERY);

        statement.setString(1,email);

        ResultSet professorResult = statement.executeQuery();
        Professor professor = new Professor();
        if(professorResult.next()){
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString(
                "numPersonal"));
            professor.setHonorificTitle(professorResult.getString(
                "honorifico"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
        }
        
        DataBaseManager.closeConnection();

        return professor;
    }

    @Override
    public List<Professor> getAllProfessors() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet professorResult = statement.executeQuery(
                GET_ALL_PROFESSORS_QUERY);
        
        List<Professor> professorList = new ArrayList<>();
        
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString(
                    "apellido"));
            professor.setPersonalNumber(professorResult.getString(
                    "numPersonal"));
            professor.setHonorificTitle(professorResult.getString(
                    "honorifico"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }
    
    @Override
    public List<Professor> getProfessorsByStatus(int status) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_PROFESSORS_BY_STATUS_QUERY);

        statement.setInt(1, status);

        ResultSet professorResult = statement.executeQuery();
        
        List<Professor> professorList = new ArrayList<>();
        
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString(
                    "apellido"));
            professor.setPersonalNumber(professorResult.getString(
                    "numPersonal"));
            professor.setHonorificTitle(professorResult.getString(
                    "honorifico"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public int updateProfessorTransaction(String email, Professor professor) throws SQLException {
        int response = ERROR_ADDITION;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, professor.getEMail());
            accountStatement.setString(2, professor.getPassword());
            accountStatement.setInt(3, professor.getStatus());
            accountStatement.setString(4, email);
        
            response = accountStatement.executeUpdate();
        
            if(response != ERROR_ADDITION){
                PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROFESSOR_COMMAND);
                
                professorStatement.setString(1, professor.getName());
                professorStatement.setString(2, professor.getLastName());
                professorStatement.setString(3, professor.getPersonalNumber());
                professorStatement.setString(4, professor.getHonorificTitle());
                professorStatement.setInt(5, professor.getIsAdmin());
                professorStatement.setString(6, email);
            
                response = response + professorStatement.executeUpdate();
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            DataBaseManager.getConnection().rollback();
            Logger.getLogger(ProfessorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    @Override
    public int changeProfessorStatus(String email, int status) throws SQLException {
        int response = ERROR_ADDITION;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(CHANGE_PROFESSOR_STATUS_QUERY);       
        
        statement.setInt(1, status);
        statement.setString(2, email);
        
        response = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return response;
    }

    
}
