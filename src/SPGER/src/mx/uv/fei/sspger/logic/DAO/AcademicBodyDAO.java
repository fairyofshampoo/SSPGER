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
    private final String GET_ACADEMIC_BODIES_FROM_PROFESSOR = "SELECT nombre, idCuerpoAcademico FROM sspger.profesor_cuerpo_academico INNER JOIN sspger.cuerpo_academico ON idCuerpoAcademico = idCuerpoAcadémico WHERE idUsuarioProfesor = ?";
    private final String UPDATE_ACADEMIC_BODY = "UPDATE cuerpo_academico SET idCuerpoAcademico = ?, nombre = ? WHERE idCuerpoAcademico = ?";
    private final String EXISTENCE_ACADEMIC_BODY = "SELECT COUNT(*) FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
    private final String UPDATE_ACADEMIC_BODY_STATUS = "UPDATE cuerpo_academico SET estado = ? WHERE idCuerpoAcademico = ?";
    private final String DELETE_ACADEMIC_BODY_MEMBER = "DELETE FROM profesor_cuerpo_academico WHERE idProfesorCuerpoAcademico = ?";
    private final String GET_ALL_ACADEMIC_BODY_MEMBER = "SELECT profesor_cuerpo_academico.idProfesorCuerpoAcademico, profesor_cuerpo_academico.idUsuarioProfesor, profesor_cuerpo_academico.rol, profesor.nombre, profesor.apellido, profesor.honorifico, profesor.correo"
        + " FROM profesor_cuerpo_academico INNER JOIN profesor ON profesor.idUsuarioProfesor = profesor_cuerpo_academico.idUsuarioProfesor WHERE idCuerpoAcadémico = ?";
    private final String GET_MEMBER_BY_ID_PROFESSOR = "SELECT profesor_cuerpo_academico.idProfesorCuerpoAcademico, profesor_cuerpo_academico.idUsuarioProfesor, profesor_cuerpo_academico.rol, profesor.nombre, profesor.apellido, profesor.honorifico, profesor.correo"
        + " FROM profesor_cuerpo_academico INNER JOIN profesor ON profesor.idUsuarioProfesor = profesor_cuerpo_academico.idUsuarioProfesor WHERE idCuerpoAcadémico = ? AND profesor_cuerpo_academico.idUsuarioProfesor = ?";
    private final String TOTAL_RESPONSIBLE_PER_ACADEMIC_BODY = "SELECT COUNT(*) FROM profesor_cuerpo_academico WHERE rol = ? AND idCuerpoAcadémico = ?";
    private final String GET_ALL_ACADEMIC_BODY_AVAILABLE = "SELECT * FROM cuerpo_academico WHERE estado = 1";
    private final int VALUE_BY_DEFAULT = 0;
    private final int NOT_FOUND_INT = -1;
    private final String RESPONSIBLE_ROLE = "Responsable";
    private final String MEMBER_ROLE = "Miembro";

    private AcademicBody setAcademicBodyData(ResultSet academicBodyResult) throws SQLException {
        AcademicBody academicBody = new AcademicBody();

        if (academicBodyResult.next()) {
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBody.setStatus(academicBodyResult.getInt("estado"));
        }

        return academicBody;
    }

    private List<AcademicBody> setAcademicBodyListData(ResultSet academicBodyResult) throws SQLException {
        List<AcademicBody> academicBodyList = new ArrayList<>();

        while (academicBodyResult.next()) {
            AcademicBody academicBody = new AcademicBody();
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBody.setStatus(academicBodyResult.getInt("estado"));
            academicBodyList.add(academicBody);
        }

        return academicBodyList;
    }

    private AcademicBodyMember setAcademicBodyMemberData(ResultSet memberResult) throws SQLException {
        AcademicBodyMember academicBodyMember = new AcademicBodyMember();
        academicBodyMember.setIdAcademicBodyMember(NOT_FOUND_INT);

        if (memberResult.next()) {
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

    private List<AcademicBodyMember> setAcademicBodyMemberListData(ResultSet memberResult) throws SQLException {
        List<AcademicBodyMember> academicBodyMemberList = new ArrayList<>();

        while (memberResult.next()) {
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

    private int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_ACADEMIC_BODY);

        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        statement.setInt(3, academicBody.getStatus());

        result = statement.executeUpdate();

        return result;
    }

    /**
     *
     * @param academicBody to setter the academic body data that will be added
     * @return an int (-1) if there's an error with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int addAcademicBodyTransaction(AcademicBody academicBody) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            result = addAcademicBody(academicBody);

            for (int i = 0; i < academicBody.getMember().size(); i++) {
                PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_ACADEMIC_BODY_MEMBER);

                statement.setString(1, academicBody.getKey());
                statement.setInt(2, academicBody.getMember().get(i).getId());
                statement.setString(3, academicBody.getMember().get(i).getRole());
                result += statement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            result = NOT_FOUND_INT;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return result;
    }

    /**
     *
     * @param memberList is the list of the academic body members to be added
     * @param academicBodyKey to refer the academic body where the members will
     * be added
     * @return an int (-1) if there's an error with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int addAcademicBodyMember(List<AcademicBodyMember> memberList, String academicBodyKey) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            for (int i = 0; i < memberList.size(); i++) {
                PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_ACADEMIC_BODY_MEMBER);

                statement.setString(1, academicBodyKey);
                statement.setInt(2, memberList.get(i).getId());
                statement.setString(3, memberList.get(i).getRole());

                result += statement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            result = NOT_FOUND_INT;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return result;
    }

    /**
     *
     * @param memberList is the list of the academic body members to be deleted
     * @return an int (-1) if there's an error with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int deleteAcademicBodyMember(List<AcademicBodyMember> memberList) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            for (int i = 0; i < memberList.size(); i++) {
                PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_ACADEMIC_BODY_MEMBER);

                statement.setInt(1, memberList.get(i).getIdAcademicBodyMember());

                result += statement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            result = NOT_FOUND_INT;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return result;
    }

    /**
     *
     * @param key to refer the academic body before the update
     * @param academicBody the new academic body data
     * @return an int (1) if the update it's success, (0) if it's not
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int updateAcademicBody(String key, AcademicBody academicBody) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACADEMIC_BODY);

        statement.setString(1, academicBody.getKey());
        statement.setString(2, academicBody.getName());
        statement.setString(3, key);

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     *
     * @param key to indicate the academic body to return
     * @return the academic body, if there's an error return the academic body
     * member with the id setted in -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public AcademicBody getAcademicBody(String key) throws SQLException {
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY);

        preparedStatement.setString(1, key);

        ResultSet academicBodyResult = preparedStatement.executeQuery();
        AcademicBody academicBody = setAcademicBodyData(academicBodyResult);

        DataBaseManager.closeConnection();

        return academicBody;
    }

    /**
     *
     * @param key to refer the academic body where the members are registered
     * @return a list of academic body members with the role of member
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<AcademicBodyMember> getAcademicBodyMembers(String key) throws SQLException {
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY_MEMBER_BY_ROLE);

        preparedStatement.setString(1, key);
        preparedStatement.setString(2, MEMBER_ROLE);

        ResultSet memberResult = preparedStatement.executeQuery();
        List<AcademicBodyMember> academicBodyMemberList = setAcademicBodyMemberListData(memberResult);

        DataBaseManager.closeConnection();

        return academicBodyMemberList;
    }

    /**
     *
     * @param key to refer the academic body where the responsible is registered
     * @return the responsible of the academic body, if there's an error return
     * the academic body member with the id setted in -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public AcademicBodyMember getAcademicBodyResponsible(String key) throws SQLException {
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY_MEMBER_BY_ROLE);

        preparedStatement.setString(1, key);
        preparedStatement.setString(2, RESPONSIBLE_ROLE);

        ResultSet responsibleResult = preparedStatement.executeQuery();
        AcademicBodyMember responsible = setAcademicBodyMemberData(responsibleResult);

        DataBaseManager.closeConnection();

        return responsible;
    }

    /**
     *
     * @param key to refer the academic body where all the members are
     * registered (no matter the role)
     * @return a list of academic body member in a certain academic body
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<AcademicBodyMember> getAllAcademicBodyMembers(String key) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ALL_ACADEMIC_BODY_MEMBER);

        statement.setString(1, key);

        ResultSet result = statement.executeQuery();
        List<AcademicBodyMember> academicBodyMemberList = setAcademicBodyMemberListData(result);

        DataBaseManager.closeConnection();

        return academicBodyMemberList;
    }

    /**
     *
     * @return a list of all academic bodies
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<AcademicBody> getAllAcademicBody() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();

        ResultSet academicBodyResult = statement.executeQuery(GET_ALL_ACADEMIC_BODY);
        List<AcademicBody> academicBodyList = setAcademicBodyListData(academicBodyResult);

        DataBaseManager.closeConnection();

        return academicBodyList;
    }

    /**
     *
     * @param key to find an academic body with the same key
     * @return an int that refers to the total of academic body that has the
     * same key, return -1 if there's a problem
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int getExistenceAcademicBody(String key) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(EXISTENCE_ACADEMIC_BODY);

        statement.setString(1, key);

        ResultSet academicBodyResult = statement.executeQuery();
        int result = NOT_FOUND_INT;

        if (academicBodyResult.next()) {
            result = academicBodyResult.getInt(1);
        }

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     *
     * @param status to refer the new status of the academic body (1 if it's
     * active or 0 if it's not)
     * @param key to refer the academic body that will be updated
     * @return an int (1) if the update it's success, (0) if it's not
     * @throws SQLException if there's an error with the database
     */
    @Override
    public int updateAcademicBodyStatus(int status, String key) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACADEMIC_BODY_STATUS);

        statement.setInt(1, status);
        statement.setString(2, key);

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     *
     * @param key to refer the academic body to find a responsible assigned
     * @return an int that refers to the total of responsible registered in the
     * academic body, return -1 if there's a problem
     * @throws SQLException if there's an error with the database
     */
    @Override
    public int responsibleExistence(String key) throws SQLException {
        int response = NOT_FOUND_INT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(TOTAL_RESPONSIBLE_PER_ACADEMIC_BODY);

        statement.setString(1, RESPONSIBLE_ROLE);
        statement.setString(2, key);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            response = result.getInt(1);
        }

        DataBaseManager.closeConnection();

        return response;
    }

    /**
     *
     * @return a list of academic bodies with the active status
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<AcademicBody> getAcademicBodiesActive() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();

        ResultSet academicBodyResult = statement.executeQuery(GET_ALL_ACADEMIC_BODY_AVAILABLE);
        List<AcademicBody> academicBodyList = setAcademicBodyListData(academicBodyResult);

        DataBaseManager.closeConnection();

        return academicBodyList;
    }

    /**
     *
     * @param idProfessor to get the academic body member by the professor
     * @param academicBodyKey to make reference to the academic body
     * @return the academic body member, if there's an error return the academic
     * body member with the id setted in -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public AcademicBodyMember getMemberPerIdProfessor(int idProfessor, String academicBodyKey) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_MEMBER_BY_ID_PROFESSOR);

        statement.setString(1, academicBodyKey);
        statement.setInt(2, idProfessor);

        ResultSet result = statement.executeQuery();
        AcademicBodyMember academicBodyMember = setAcademicBodyMemberData(result);

        DataBaseManager.closeConnection();
        return academicBodyMember;
    }

    /**
     *
     * @param professorId to get the academic bodies from a professor
     * @return a list of academic bodies
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<AcademicBody> getAcademicBodiesFromProfessor(int professorId) throws SQLException {
        List<AcademicBody> professorAcademicBodies = new ArrayList<>();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODIES_FROM_PROFESSOR);
        statement.setInt(1, professorId);

        ResultSet result = statement.executeQuery();

        while (result.next()) {
            AcademicBody academicBody = new AcademicBody();
            academicBody.setName(result.getString("nombre"));
            academicBody.setKey(result.getString("idCuerpoAcademico"));

            professorAcademicBodies.add(academicBody);
        }

        DataBaseManager.closeConnection();

        return professorAcademicBodies;
    }
}
