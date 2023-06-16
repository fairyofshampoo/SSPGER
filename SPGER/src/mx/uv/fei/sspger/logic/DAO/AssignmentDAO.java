package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.contracts.IAssingment;


public class AssignmentDAO implements IAssingment{
    
    private final String REGISTER_ASSIGNMENT = "INSERT INTO asignacion(idUsuarioProfesor, titulo, fechaInicia, fechaFin, fechaPublicacion, descripcion, idTrabajoRecepcional) values (?,?,?,?,?,?,?)";
    private final String GET_ASSIGNMENT_PER_RECEPTIONAL_WORK = "SELECT * FROM asignacion Where idTrabajoRecepcional= ?";
    private final String UPDATE_ASSIGNMENT = "UPDATE asignacion SET titulo = ?, fechaInicia = ?, fechaFin = ?, descripcion = ? WHERE idAsignacion = ?;";
    private final String DELETE_ASSIGNMENT = "DELETE FROM asignacion WHERE idAsignacion = ?";
    private final String GET_ASSIGNMENT_BY_ID = "SELECT * FROM asignacion WHERE idAsignacion = ?";
    private final String GET_ASSIGMENT_COUNT_PER_RECEPTIONAL_WORK = "SELECT COUNT(*) AS submissionCount FROM sspger.asignacion WHERE idTrabajoRecepcional = ?";
    private final int ERROR_IN_COUNT = -1;
    
    @Override
    public int registerAssignment(Assignment assignment, int idProfessor, int idReceptionalWork) throws SQLException {
        int result;        
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(REGISTER_ASSIGNMENT);
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        
        statement.setInt(1, idProfessor);
        statement.setString(2, assignment.getTitle());
        statement.setDate(3, assignment.getStartDate());
        statement.setDate(4, assignment.getDeadline());
        statement.setDate(5, currentDate);
        statement.setString(6, assignment.getDescription());
        statement.setInt(7, idReceptionalWork);

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public List<Assignment> getAssignmentsPerReceptionalWork(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGNMENT_PER_RECEPTIONAL_WORK);
        
        statement.setInt(1, idReceptionalWork);
        
        ResultSet assignmentResult = statement.executeQuery();
        List<Assignment> assignmentList = new ArrayList<>();
        
        while(assignmentResult.next()){
            Assignment assignment = new Assignment();
            
            assignment.setId(assignmentResult.getInt("idAsignacion"));
            assignment.setTitle(assignmentResult.getString("titulo"));
            assignment.setStartDate(assignmentResult.getDate("fechaInicia"));
            assignment.setDeadline(assignmentResult.getDate("fechaFin"));
            assignment.setPublicationDate(assignmentResult.getDate("fechaPublicacion"));
            assignment.setDescription(assignmentResult.getString("descripcion"));
            assignmentList.add(assignment);
        } 
        
        DataBaseManager.closeConnection();
        
        return assignmentList;
    }

    @Override
    public int updateAssignment(Assignment assignment) throws SQLException {
        int result;
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_ASSIGNMENT);
        
        statement.setString(1, assignment.getTitle());
        statement.setDate(2, assignment.getStartDate());
        statement.setDate(3, assignment.getDeadline());
        statement.setString(4, assignment.getDescription());
        statement.setInt(5, assignment.getId());

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public int deleteAssignment (Assignment assignment) throws SQLException{
        int result;
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(DELETE_ASSIGNMENT);
        
        statement.setInt(1, assignment.getId());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public Assignment getAssignmentById (int assignmentId) throws SQLException{
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGNMENT_BY_ID);
        
        statement.setInt(1, assignmentId);
        
        ResultSet assignmentResult = statement.executeQuery();
        Assignment assignment = new Assignment();
               
        if (assignmentResult.next()){
            assignment.setId(assignmentResult.getInt("idAsignacion"));
            assignment.setStartDate(assignmentResult.getDate("fechaInicia"));
            assignment.setPublicationDate(assignmentResult.getDate("fechaPublicacion"));
            assignment.setDeadline(assignmentResult.getDate("fechaFin"));
            assignment.setTitle(assignmentResult.getString("titulo"));
            assignment.setDescription(assignmentResult.getString("descripcion"));
            assignment.setIdProject(assignmentResult.getInt("idTrabajoRecepcional"));
            assignment.setProfessorId(assignmentResult.getInt("idUsuarioProfesor"));
        }
        
        DataBaseManager.closeConnection();
        
        return assignment;
    }

    @Override
    public int getCountAssignmentPerReceptionalWork(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ASSIGMENT_COUNT_PER_RECEPTIONAL_WORK);
        int quantityOfAssignments;
        
        statement.setInt(1, idReceptionalWork);
        
        ResultSet countResult = statement.executeQuery();
        
        if (countResult.next()){
            quantityOfAssignments = countResult.getInt("submissionCount");
        }
        else {
            quantityOfAssignments = ERROR_IN_COUNT;
        }
        
        return quantityOfAssignments;
    }
}
