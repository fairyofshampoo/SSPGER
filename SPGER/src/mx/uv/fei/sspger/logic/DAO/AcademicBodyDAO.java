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
    private final String ADD_ACADEMIC_BODY = "INSERT INTO cuerpo_academico(idCuerpoAcademico, nombre, estado) values (?,?,?)";
    private final String ADD_ACADEMIC_BODY_MEMBER = "INSERT INTO profesor_cuerpo_academico(idCuerpoAcadémico, idUsuarioProfesor, rol) values (?,?,?)";
    private final String GET_ACADEMIC_BODY = "SELECT * FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
    private final String GET_ALL_ACADEMIC_BODY = "SELECT * FROM cuerpo_academico";
    private final String GET_ACADEMIC_BODY_MEMBER_BY_ROLE = "SELECT profesor_cuerpo_academico.idProfesorCuerpoAcademico, profesor_cuerpo_academico.idUsuarioProfesor, profesor_cuerpo_academico.rol, profesor.nombre, profesor.apellido, profesor.honorifico, profesor.correo"
            + " FROM profesor_cuerpo_academico INNER JOIN profesor ON profesor.idUsuarioProfesor = profesor_cuerpo_academico.idUsuarioProfesor WHERE idCuerpoAcadémico = ? AND rol = ?";
    private final String GET_PROFESSOR_QUERY = "SELECT correo, idUsuarioProfesor FROM profesor WHERE correo = ?";
    private final String UPDATE_ACADEMIC_BODY = "UPDATE cuerpo_academico SET idCuerpoAcademico = ?, nombre = ? WHERE idCuerpoAcademico = ?";
    private final String DELETE_ACADEMIC_BODY = "DELETE FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
    private final String EXISTENCE_ACADEMIC_BODY = "SELECT COUNT(*) FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
    private final String UPDATE_ACADEMIC_BODY_STATUS = "UPDATE cuerpo_academico SET estado = ? WHERE idCuerpoAcademico = ?";
    private final String DELETE_ACADEMIC_BODY_MEMBER = "DELETE FROM profesor_cuerpo_academico WHERE idCuerpoAcadémico = ? AND idUsuarioProfesor = ?";
    private final int VALUE_BY_DEFAULT = 0;
    private final String NOT_FOUND = "UV-CA-0000";
    private final int NOT_FOUND_INT = -1;
    private final String RESPONSIBLE_ROLE = "Responsable";
    private final String MEMBER_ROLE = "Miembro";
    private final int ACTIVE_STATUS = 1;
    
    private AcademicBody setAcademicBodyData(ResultSet academicBodyResult) throws SQLException{
        AcademicBody academicBody = new AcademicBody();
        academicBody.setKey(NOT_FOUND);
        
        if(academicBodyResult.next()){
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBody.setStatus(academicBodyResult.getInt("estado"));
        }
        
        return academicBody;
    }
    
    private List<AcademicBody> setAcademicBodyListData(ResultSet academicBodyResult) throws SQLException{
        List<AcademicBody> academicBodyList = new ArrayList<>();
        
        while(academicBodyResult.next()){
            AcademicBody academicBody = new AcademicBody();
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBody.setStatus(academicBodyResult.getInt("estado"));
            academicBodyList.add(academicBody);
        }
        
        return academicBodyList;
    }
    
    private AcademicBodyMember setAcademicBodyMemberData(ResultSet memberResult) throws SQLException{
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        academicBodyMember.setIdAcademicBodyMember(NOT_FOUND_INT);
        
        if(memberResult.next()){
            academicBodyMember.setIdAcademicBodyMember(memberResult.getInt("idProfesorCuerpoAcademico"));
            academicBodyMember.setRole(memberResult.getString("rol"));
            academicBodyMember.setId(memberResult.getInt("idUsuarioProfesor"));
            academicBodyMember.setHonorificTitle(memberResult.getString("honorifico"));
            academicBodyMember.setName(memberResult.getString("nombre"));
            academicBodyMember.setLastName(memberResult.getString("apellido"));
            academicBodyMember.setEMail(memberResult.getString("correo"));
        }
        
        return academicBodyMember;
    }
    
    private List<AcademicBodyMember> setAcademicBodyMemberListData(ResultSet memberResult) throws SQLException{
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();
        
        while(memberResult.next()){
            AcademicBodyMember academicBodyMember = new AcademicBodyMember();
            academicBodyMember.setIdAcademicBodyMember(memberResult.getInt("idProfesorCuerpoAcademico"));
            academicBodyMember.setRole(memberResult.getString("rol"));
            academicBodyMember.setId(memberResult.getInt("idUsuarioProfesor"));
            academicBodyMember.setHonorificTitle(memberResult.getString("honorifico"));
            academicBodyMember.setName(memberResult.getString("nombre"));
            academicBodyMember.setLastName(memberResult.getString("apellido"));
            academicBodyMember.setEMail(memberResult.getString("correo"));
            academicBodyMemberList.add(academicBodyMember);
        }
        
        return academicBodyMemberList;
    }

    @Override
    public int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_ACADEMIC_BODY);
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        statement.setInt(3, academicBody.getStatus());
        
        result = statement.executeUpdate();
        
        return result;
    }

    @Override
    public AcademicBody getAcademicBody(String key) throws SQLException {
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY);        
        
        preparedStatement.setString(1, key);
        
        ResultSet academicBodyResult = preparedStatement.executeQuery();
        AcademicBody academicBody = setAcademicBodyData(academicBodyResult);
        
        DataBaseManager.closeConnection();
        
        return academicBody;
    }
    
    @Override
    public List<AcademicBodyMember> getAcademicBodyMembers(String key) throws SQLException{
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY_MEMBER_BY_ROLE);
                
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, MEMBER_ROLE);
        
        ResultSet memberResult = preparedStatement.executeQuery();
        List<AcademicBodyMember> academicBodyMemberList = setAcademicBodyMemberListData(memberResult);
        
        DataBaseManager.closeConnection();
        return academicBodyMemberList;
    }
    
    @Override
    public AcademicBodyMember getAcademicBodyResponsible(String key) throws SQLException{
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY_MEMBER_BY_ROLE);
                       
        preparedStatement.setString(1, key);
        preparedStatement.setString(2, RESPONSIBLE_ROLE);
        
        ResultSet responsibleResult = preparedStatement.executeQuery();
        AcademicBodyMember responsible = setAcademicBodyMemberData(responsibleResult);
        
        return responsible;
    }
    
    @Override
    public List<AcademicBody> getAllAcademicBody() throws SQLException{
        Statement statement = DataBaseManager.getConnection().createStatement();
        
        ResultSet academicBodyResult = statement.executeQuery(GET_ALL_ACADEMIC_BODY);
        List<AcademicBody> academicBodyList = setAcademicBodyListData(academicBodyResult);
        
        DataBaseManager.closeConnection();
        
        return academicBodyList;
    }
    
    @Override
    // The parameter include key because it could be updated
    public int updateAcademicBody (String key, AcademicBody academicBody) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACADEMIC_BODY);       
        
        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        statement.setString(3, key);
        
        result = statement.executeUpdate();
        
        return result;
    }
    
    // The parameter include key because it could be updated
    @Override
    public int updateAcademicBodyTransaction(String key, AcademicBody academicBody, List<AcademicBodyMember> oldMembers) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            for(int i = 0; i < oldMembers.size(); i++){
                result += deleteAcademicBodyMember(key, oldMembers.get(i));
            }

            result += updateAcademicBody(key, academicBody);

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
    public int deleteAcademicBody (String key) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_ACADEMIC_BODY);
        
        statement.setString(1, key);
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public int addAcademicBodyMember(AcademicBodyMember academicBodyMember, String academicBodyKey) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_ACADEMIC_BODY_MEMBER);

        statement.setString(1, academicBodyKey);
        statement.setInt(2, academicBodyMember.getId());
        statement.setString(3, academicBodyMember.getRole());

        result = statement.executeUpdate();

        return result;
    }
    
    @Override
    public int deleteAcademicBodyMember(String key, AcademicBodyMember academicBodyMember) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_ACADEMIC_BODY_MEMBER);
        
        statement.setString(1, key);
        statement.setInt(2, academicBodyMember.getId());
        
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
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROFESSOR_QUERY);

        statement.setString(1,email);

        ResultSet professorResult = statement.executeQuery();
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        
        academicBodyMember.setId(NOT_FOUND_INT);
        
        if(professorResult.next()){
            academicBodyMember.setEMail(professorResult.getString("correo"));
            academicBodyMember.setId(professorResult.getInt("idUsuarioProfesor"));
        }
        
        DataBaseManager.closeConnection();

        return academicBodyMember;
    }
    
    @Override
    public int getExistenceAcademicBody(String key) throws SQLException{
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(EXISTENCE_ACADEMIC_BODY);
        
        statement.setString(1, key);
        
        ResultSet academicBodyResult = statement.executeQuery();
        int result = VALUE_BY_DEFAULT;
        
        if(academicBodyResult.next()){
            result = academicBodyResult.getInt(1);
        }
        
        return result;
    }
    
    @Override
    public int updateAcademicBodyStatus(int status, String key) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACADEMIC_BODY_STATUS);
        
        statement.setInt(1, status);
        statement.setString(2, key);
        
        result = statement.executeUpdate();
        
        return result;
    }
}
