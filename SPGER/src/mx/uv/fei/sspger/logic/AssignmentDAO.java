/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;

/**
 *
 * @author mario
 */
public class AssignmentDAO implements IAssingment{
    
    @Override
    public int registerAssignment (Assignment assignment, String idProfessor, int idProject) throws SQLException {
        int result;
        String query = "INSERT INTO asignacion(idUsuarioProfesor, titulo, fechaInicia, fechaFin, fechaPublicacion, descripcion, idAnteproyecto) values (?,?,?,?,?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, idProfessor);
        statement.setString(2, assignment.getTitle());
        statement.setTimestamp(3, assignment.getStartDate());
        statement.setTimestamp(4, assignment.getDeadline());
        statement.setTimestamp(5, assignment.getPublicationDate());
        statement.setString(6, assignment.getDescription());
        statement.setInt(7, idProject);

        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }


    @Override
    public List<Assignment> getAssignmentsPerProject(int idProject) throws SQLException {
        String query = "Select * From asignacion Where idAnteproyecto= ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, 1);
        
        ResultSet assignmentResult = statement.executeQuery();
        List<Assignment> assignmentList = new ArrayList<>();
        
        while(assignmentResult.next()){
            Assignment assignment = new Assignment();
            
            assignment.setTitle(assignmentResult.getString("titulo"));
            assignment.setStartDate(assignmentResult.getTimestamp("fechaInicia"));
            assignment.setDeadline(assignmentResult.getTimestamp("fechaFin"));
            assignment.setPublicationDate(assignmentResult.getTimestamp("fechaPublicacion"));
            assignment.setDescription(assignmentResult.getString("descripcion"));
            assignmentList.add(assignment);
        } 
        dataBaseManager.closeConnection();
        
        return assignmentList;
    }
}
