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
    private final int ERROR = -1;
    private static final int VALUE_BY_DEFAULT = 0;
    private final String ADD_PROFESSOR_COMMAND = "insert into profesor(correo, nombre, apellido, numPersonal, honorifico, isAdmin) values(?,?,?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_PROFESSOR_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE profesor.idUsuarioProfesor = ?";
    private final String GET_PROFESSOR_BY_EMAIL = "SELECT * FROM profesor WHERE correo = ?";
    private final String GET_PROFESSOR_BY_ID = "SELECT * FROM profesor WHERE idUsuarioProfesor = ?";
    private final String GET_ALL_PROFESSORS_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo";
    private final String GET_PROFESSORS_BY_STATUS_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, estado = ? WHERE correo = ?";
    private final String UPDATE_PROFESSOR_COMMAND = "UPDATE profesor SET nombre = ?, apellido = ?, numPersonal = ?, honorifico = ?, isAdmin = ? WHERE correo = ?";
    private final String GET_DIRECTOR_BY_PROJECT = "SELECT * FROM profesor_anteproyecto NATURAL JOIN profesor WHERE profesor_anteproyecto.idAnteproyecto = ? AND rol = ?";
    private final String GET_COODIRECTORS_BY_PROJECT = "SELECT * FROM profesor_anteproyecto NATURAL JOIN profesor WHERE profesor_anteproyecto.idAnteproyecto = ? AND rol = ?";
    private final String SEARCH_PROFESSOR_BY_NAME = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE CONCAT(profesor.nombre, ?, profesor.apellido) LIKE ?";
    private final String GET_ALL_DIRECTORS_BY_PROJECT_STATUS = "SELECT DISTINCT profesor.idUsuarioProfesor, profesor.nombre, profesor.apellido, profesor.honorifico FROM profesor INNER JOIN profesor_anteproyecto ON profesor.idUsuarioProfesor = profesor_anteproyecto.idUsuarioProfesor INNER JOIN anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.rol = ? AND anteproyecto.estadoAnteproyecto = ?";
    private final String GET_PROFESSOR_BY_COURSE = "SELECT * FROM profesor INNER JOIN curso ON profesor.idUsuarioProfesor = curso.idUsuarioProfesor WHERE idCurso = ?";
    private final String DIRECTOR_ROLE = "Director";
    private final String CODIRECTOR_ROLE = "Codirector";
    
    @Override
    public int addProfessorTransaction(Professor professor) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(ADD_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, professor.getEMail());
            accountStatement.setString(2, professor.getPassword());
            accountStatement.setInt(3, professor.getStatus());
        
            response = accountStatement.executeUpdate();
        
            if(response != VALUE_BY_DEFAULT){
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
            response = ERROR;
            DataBaseManager.getConnection().rollback();
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }
    
    private Professor setUserProfessorData(ResultSet professorResult) throws SQLException{
        Professor professor = new Professor();
        professor.setId(ERROR);
        
        if(professorResult.next()){
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setStatus(professorResult.getInt("estado"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
        }
        return professor;
    }
    
    private Professor setProfessorData(ResultSet professorResult) throws SQLException{
        Professor professor = new Professor();
        professor.setId(ERROR);
        
        if(professorResult.next()){
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
        }
        return professor;
    }
    
    private List<Professor> setUserProfessorList(ResultSet professorResult) throws SQLException{
        List <Professor> professorList = new ArrayList<>();
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setStatus(professorResult.getInt("estado"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
            professorList.add(professor);
        }
        return professorList;
    }
    
    private List<Professor> setProfessorList(ResultSet professorResult) throws SQLException{
        List <Professor> professorList = new ArrayList<>();
        while(professorResult.next()){
            Professor professor = new Professor();
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setIsAdmin(professorResult.getInt("isAdmin"));
            professorList.add(professor);
        }
        return professorList;
    }
    
    @Override
    public Professor getProfessor(int idUser) throws SQLException {
        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(GET_PROFESSOR_QUERY);

        professorStatement.setInt(1,idUser);

        Professor professor = new Professor();
        ResultSet professorResult = professorStatement.executeQuery();
        professor = setUserProfessorData(professorResult);
        
        DataBaseManager.closeConnection();

        return professor;
    }

    @Override
    public List<Professor> getAllProfessors() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();
        
        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery(GET_ALL_PROFESSORS_QUERY);
        professorList = setUserProfessorList(professorResult);
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }
    
    @Override
    public List<Professor> getProfessorsByStatus(int status) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROFESSORS_BY_STATUS_QUERY);

        statement.setInt(1, status);
        
        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery();
        professorList = setUserProfessorList(professorResult);
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }

    @Override
    public int updateProfessorTransaction(String eMail, Professor professor) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);
        
            accountStatement.setString(1, professor.getEMail());
            accountStatement.setInt(2, professor.getStatus());
            accountStatement.setString(3, eMail);
        
            response = accountStatement.executeUpdate();
        
            if(response != VALUE_BY_DEFAULT){
                response = response + updateProfessorData(professor);
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            response = ERROR;
            DataBaseManager.getConnection().rollback();
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }
    
    private int updateProfessorData(Professor professor) throws SQLException{
        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROFESSOR_COMMAND);
        
        professorStatement.setString(1, professor.getName());
        professorStatement.setString(2, professor.getLastName());
        professorStatement.setString(3, professor.getPersonalNumber());
        professorStatement.setString(4, professor.getHonorificTitle());
        professorStatement.setInt(5, professor.getIsAdmin());
        professorStatement.setString(6, professor.getEMail());
        
        return professorStatement.executeUpdate();
    }

    @Override
    public int updateProfessorWithPasswordTransaction(String eMail, Professor professor) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try{
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND);
        
            accountStatement.setString(1, professor.getEMail());
            accountStatement.setString(2, professor.getPassword());
            accountStatement.setInt(3, professor.getStatus());
            accountStatement.setString(4, eMail);
        
            response = accountStatement.executeUpdate();
        
            if(response != VALUE_BY_DEFAULT){
                response = response + updateProfessorData(professor);
            }
        
            DataBaseManager.getConnection().commit();
        } catch (SQLException ex){
            response = ERROR;
            DataBaseManager.getConnection().rollback();
        } finally{
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    

    @Override
    public Professor getDirectorByProject(int projectId) throws SQLException {
        Professor professor = new Professor();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_DIRECTOR_BY_PROJECT);
        
        statement.setInt(1, projectId);
        statement.setString(2, DIRECTOR_ROLE);
        
        ResultSet professorResult = statement.executeQuery();
        
        professor = setProfessorData(professorResult);
     
        return professor;
    }
    
    @Override
    public List<Professor> getCoodirectorByProject (int projectId) throws SQLException {
        DataBaseManager.getConnection();
        List<Professor> coodirectors = new ArrayList<>();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COODIRECTORS_BY_PROJECT);
        
        statement.setInt(1, projectId);
        statement.setString(2, CODIRECTOR_ROLE);
        
        ResultSet professorResult = statement.executeQuery();

        while(professorResult.next()){
            Professor professor = new Professor();
        
            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setPersonalNumber(professorResult.getString("numPersonal"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            coodirectors.add(professor);
        }
                    
        return coodirectors;
    }
    
    @Override
    public List<Professor> searchProfessorsbyName(String name) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_PROFESSOR_BY_NAME);
        String blankSpace = " ";
        String searchName = name + "%";
        
        statement.setString(1, blankSpace);
        statement.setString(2, searchName);
        
        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery();
        professorList = setUserProfessorList(professorResult);
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }
    
    @Override
    public Professor getProfessor(String email) throws SQLException {
        String query = GET_PROFESSOR_BY_EMAIL;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1,email);
        
        Professor professor = new Professor();
        ResultSet professorResult = statement.executeQuery();
        professor = setProfessorData(professorResult);
        
        DataBaseManager.closeConnection();

        return professor;
    }

    @Override
    public Professor getProfessorByCourse(String courseId) throws SQLException {
        String query = GET_PROFESSOR_BY_COURSE;

        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setString(1, courseId);
        
        Professor professor = new Professor();
        ResultSet professorResult = statement.executeQuery();
        professor = setProfessorData(professorResult);
        
        DataBaseManager.closeConnection();

        return professor;
    }
 
    @Override
    public Professor getProfessorById(int professorId) throws SQLException {
        String query = GET_PROFESSOR_BY_ID;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setInt(1,professorId);

        Professor professor = new Professor();
        ResultSet professorResult = statement.executeQuery();
        professor = setProfessorData(professorResult);
        
        DataBaseManager.closeConnection();

        return professor;
    }
    
    @Override
    public List<Professor> getDirectorsPerProjectStatus(String projectStatus) throws SQLException{
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ALL_DIRECTORS_BY_PROJECT_STATUS);

        statement.setString(1, DIRECTOR_ROLE);
        statement.setString(2, projectStatus);
        
        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery();
        professorList = setProfessorList(professorResult);
        
        DataBaseManager.closeConnection();
        
        return professorList;
    }
}
