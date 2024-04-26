package mx.uv.fei.sspger.logic.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.contracts.IReceptionalWork;

public class ReceptionalWorkDAO implements IReceptionalWork {

    private final int VALUE_BY_DEFAULT = 0;
    private final int RECEPTIONAL_WORK_ERROR_ID = 0;
    private final int NOT_FOUND_INT = -1;
    private final String STATUS_STUDENT_ACCEPTED = "ACEPTADO";
    private final String STATUS_STUDENT_POSTULED = "POSTULADO";
    private final String STATUS_CONCLUDED = "CONCLUIDO";
    private final String ABANDONED_RECEPTIONAL_WORK_STATUS = "ABANDONADO";
    private final String STATUS_PROJECT_VALIDATION = "VALIDADO";
    private String GET_RECEPTIONAL_WORK_BY_STATUS = "SELECT * FROM trabajo_recepcional WHERE idAnteproyecto = ? AND estado = ?";
    private final String ADD_STUDENT = "INSERT INTO estudiante_trabajo_recepcional(idEstudiante,idTrabajoRecepcional) VALUES (?,?)";
    private final String ADD_RECEPTIONAL_WORK = "INSERT INTO trabajo_recepcional(nombre,descripcion,modalidad,estado,resultados,idAnteproyecto) VALUES (?,?,?,?,?,?)";
    private final String UPDATE_STUDENT_STATUS = "UPDATE estudiante_anteproyecto SET estadoEstudiante = ? WHERE idEstudiante = ? AND idAnteproyecto = ?";
    private final String DELETE_STUDENT_BY_STATUS = "DELETE FROM estudiante_anteproyecto WHERE idEstudiante = ? AND estadoEstudiante = ?";
    private final String UPDATE_RECEPTIONAL_WORK = "UPDATE trabajo_recepcional SET nombre = ?, descripcion = ?, modalidad = ?, resultados = ? WHERE idTrabajoRecepcional = ?";
    private final String GET_RECEPTIONAL_WORK_BY_ID = "SELECT * FROM trabajo_recepcional WHERE idTrabajoRecepcional = ?";
    private final String GET_ACTIVE_RECEPTIONAL_WORK_BY_STUDENT = "SELECT nombre, descripcion, modalidad, idTrabajoRecepcional, idAnteproyecto FROM trabajo_recepcional NATURAL JOIN estudiante_trabajo_recepcional "
        + "WHERE idEstudiante = ? AND trabajo_recepcional.estado = 'VIGENTE'";
    private final String GET_RECEPTIONAL_WORK_INFO_WITH_COUNT = "SELECT nombre, modalidad, idTrabajoRecepcional, estado, COUNT(*) AS count FROM trabajo_recepcional"
        + " NATURAL JOIN estudiante_trabajo_recepcional WHERE idTrabajoRecepcional = ? GROUP BY nombre, modalidad, idTrabajoRecepcional, estado";
    private final String UPDATE_PROJECT_STATUS = "UPDATE anteproyecto SET estadoAnteproyecto = ? WHERE idAnteproyecto = ?";
    private final String UPDATE_RECEPTIONAL_WORK_STATUS = "UPDATE trabajo_recepcional SET estado = ? WHERE idTrabajoRecepcional = ?";
    private final String DELETE_STUDENT_BY_PROJECT = "DELETE FROM estudiante_anteproyecto WHERE idEstudiante = ? AND estadoEstudiante = ? AND idAnteproyecto = ?";
    private final String GET_RECEPTIONAL_WORKS_BY_PROFESSOR = "SELECT idTrabajoRecepcional FROM trabajo_recepcional NATURAL JOIN profesor_anteproyecto WHERE idUsuarioProfesor = ?";
    private final String GET_RECEPTIONAL_WORKS_BY_PROFESSOR_AND_STATE = "SELECT idTrabajoRecepcional FROM trabajo_recepcional NATURAL JOIN profesor_anteproyecto WHERE idUsuarioProfesor = ? AND estado = ?";
    private final String DELETE_STUDENT_IN_RECEPTIONAL_WORK = "DELETE FROM estudiante_trabajo_recepcional WHERE idEstudiante = ? AND idTrabajoRecepcional = ?";
    private final String GET_RECEPTIONAL_WORK_NAME = "SELECT nombre, idTrabajoRecepcional FROM trabajo_recepcional WHERE idTrabajoRecepcional = ?";

    /**
     * @param idReceptionalWork to search for a receptionalWork in the database
     * with this idReceptionalWork.
     * @return a receptionalWork with all its field if match found, a
     * receptionalWork with an error id if no match.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public ReceptionalWork getRecepetionalWorkById(int idReceptionalWork) throws SQLException {
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_BY_ID);

        statement.setInt(1, idReceptionalWork);

        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        if (receptionalWorkResult.next()) {
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
            receptionalWork.setResults(receptionalWorkResult.getString("resultados"));
        }

        DataBaseManager.closeConnection();

        return receptionalWork;
    }

    /**
     * @param receptionalWork to update a receptionalWork in the database.
     * @return an int (1) if succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateReceptionalWork(ReceptionalWork receptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_RECEPTIONAL_WORK);

        statement.setString(1, receptionalWork.getName());
        statement.setString(2, receptionalWork.getDescription());
        statement.setString(3, receptionalWork.getModality());
        statement.setString(4, receptionalWork.getResults());
        statement.setInt(5, receptionalWork.getIdReceptionalWork());

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     * @param idProject to search for a project in the database.
     * @param studentList to remove this students from the receptionalWork and
     * project objects in the database.
     * @param idReceptionalWork to search for a receptionalWork in the database.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int expellStudent(int idProject, List<Student> studentList, int idReceptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            for (int i = 0; i < studentList.size(); i++) {
                PreparedStatement statementStudentReceptionalWork = DataBaseManager.getConnection().prepareStatement(DELETE_STUDENT_IN_RECEPTIONAL_WORK);

                statementStudentReceptionalWork.setInt(1, studentList.get(i).getId());
                statementStudentReceptionalWork.setInt(2, idReceptionalWork);

                result += statementStudentReceptionalWork.executeUpdate();

                PreparedStatement statementStudentProject = DataBaseManager.getConnection().prepareStatement(UPDATE_STUDENT_STATUS);

                statementStudentProject.setString(1, STATUS_STUDENT_POSTULED);
                statementStudentProject.setInt(2, studentList.get(i).getId());
                statementStudentProject.setInt(3, idProject);

                result += statementStudentProject.executeUpdate();
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
     * @param studentList to add this students to the receptionalWork object in
     * the database.
     * @param idReceptionalWork too search for a receptionalWork in the
     * database.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int addStudentToReceptionalWork(List<Student> studentList, int idReceptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        DataBaseManager.getConnection();

        for (int i = 0; i < studentList.size(); i++) {
            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_STUDENT);

            statement.setInt(1, studentList.get(i).getId());
            statement.setInt(2, idReceptionalWork);

            result = statement.executeUpdate();
        }

        return result;
    }

    /**
     * Delete all students from the receptionalWork and change its state to
     * abandoned in the database.
     *
     * @param studentList to delete this students from the receptionalWork
     * object in the database.
     * @param receptionalWork too search for a receptionalWork in the database.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int abandoneReceptionalWork(List<Student> studentList, ReceptionalWork receptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            result += changeReceptionalWorkStatus(ABANDONED_RECEPTIONAL_WORK_STATUS, receptionalWork.getIdReceptionalWork());

            PreparedStatement statementProjectUpdate = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_STATUS);

            statementProjectUpdate.setString(1, STATUS_PROJECT_VALIDATION);
            statementProjectUpdate.setInt(2, receptionalWork.getIdProject());
            result += statementProjectUpdate.executeUpdate();

            for (int i = 0; i < studentList.size(); i++) {
                PreparedStatement statementStudentProject = DataBaseManager.getConnection().prepareStatement(DELETE_STUDENT_BY_PROJECT);
                statementStudentProject.setInt(1, studentList.get(i).getId());
                statementStudentProject.setString(2, STATUS_STUDENT_ACCEPTED);
                statementStudentProject.setInt(3, receptionalWork.getIdProject());
                result += statementStudentProject.executeUpdate();
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

    private int changeReceptionalWorkStatus(String status, int idReceptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_RECEPTIONAL_WORK_STATUS);

        statement.setString(1, status);
        statement.setInt(2, idReceptionalWork);

        result = statement.executeUpdate();

        return result;
    }

    /**
     * Change the state of a receptionalWork to concluded in the database.
     *
     * @param receptionalWork too search for a receptionalWork in the database.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int concludeReceptionalWork(ReceptionalWork receptionalWork) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            result += changeReceptionalWorkStatus(STATUS_CONCLUDED, receptionalWork.getIdReceptionalWork());

            PreparedStatement statementProjectUpdate = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_STATUS);

            statementProjectUpdate.setString(1, STATUS_CONCLUDED);
            statementProjectUpdate.setInt(2, receptionalWork.getIdProject());
            result += statementProjectUpdate.executeUpdate();

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
     * @param idStudent to search for the active receptionalWork of a student in
     * the database.
     * @return a receptionalWork with all its fields, except space, if a match
     * found or a receptionalWork with an error id if no match.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public ReceptionalWork getActiveReceptionalWorkByStudent(int idStudent) throws SQLException {
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ACTIVE_RECEPTIONAL_WORK_BY_STUDENT);

        statement.setInt(1, idStudent);

        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        if (receptionalWorkResult.next()) {
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
        } else {
            receptionalWork.setIdReceptionalWork(RECEPTIONAL_WORK_ERROR_ID);
        }

        DataBaseManager.closeConnection();

        return receptionalWork;
    }

    /**
     * @param idReceptionalWork to search for a receptionalWork with this id in
     * the database.
     * @return a receptionalWork with all its fields if a match found or a
     * receptionalWork with an error id if no match.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public ReceptionalWork getReceptionalWorkWithNumberOfStudents(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_INFO_WITH_COUNT);

        statement.setInt(1, idReceptionalWork);

        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        if (receptionalWorkResult.next()) {
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setSpace(receptionalWorkResult.getInt("count"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
        } else {
            receptionalWork.setIdReceptionalWork(RECEPTIONAL_WORK_ERROR_ID);
        }

        DataBaseManager.closeConnection();

        return receptionalWork;
    }

    /**
     * @param idProject to search for receptionalWorks assigned to this project
     * in the database.
     * @param status the status of the receptionalWorks to be searched.
     * @return an int with the count of receptionalWorks found (>1) if matches
     * (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getTotalActiveReceptionalWorks(int idProject, String status) throws SQLException {
        String query = "SELECT COUNT(*) FROM trabajo_recepcional WHERE idAnteproyecto = ? AND estado = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

        statement.setInt(1, idProject);
        statement.setString(2, status);

        ResultSet totalReceptionalWorkResult = statement.executeQuery();
        int totalReceptionalWorks = VALUE_BY_DEFAULT;

        if (totalReceptionalWorkResult.next()) {
            totalReceptionalWorks = totalReceptionalWorkResult.getInt(1);
        }

        statement.close();

        DataBaseManager.closeConnection();

        return totalReceptionalWorks;
    }

    /**
     * @param idProfessor to search for receptionalWorks with this professor
     * assigned in the database.
     * @param state to search for receptionalWorks with this state.
     * @return a list of receptionalWorks if matches found, an empty list if
     * not.
     * @throws SQLException
     */
    @Override
    public List<ReceptionalWork> getReceptionalWorksByProfessorAndState(int idProfessor, String state) throws SQLException {
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORKS_BY_PROFESSOR_AND_STATE);

        statement.setInt(1, idProfessor);
        statement.setString(2, state);

        ResultSet receptionalWorkResult = statement.executeQuery();
        List<ReceptionalWork> professorReceptionalWorks = new ArrayList<>();

        while (receptionalWorkResult.next()) {
            ReceptionalWork receptionalWork = new ReceptionalWork();

            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            professorReceptionalWorks.add(receptionalWork);
        }

        DataBaseManager.closeConnection();

        return professorReceptionalWorks;
    }

    /**
     * @param idProfessor to search for receptionalWorks related to this
     * professor in the database.
     * @return a list of receptionalWorks if matches found, an empty list if
     * not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<ReceptionalWork> getReceptionalWorksByProfessor(int idProfessor) throws SQLException {
        DataBaseManager.getConnection();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORKS_BY_PROFESSOR);

        statement.setInt(1, idProfessor);

        ResultSet receptionalWorkResult = statement.executeQuery();
        List<ReceptionalWork> professorReceptionalWorks = new ArrayList<>();

        while (receptionalWorkResult.next()) {
            ReceptionalWork receptionalWork = new ReceptionalWork();

            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            professorReceptionalWorks.add(receptionalWork);
        }

        DataBaseManager.closeConnection();

        return professorReceptionalWorks;
    }

    /**
     * @param idProject to search for a receptionalWork with this projectId in
     * the database.
     * @param status tp search for a receptionalWork with this state in the
     * database.
     * @return a receptionalWork with all its fields except for the spaces, if
     * no match found a receptionalWork with error id.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public ReceptionalWork getReceptionalWorkByStatus(int idProject, String status) throws SQLException {

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_BY_STATUS);

        statement.setInt(1, idProject);
        statement.setString(2, status);

        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();

        if (receptionalWorkResult.next()) {
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
            receptionalWork.setResults(receptionalWorkResult.getString("resultados"));
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
        }

        DataBaseManager.closeConnection();

        return receptionalWork;
    }

    private int deleteStudentPostuledToProject(int idStudent) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_STUDENT_BY_STATUS);

        statement.setInt(1, idStudent);
        statement.setString(2, STATUS_STUDENT_POSTULED);

        result = statement.executeUpdate();
        return result;
    }

    private int updateStudentStatusToAccepted(int idStudent, int idProject) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_STUDENT_STATUS);

        statement.setString(1, STATUS_STUDENT_ACCEPTED);
        statement.setInt(2, idStudent);
        statement.setInt(3, idProject);

        result = statement.executeUpdate();
        return result;
    }

    /**
     * @param studentList to add this students to the receptionalWork in the
     * database.
     * @param idReceptionalWork the receptionalWork where the students will be
     * added.
     * @param idProject the project where the students need to be added.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int addStudentsToProjectTransaction(List<Student> studentList, int idReceptionalWork, int idProject) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            result += addStudentToReceptionalWork(studentList, idReceptionalWork);

            for (int i = 0; i < studentList.size(); i++) {
                result += updateStudentStatusToAccepted(studentList.get(i).getId(), idProject);
                result += deleteStudentPostuledToProject(studentList.get(i).getId());
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
     * @param studentList a list of students to add to the receptionalWork.
     * @param receptionalWork mew receptionalWork to be added to the database.
     * @param idProject the project related to the receptionalWork.
     * @return an int (>0) if the transaction was made succesfully (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int addReceptionalWorkTransaction(List<Student> studentList, ReceptionalWork receptionalWork, int idProject) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        int response = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);

            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(ADD_RECEPTIONAL_WORK, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, receptionalWork.getName());
            statement.setString(2, receptionalWork.getDescription());
            statement.setString(3, receptionalWork.getModality());
            statement.setString(4, receptionalWork.getState());
            statement.setString(5, receptionalWork.getResults());
            statement.setInt(6, receptionalWork.getIdProject());

            response = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            while (rs.next()) {
                response = rs.getInt(1);
            }

            if (response != VALUE_BY_DEFAULT) {
                result += addStudentToReceptionalWork(studentList, response);

                for (int i = 0; i < studentList.size(); i++) {
                    result += updateStudentStatusToAccepted(studentList.get(i).getId(), idProject);
                    result += deleteStudentPostuledToProject(studentList.get(i).getId());
                }
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
     * @param receptionalWorkId to search for a receptionalWork with this id.
     * @return a receptionalWork with the id and name fields, if no match found
     * a receptionalWork with an errorId.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public ReceptionalWork getReceptionalWorkName(int receptionalWorkId) throws SQLException {
        ReceptionalWork receptionalWork = new ReceptionalWork();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_NAME);

        statement.setInt(1, receptionalWorkId);

        ResultSet receptionalWorkResult = statement.executeQuery();

        if (receptionalWorkResult.next()) {
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
        } else {
            receptionalWork.setIdReceptionalWork(NOT_FOUND_INT);
        }

        return receptionalWork;
    }
}
