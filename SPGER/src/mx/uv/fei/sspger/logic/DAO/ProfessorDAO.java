package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IProfessor;
import mx.uv.fei.sspger.logic.Professor;


public class ProfessorDAO implements IProfessor{
    private final String ADD_PROFESSOR_QUERY = "insert into profesor(correo, "
            + "nombre, apellido, numPersonal, honorifico) values(?,?,?,?,?)";
    private final String GET_PROFESSOR_QUERY = "SELECT * FROM profesor WHERE "
            + "correo = ?";
    private final String GET_ALL_PROFESSORS_QUERY = "SELECT * FROM profesor";
    private final String UPDATE_PROFESSOR_QUERY = "UPDATE profesor SET correo ="
            + " ?, nombre = ?, apellido = ?, numPersonal = ?, honorifico = "
            + "? WHERE correo = ?";
            
    @Override
    public int addProfessor(Professor professor) throws SQLException {
    int resultProfessor;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(ADD_PROFESSOR_QUERY);
        
        statement.setString(1, professor.getEMail());
        statement.setString(2, professor.getName());
        statement.setString(3, professor.getLastName());
        statement.setString(4, professor.getPersonalNumber());
        statement.setString(5, professor.getHonorificTitle());
        
        resultProfessor = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return resultProfessor;
    }

    @Override
    public Professor getProfessor(String email) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_PROFESSOR_QUERY);

        statement.setString(1,email);

        ResultSet professorResult = statement.executeQuery();
        professorResult.next();
        
        Professor professor = new Professor();
        professor.setEMail(professorResult.getString("correo"));
        professor.setName(professorResult.getString("nombre"));
        professor.setLastName(professorResult.getString("apellido"));
        professor.setPersonalNumber(professorResult.getString(
                "numPersonal"));
        professor.setHonorificTitle(professorResult.getString(
                "honorifico"));
        professor.setId(professorResult.getInt("idUsuarioProfesor"));
        
        DataBaseManager.closeConnection();

        return professor;
    }

    @Override
    public List<Professor> getAllProfessors() throws SQLException {
        DataBaseManager.getConnection();
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
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public int updateProfessor(String email, Professor professor) throws SQLException {
        int resultProfessor;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(UPDATE_PROFESSOR_QUERY);       
        
        statement.setString(1, professor.getEMail());
        statement.setString(2, professor.getName());
        statement.setString(3, professor.getLastName());
        statement.setString(4, professor.getPersonalNumber());
        statement.setString(5, professor.getHonorificTitle());
        statement.setString(6, email);
        
        resultProfessor = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return resultProfessor;
    }

    @Override
    public int deleteProfessor(String email) throws SQLException {
        int resultProfessor;
        String query = "DELETE FROM profesor WHERE correo = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(query);
        
        statement.setString(1, email);
        
        resultProfessor = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return resultProfessor;
    }
    
}
