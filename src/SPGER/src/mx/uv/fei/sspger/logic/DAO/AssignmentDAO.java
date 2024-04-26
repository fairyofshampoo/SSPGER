package mx.uv.fei.sspger.logic.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.contracts.IAssingment;

public class AssignmentDAO implements IAssingment {

    private final String REGISTER_ASSIGNMENT = "INSERT INTO asignacion(idUsuarioProfesor, titulo, fechaInicia, fechaFin, fechaPublicacion, descripcion, idTrabajoRecepcional) values (?,?,?,?,?,?,?)";
    private final String GET_ASSIGNMENT_PER_RECEPTIONAL_WORK = "SELECT * FROM asignacion Where idTrabajoRecepcional= ?";
    private final String UPDATE_ASSIGNMENT = "UPDATE asignacion SET titulo = ?, fechaInicia = ?, fechaFin = ?, descripcion = ? WHERE idAsignacion = ?;";
    private final String DELETE_ASSIGNMENT = "DELETE FROM asignacion WHERE idAsignacion = ?";
    private final String GET_ASSIGNMENT_BY_ID = "SELECT * FROM asignacion WHERE idAsignacion = ?";
    private final String GET_ASSIGMENT_COUNT_PER_RECEPTIONAL_WORK = "SELECT COUNT(*) AS submissionCount FROM sspger.asignacion WHERE idTrabajoRecepcional = ?";
    private final int ERROR_IN_COUNT = -1;

    /**
     * @param assignment to add this assignment to the database.
     * @param idProfessor to add this idProfessor to the assignment
     * registration.
     * @return an int (1) if succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int registerAssignment(Assignment assignment, int idProfessor) throws SQLException {
        int result;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(REGISTER_ASSIGNMENT);

        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());

        java.util.Date utilStartDate = new java.util.Date(assignment.getStartDate().getTime());
        java.util.Date utilEndDate = new java.util.Date(assignment.getDeadline().getTime());

        java.sql.Timestamp timestampStartDate = new java.sql.Timestamp(utilStartDate.getTime());
        java.sql.Timestamp timestampEndDate = new java.sql.Timestamp(utilEndDate.getTime());

        statement.setInt(1, idProfessor);
        statement.setString(2, assignment.getTitle());
        statement.setTimestamp(3, timestampStartDate);
        statement.setTimestamp(4, timestampEndDate);
        statement.setTimestamp(5, currentTimestamp);
        statement.setString(6, assignment.getDescription());
        statement.setInt(7, assignment.getIdProject());

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     * @param idReceptionalWork to get all the assignments in the database
     * related to a receptionalWork
     * @return a list with assignments if matches were found, an empty list if
     * not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Assignment> getAssignmentsPerReceptionalWork(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGNMENT_PER_RECEPTIONAL_WORK);

        statement.setInt(1, idReceptionalWork);

        ResultSet assignmentResult = statement.executeQuery();
        List<Assignment> assignmentList = new ArrayList<>();

        while (assignmentResult.next()) {
            Assignment assignment = new Assignment();

            setTime(assignment, assignmentResult);
            assignment.setId(assignmentResult.getInt("idAsignacion"));
            assignment.setTitle(assignmentResult.getString("titulo"));
            assignment.setDescription(assignmentResult.getString("descripcion"));
            assignment.setIdSubmission(assignmentResult.getInt("idAvance"));
            assignmentList.add(assignment);
        }

        DataBaseManager.closeConnection();

        return assignmentList;
    }

    private void setTime(Assignment assignment, ResultSet assignmentResult) throws SQLException {
        java.util.Date startUtilDate = new java.util.Date(assignmentResult.getTimestamp("fechaInicia").getTime());
        java.util.Date endUtilDate = new java.util.Date(assignmentResult.getTimestamp("fechaFin").getTime());
        java.util.Date publicationUtilDate = new java.util.Date(assignmentResult.getTimestamp("fechaPublicacion").getTime());

        java.sql.Date startSqlDate = new java.sql.Date(startUtilDate.getTime());
        java.sql.Date endSqlDate = new java.sql.Date(endUtilDate.getTime());
        java.sql.Date publicationSqlDate = new java.sql.Date(publicationUtilDate.getTime());

        assignment.setStartDate(startSqlDate);
        assignment.setDeadline(endSqlDate);
        assignment.setPublicationDate(publicationSqlDate);
    }

    /**
     * @param assignment to modify an assignment in the database.
     * @return an int (1) if succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateAssignment(Assignment assignment) throws SQLException {
        int result;

        java.util.Date utilStartDate = new java.util.Date(assignment.getStartDate().getTime());
        java.util.Date utilEndDate = new java.util.Date(assignment.getDeadline().getTime());

        java.sql.Timestamp timestampStartDate = new java.sql.Timestamp(utilStartDate.getTime());
        java.sql.Timestamp timestampEndDate = new java.sql.Timestamp(utilEndDate.getTime());

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ASSIGNMENT);

        statement.setString(1, assignment.getTitle());
        statement.setTimestamp(2, timestampStartDate);
        statement.setTimestamp(3, timestampEndDate);
        statement.setString(4, assignment.getDescription());
        statement.setInt(5, assignment.getId());

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     * @param assignment to delete an assignment in the database.
     * @return an int (1) if succeded (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int deleteAssignment(Assignment assignment) throws SQLException {
        int result;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_ASSIGNMENT);

        statement.setInt(1, assignment.getId());

        result = statement.executeUpdate();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     * @param assignmentId to get an the information of an assignment from the
     * database.
     * @return an assignment with all its fields or an assignment with an
     * invalid id if no match found.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Assignment getAssignmentById(int assignmentId) throws SQLException {

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGNMENT_BY_ID);

        statement.setInt(1, assignmentId);

        ResultSet assignmentResult = statement.executeQuery();
        Assignment assignment = new Assignment();

        if (assignmentResult.next()) {
            setTime(assignment, assignmentResult);
            assignment.setId(assignmentResult.getInt("idAsignacion"));
            assignment.setTitle(assignmentResult.getString("titulo"));
            assignment.setDescription(assignmentResult.getString("descripcion"));
            assignment.setIdProject(assignmentResult.getInt("idTrabajoRecepcional"));
            assignment.setProfessorId(assignmentResult.getInt("idUsuarioProfesor"));
        }

        DataBaseManager.closeConnection();

        return assignment;
    }

    /**
     * @param idReceptionalWork to search for assignments from a
     * receptionalWork.
     * @return an int with the quantity of assignments related to that
     * receptionalWork.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getCountAssignmentPerReceptionalWork(int idReceptionalWork) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGMENT_COUNT_PER_RECEPTIONAL_WORK);
        int quantityOfAssignments;

        statement.setInt(1, idReceptionalWork);

        ResultSet countResult = statement.executeQuery();

        if (countResult.next()) {
            quantityOfAssignments = countResult.getInt("submissionCount");
        } else {
            quantityOfAssignments = ERROR_IN_COUNT;
        }

        DataBaseManager.closeConnection();

        return quantityOfAssignments;
    }
}
