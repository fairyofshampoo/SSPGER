package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.contracts.IAssingment;


public class AssignmentDAO implements IAssingment{
    
    @Override
    public int registerAssignment(Assignment assignment, int idProfessor, int idProject) throws SQLException {
        int result;
        String query = "INSERT INTO asignacion(idUsuarioProfesor, titulo, fechaInicia, fechaFin, fechaPublicacion, descripcion, idAnteproyecto) values (?,?,?,?,?,?,?)";
        
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        
        statement.setInt(1, idProfessor);
        statement.setString(2, assignment.getTitle());
        statement.setDate(3, assignment.getStartDate());
        statement.setDate(4, assignment.getDeadline());
        statement.setDate(5, currentDate);
        statement.setString(6, assignment.getDescription());
        statement.setInt(7, idProject);

        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }


    @Override
    public List<Assignment> getAssignmentsPerProject(int idProject) throws SQLException {
        String query = "Select * From asignacion Where idAnteproyecto= ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        
        ResultSet assignmentResult = statement.executeQuery();
        List<Assignment> assignmentList = new ArrayList<>();
        
        while(assignmentResult.next()){
            Assignment assignment = new Assignment();
            
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
        String query = "UPDATE asignacion SET titulo = ?,"
                + " fechaInicia = ?, fechaFin = ?, descripcion = ? WHERE idAsignacion = ?;";
        
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
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
        String query = "DELETE FROM asignacion WHERE idAsignacion = ?";
        
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, assignment.getId());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }
    
    @Override
    public Assignment getAssignmentById (int assignmentId) throws SQLException{
        Assignment assignment = new Assignment();
        String query = "SELECT * FROM asignacion WHERE idAsignacion = ?";
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, assignmentId);
        
        ResultSet assignmentResult = statement.executeQuery();
        assignmentResult.next();
        
        assignment.setId(assignmentResult.getInt("idAsignacion"));
        assignment.setStartDate(assignmentResult.getDate("fechaInicia"));
        assignment.setPublicationDate(assignmentResult.getDate("fechaPublicacion"));
        assignment.setDeadline(assignmentResult.getDate("fechaFin"));
        assignment.setTitle(assignmentResult.getString("titulo"));
        assignment.setDescription(assignmentResult.getString("descripcion"));
        assignment.setIdProject(assignmentResult.getInt("idAnteproyecto"));
        assignment.setProfessorId(assignmentResult.getInt("idUsuarioProfesor"));
        
        DataBaseManager.closeConnection();
        
        return assignment;
    }
}
