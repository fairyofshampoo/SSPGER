/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;

/**
 *
 * @author mario
 */
public class AssignmentDAO implements IAssingment{
    
    @Override
    public int registerAssignment(Assignment assignment, String personalNumber) throws SQLException{
        int result;
        String query = "insert into asignacion(titulo, fechaLimiteEntrega, fechaPublicacion, descripcion, idUsuarioProfesor)"
                + "values (? , ? , ? , ? , ?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, assignment.getTitle());
        statement.setTimestamp(2,assignment.getDeadline());
        statement.setTimestamp(3, assignment.getPublicationDate());
        statement.setString(4, assignment.getDescription());
        statement.setString(5, personalNumber);
        
        result = statement.executeUpdate();
        
        return result;
    }

    @Override
    public List<Assignment> getAssignmentsPerProyect() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
