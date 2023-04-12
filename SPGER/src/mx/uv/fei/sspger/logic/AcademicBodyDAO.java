package mx.uv.fei.sspger.logic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;

/**
 *
 * @author fabin
 */
public class AcademicBodyDAO implements IAcademicBody {

    @Override
    public int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result;
        String query = "insert into cuerpo_academico(idCuerpoAcademico, nombre) values (?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, academicBody.getId());
        statement.setString(2, academicBody.getName());
        
        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public AcademicBody getAcademicBody() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
