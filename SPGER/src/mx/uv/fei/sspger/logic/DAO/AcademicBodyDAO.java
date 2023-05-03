package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.AcademicBodyMember;
import mx.uv.fei.sspger.logic.IAcademicBody;


public class AcademicBodyDAO implements IAcademicBody {

    @Override
    public int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result;
        String query = "INSERT INTO cuerpo_academico(idCuerpoAcademico, nombre) values (?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AcademicBody getAcademicBody(String key) throws SQLException {
        String query = "SELECT * FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(query);        
        AcademicBody academicBody = new AcademicBody();
        
        preparedStatement.setString(1, key);
        ResultSet academicBodyResult = preparedStatement.executeQuery();
        academicBodyResult.next();
        
        academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
        academicBody.setName(academicBodyResult.getString("nombre"));
        
        DataBaseManager.closeConnection();
        
        return academicBody;
    }
    
    @Override
    public List<AcademicBody> getAllAcademicBody() throws SQLException{
        String query = "SELECT * FROM cuerpo_academico";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet academicBodyResult = statement.executeQuery(query);
        List<AcademicBody> academicBodyList = new ArrayList<>();

        while(academicBodyResult.next()){
            AcademicBody academicBody = new AcademicBody();
            
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBodyList.add(academicBody);
        }
        
        DataBaseManager.closeConnection();
        
        return academicBodyList;
    }
    
    @Override
    // The parameter include id because it could be updated
    public int updateAcademicBody (String key, AcademicBody academicBody) throws SQLException{
        int result;
        String query = "UPDATE cuerpo_academico SET idCuerpoAcademico = ?, nombre = ? WHERE idCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        statement.setString(3, key);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public int deleteAcademicBody (String key) throws SQLException{
        int result;
        String query = "DELETE FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, key);
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
   public int addAcademicBodyMember(AcademicBodyMember academicBodyMember) throws SQLException{
        int result;
        String query = "INSERT INTO profesor_cuerpo_academico(idCuerpoAcadémico, idUsuarioProfesor, rol) values (?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, academicBodyMember.getIdAcademicBody());
        statement.setInt(2, academicBodyMember.getIdUserProfessor());
        statement.setString(3, academicBodyMember.getRole());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
   
    @Override
    public int updateAcademicBodyMember(AcademicBodyMember academicBodyMember) throws SQLException{
        int result;
        String query = "UPDATE profesor_cuerpo_academico SET idCuerpoAcadémico = ?, idUsuarioProfesor = ?, rol = ? WHERE idProfesorCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, academicBodyMember.getIdAcademicBody());
        statement.setInt(2, academicBodyMember.getIdUserProfessor());
        statement.setString(3, academicBodyMember.getRole());
        statement.setInt(4, academicBodyMember.getId());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
   
    @Override
   public int removeAcademicBodyMember(int idProfessor) throws SQLException{
       int result;
       String query = "DELETE FROM profesor_cuerpo_academico WHERE idProfesorCuerpoAcademico = ?";
       DataBaseManager.getConnection();
       PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
       statement.setInt(1, idProfessor);
        
       result = statement.executeUpdate();
        
       DataBaseManager.closeConnection();
        
       return result;
   }

    @Override
    public List<AcademicBodyMember> getAllAcademicBodyMember() throws SQLException {
        String query = "SELECT * FROM profesor_cuerpo_academico";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet academicBodyMemberResult = statement.executeQuery(query);
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();

        while(academicBodyMemberResult.next()){
            AcademicBodyMember academicBodyMember = new AcademicBodyMember();
            
            academicBodyMember.setId(academicBodyMemberResult.getInt("idProfesorCuerpoAcademico"));
            academicBodyMember.setIdAcademicBody(academicBodyMemberResult.getString("idCuerpoAcadémico"));
            academicBodyMember.setIdUserProfessor(academicBodyMemberResult.getInt("idUsuarioProfesor"));
            academicBodyMember.setRole(academicBodyMemberResult.getString("rol"));
            academicBodyMemberList.add(academicBodyMember);
        }
        
        DataBaseManager.closeConnection();
        
        return academicBodyMemberList;
    }

    @Override
    public AcademicBodyMember getAcademicBodyMember(int id) throws SQLException {
        String query = "SELECT * FROM profesor_cuerpo_academico WHERE idProfesorCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(query);        
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        
        preparedStatement.setInt(1, id);
        ResultSet academicBodyMemberResult = preparedStatement.executeQuery();
        academicBodyMemberResult.next();
        
        academicBodyMember.setId(academicBodyMemberResult.getInt("idProfesorCuerpoAcademico"));
        academicBodyMember.setIdAcademicBody(academicBodyMemberResult.getString("idCuerpoAcadémico"));
        academicBodyMember.setIdUserProfessor(academicBodyMemberResult.getInt("idUsuarioProfesor"));
        academicBodyMember.setRole(academicBodyMemberResult.getString("rol"));
        
        DataBaseManager.closeConnection();
        
        return academicBodyMember;
    }
    
    @Override
    public int getAcademicBodyMemberId(String academicBodyKey, int professorId) throws SQLException{
        String query = "SELECT idProfesorCuerpoAcademico FROM profesor_cuerpo_academico WHERE idCuerpoAcadémico = ? AND idUsuarioProfesor = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);        
        
        statement.setString(1, academicBodyKey);
        statement.setInt(2, professorId);
        
        ResultSet result = statement.executeQuery();
        result.next();
        
        int academicBodyMemberId = result.getInt("idProfesorCuerpoAcademico");
        
        DataBaseManager.closeConnection();
        return academicBodyMemberId;
    }
}