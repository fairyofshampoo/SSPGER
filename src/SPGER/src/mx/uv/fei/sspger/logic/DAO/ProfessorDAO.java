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

public class ProfessorDAO implements IProfessor {

    private final int ERROR = -1;
    private static final int VALUE_BY_DEFAULT = 0;
    private final String ADD_PROFESSOR_COMMAND = "insert into profesor(correo, nombre, apellido, numPersonal, honorifico, isAdmin) values(?,?,?,?,?,?)";
    private final String ADD_ACCESS_ACCOUNT_COMMAND = "insert into cuenta_acceso(correo, password, estado) values (?, SHA1(?), ?)";
    private final String GET_PROFESSOR_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE profesor.idUsuarioProfesor = ?";
    private final String GET_ALL_PROFESSORS_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo";
    private final String GET_PROFESSORS_BY_STATUS_QUERY = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE cuenta_acceso.estado = ?";
    private final String UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND = "UPDATE cuenta_acceso SET correo = ?, password = SHA1(?), estado = ? WHERE correo = ?";
    private final String UPDATE_ACCESS_ACCOUNT_COMMAND = "UPDATE cuenta_acceso SET correo = ?, estado = ? WHERE correo = ?";
    private final String UPDATE_PROFESSOR_COMMAND = "UPDATE profesor SET nombre = ?, apellido = ?, numPersonal = ?, honorifico = ?, isAdmin = ? WHERE correo = ?";
    private final String GET_DIRECTOR_BY_PROJECT = "SELECT * FROM profesor_anteproyecto NATURAL JOIN profesor WHERE profesor_anteproyecto.idAnteproyecto = ? AND rol = ?";
    private final String GET_COODIRECTORS_BY_PROJECT = "SELECT * FROM profesor_anteproyecto NATURAL JOIN profesor WHERE profesor_anteproyecto.idAnteproyecto = ? AND rol = ?";
    private final String SEARCH_PROFESSOR_BY_NAME = "SELECT * FROM cuenta_acceso INNER JOIN profesor ON cuenta_acceso.correo = profesor.correo WHERE CONCAT(profesor.nombre, ?, profesor.apellido) LIKE ?";
    private final String GET_ALL_DIRECTORS_BY_PROJECT_STATUS = "SELECT DISTINCT profesor.* FROM profesor INNER JOIN profesor_anteproyecto ON profesor.idUsuarioProfesor = profesor_anteproyecto.idUsuarioProfesor INNER JOIN anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.rol = ? AND anteproyecto.estadoAnteproyecto = ?";
    private final String GET_PROFESSOR_BY_COURSE = "SELECT * FROM profesor INNER JOIN curso ON profesor.idUsuarioProfesor = curso.idUsuarioProfesor WHERE idCurso = ?";
    private final String GET_PROFESSORS_NAME_BY_COURSE = "SELECT profesor.honorifico ,profesor.nombre, profesor.apellido, profesor.idUsuarioProfesor FROM sspger.profesor INNER JOIN sspger.curso ON profesor.idUsuarioProfesor = curso.idUsuarioProfesor WHERE idCurso = ?";
    private final String GET_AVAILABLE_PROFESSORS_NAME = "SELECT honorifico, nombre, apellido, idUsuarioProfesor FROM profesor INNER JOIN cuenta_acceso ON cuenta_acceso.correo = profesor.correo WHERE cuenta_acceso.estado = 1";
    private final String GET_ACTIVE_PROFESSORS_NOT_SELECTED = "SELECT profesor.idUsuarioProfesor, profesor.correo, profesor.nombre, profesor.apellido, profesor.honorifico FROM sspger.profesor INNER JOIN sspger.cuenta_acceso ON profesor.correo = cuenta_acceso.correo"
        + "WHERE NOT EXISTS (SELECT 1 FROM sspger.profesor_cuerpo_academico WHERE profesor_cuerpo_academico.idUsuarioProfesor = profesor.idUsuarioProfesor AND profesor_cuerpo_academico.idCuerpoAcadémico = ?) AND cuenta_acceso.estado = ?";
    private final String DIRECTOR_ROLE = "Director";
    private final String CODIRECTOR_ROLE = "Codirector";

    /**
     *
     * @param professor to save new professor in the database
     * @return 2 if it's successful, 0 if it's not and -1 if there's an
     * exception
     * @throws SQLException if there's an error in the database.
     */
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

            if (response != VALUE_BY_DEFAULT) {
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
        } catch (SQLException sqlException) {
            response = ERROR;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param academicBodyKey to find all the active professors not in a
     * selected academic body
     * @return an empty list if there's no result or a list full of professors
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Professor> getProfessorsNotSelectedByAcademicBody(String academicBodyKey) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ACTIVE_PROFESSORS_NOT_SELECTED);
        int activeStatus = 1;

        statement.setString(1, academicBodyKey);
        statement.setInt(2, activeStatus);

        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery();

        while (professorResult.next()) {
            Professor professor = new Professor();

            professor.setEMail(professorResult.getString("correo"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professorList.add(professor);
        }

        DataBaseManager.closeConnection();

        return professorList;
    }

    private Professor setUserProfessorData(ResultSet professorResult) throws SQLException {
        Professor professor = new Professor();
        professor.setId(ERROR);

        if (professorResult.next()) {
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

    private Professor setProfessorData(ResultSet professorResult) throws SQLException {
        Professor professor = new Professor();
        professor.setId(ERROR);

        if (professorResult.next()) {
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

    private List<Professor> setUserProfessorList(ResultSet professorResult) throws SQLException {
        List<Professor> professorList = new ArrayList<>();
        while (professorResult.next()) {
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

    private List<Professor> setProfessorList(ResultSet professorResult) throws SQLException {
        List<Professor> professorList = new ArrayList<>();
        while (professorResult.next()) {
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

    /**
     *
     * @param idUser to get a professor from the database
     * @return professor with id = -1 if there's no results or a professor
     * succesfully
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Professor getProfessor(int idUser) throws SQLException {
        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(GET_PROFESSOR_QUERY);

        professorStatement.setInt(1, idUser);

        Professor professor = new Professor();
        ResultSet professorResult = professorStatement.executeQuery();
        professor = setUserProfessorData(professorResult);

        DataBaseManager.closeConnection();

        return professor;
    }

    /**
     *
     * @return all the existing professors in the database
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Professor> getAllProfessors() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();

        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery(GET_ALL_PROFESSORS_QUERY);
        professorList = setUserProfessorList(professorResult);

        DataBaseManager.closeConnection();

        return professorList;
    }

    /**
     *
     * @return a list of all the active professors in the database
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Professor> getActiveProfessors() throws SQLException {
        Statement statement = DataBaseManager.getConnection().createStatement();

        List<Professor> professorList = new ArrayList<>();
        ResultSet professorResult = statement.executeQuery(GET_AVAILABLE_PROFESSORS_NAME);

        while (professorResult.next()) {
            Professor professor = new Professor();
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
            professorList.add(professor);
        }

        DataBaseManager.closeConnection();

        return professorList;
    }

    /**
     *
     * @param status to search a list of professors by his status
     * @return an empty list if there's no result or a list full of professors
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param eMail to update an existing professor in the database
     * @param professor to update the professor data
     * @return 2 if it's successful or 0 if it's not
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateProfessorTransaction(String eMail, Professor professor) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_COMMAND);

            accountStatement.setString(1, professor.getEMail());
            accountStatement.setInt(2, professor.getStatus());
            accountStatement.setString(3, eMail);

            response = accountStatement.executeUpdate();

            if (response != VALUE_BY_DEFAULT) {
                response = response + updateProfessorData(professor);
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            response = ERROR;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    private int updateProfessorData(Professor professor) throws SQLException {
        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROFESSOR_COMMAND);

        professorStatement.setString(1, professor.getName());
        professorStatement.setString(2, professor.getLastName());
        professorStatement.setString(3, professor.getPersonalNumber());
        professorStatement.setString(4, professor.getHonorificTitle());
        professorStatement.setInt(5, professor.getIsAdmin());
        professorStatement.setString(6, professor.getEMail());

        return professorStatement.executeUpdate();
    }

    /**
     *
     * @param eMail to update an existing professor in the database
     * @param professor to update the professor data
     * @return 2 if it's successful or 0 if it's not
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateProfessorWithPasswordTransaction(String eMail, Professor professor) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement accountStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_ACCESS_ACCOUNT_WITH_PASSWORD_COMMAND);

            accountStatement.setString(1, professor.getEMail());
            accountStatement.setString(2, professor.getPassword());
            accountStatement.setInt(3, professor.getStatus());
            accountStatement.setString(4, eMail);

            response = accountStatement.executeUpdate();

            if (response != VALUE_BY_DEFAULT) {
                response = response + updateProfessorData(professor);
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            response = ERROR;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param projectId to get a director with that project registered
     * @return a professor with id -1 if there's no result
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param projectId to get all the professors with role of codirector in the
     * project from the database
     * @return an empty list if there's no result or a list full of professors
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Professor> getCoodirectorByProject(int projectId) throws SQLException {
        DataBaseManager.getConnection();
        List<Professor> coodirectors = new ArrayList<>();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_COODIRECTORS_BY_PROJECT);

        statement.setInt(1, projectId);
        statement.setString(2, CODIRECTOR_ROLE);

        ResultSet professorResult = statement.executeQuery();

        while (professorResult.next()) {
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

    /**
     *
     * @param name to get a list of professors with that name in the database
     * @return an empty list if there's no result or a list full of professors
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param courseId to get the professor of a course in the database
     * @return a professor with id = -1 if there's no result
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param courseId to get specific data from the professor in a course
     * @return a professor with honorific title, name, last name and id if it's
     * sucessful or a professor with id = -1 if it's not
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Professor getProfessorsNameByCourse(String courseId) throws SQLException {

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROFESSORS_NAME_BY_COURSE);

        statement.setString(1, courseId);

        Professor professor = new Professor();
        ResultSet professorResult = statement.executeQuery();

        if (professorResult.next()) {
            professor.setHonorificTitle(professorResult.getString("honorifico"));
            professor.setName(professorResult.getString("nombre"));
            professor.setLastName(professorResult.getString("apellido"));
            professor.setId(professorResult.getInt("idUsuarioProfesor"));
        } else {
            professor.setId(ERROR);
        }

        DataBaseManager.closeConnection();

        return professor;
    }

    /**
     *
     * @param projectStatus to get the directors that has a project with that
     * status
     * @return an empty list if there's no result or a list full of professors
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Professor> getDirectorsPerProjectStatus(String projectStatus) throws SQLException {
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
