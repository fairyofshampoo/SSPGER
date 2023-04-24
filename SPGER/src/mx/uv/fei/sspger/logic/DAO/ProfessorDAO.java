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

    @Override
    public int addProfessor(Professor professor) throws SQLException {
    int result;
        String query = "insert into profesor(correo, nombre, apellido, numPersonal, honorifico) values(?,?,?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, professor.getEMail());
        statement.setString(2, professor.getName());
        statement.setString(3, professor.getLastName());
        statement.setString(4, professor.getPersonalNumber());
        statement.setString(5, professor.getHonorificTitle());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public Professor getProfessor(String email) throws SQLException {
        String query = "SELECT * FROM profesor WHERE correo = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1,email);

        ResultSet professorResult = statement.executeQuery();
        professorResult.next();
        
        Professor professor = new Professor();
        professor.setEMail(professorResult.getString("correo"));
        professor.setName(professorResult.getString("nombre"));
        professor.setLastName(professorResult.getString("apellido"));
        professor.setPersonalNumber(professorResult.getString("numPersonal"));
        professor.setHonorificTitle(professorResult.getString("honorifico"));
        
        DataBaseManager.closeConnection();

        return professor;
    }

    @Override
    public List<Professor> getAllProfessors() throws SQLException {
        String query = "SELECT * FROM profesor";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet professorResult = statement.executeQuery(query);
        
        List<Professor> professorList = new ArrayList<>();
        
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professorList.add(professor);
        }
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public int updateProfessor(String email, Professor professor) throws SQLException {
        int result;
        String query = "UPDATE profesor SET correo = ?, nombre = ?, apellido = ?, numPersonal = ?, honorifico = ? WHERE correo = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, professor.getEMail());
        statement.setString(2, professor.getName());
        statement.setString(3, professor.getLastName());
        statement.setString(4, professor.getPersonalNumber());
        statement.setString(5, professor.getHonorificTitle());
        statement.setString(6, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public int deleteProfessor(String email) throws SQLException {
        int result;
        String query = "DELETE FROM profesor WHERE correo = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, email);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public Professor getProfessorByCourse(String courseId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Professor getProfessorByProyect(int proyectId) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public Professor getProfessorById(int professorId) throws SQLException {
        String query = "SELECT * FROM profesor WHERE idUsuarioProfesor = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setInt(1,professorId);

        ResultSet professorResult = statement.executeQuery();
        professorResult.next();
        
        Professor professor = new Professor();
        professor.setEMail(professorResult.getString("correo"));
        professor.setName(professorResult.getString("nombre"));
        professor.setLastName(professorResult.getString("apellido"));
        professor.setPersonalNumber(professorResult.getString("numPersonal"));
        professor.setHonorificTitle(professorResult.getString("honorifico"));
        
        DataBaseManager.closeConnection();

        return professor;
    }
    
}
