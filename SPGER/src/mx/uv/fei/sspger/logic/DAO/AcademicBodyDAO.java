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
import mx.uv.fei.sspger.logic.contracts.IAcademicBody;


public class AcademicBodyDAO implements IAcademicBody {
    private static final String ADD_ACADEMIC_BODY = "INSERT INTO cuerpo_academico(idCuerpoAcademico, nombre) values (?,?)";
    private static final String ADD_ACADEMIC_BODY_MEMBER = "INSERT INTO profesor_cuerpo_academico(idCuerpoAcadémico, idUsuarioProfesor, rol) values (?,?,?)";
    private static final int VALUE_BY_DEFAULT = 0;
    private final String GET_PROFESSOR_QUERY = "SELECT * FROM profesor WHERE correo = ?";

    @Override
    public int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result;
        String query = ADD_ACADEMIC_BODY;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        
        result = statement.executeUpdate();
        
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
        int result ;
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
    public int addAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException{
        int result;
        String query = ADD_ACADEMIC_BODY_MEMBER;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, academicBodyKey);
        statement.setInt(2, academicBodyMember.getId());
        statement.setString(3, academicBodyMember.getRole());

        result = statement.executeUpdate();

        return result;
    }
    
    @Override
    public int updateAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException{
        int result;
        String query = "UPDATE profesor_cuerpo_academico SET idCuerpoAcadémico = ?, idUsuarioProfesor = ?, rol = ? WHERE idProfesorCuerpoAcademico = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);       
        
        statement.setString(1, academicBodyKey);
        statement.setInt(2, academicBodyMember.getId());
        statement.setString(3, academicBodyMember.getRole());
        
        result = statement.executeUpdate();
        
        return result;
    }
    
    @Override
    public int addAcademicBodyTransaction(AcademicBody academicBody) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            result = addAcademicBody(academicBody);
            
            for(int i = 0; i < academicBody.getMember().size(); i++){
                result += addAcademicBodyMember(academicBody.getMember().get(i), academicBody.getKey());
            }
            
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex) {
            DataBaseManager.getConnection().rollback();
        } finally {
            DataBaseManager.getConnection().close();
        }
        
        return result;
    }
    
    @Override
    public AcademicBodyMember getProfessor(String email) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_PROFESSOR_QUERY);

        statement.setString(1,email);

        ResultSet professorResult = statement.executeQuery();
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        
        if(professorResult.next()){
            academicBodyMember.setEMail(professorResult.getString("correo"));
            academicBodyMember.setName(professorResult.getString("nombre"));
            academicBodyMember.setLastName(professorResult.getString("apellido"));
            academicBodyMember.setPersonalNumber(professorResult.getString(
                    "numPersonal"));
            academicBodyMember.setHonorificTitle(professorResult.getString(
                    "honorifico"));
            academicBodyMember.setId(professorResult.getInt("idUsuarioProfesor"));
            academicBodyMember.setIsAdmin(professorResult.getInt("isAdmin"));
        }
        
        DataBaseManager.closeConnection();

        return academicBodyMember;
    }
    
    @Override
    public int getExistenceAcademicBody(AcademicBody academicBody) throws SQLException{
        String query = "SELECT COUNT(*) FROM cuerpo_academico WHERE idCuerpoAcademico = ? AND nombre = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        
        ResultSet academicBodyResult = statement.executeQuery();
        int result = VALUE_BY_DEFAULT;
        
        if(academicBodyResult.next()){
            result = academicBodyResult.getInt(1);
        }
        
        return result;
    }

}