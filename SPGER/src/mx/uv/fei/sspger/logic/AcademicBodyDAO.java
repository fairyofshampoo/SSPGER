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
    public AcademicBody getAcademicBody(String id) throws SQLException {
        String query = "SELECT * FROM cuerpo_academico WHERE idCuerpoAcademico = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        AcademicBody academicBody = new AcademicBody();
        
        preparedStatement.setString(1, id);
        ResultSet academicBodyResult = preparedStatement.executeQuery();
        academicBodyResult.next();
        
        academicBody.setId(academicBodyResult.getString("idCuerpoAcademico"));
        academicBody.setName(academicBodyResult.getString("nombre"));
        
        dataBaseManager.closeConnection();
        
        return academicBody;
    }
    
    @Override
    public List<AcademicBody> getAllAcademicBody() throws SQLException{
        String query = "SELECT * FROM cuerpo_academico";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet academicBodyResult = statement.executeQuery(query);
        List<AcademicBody> academicBodyList = new ArrayList<>();

        while(academicBodyResult.next()){
            AcademicBody academicBody = new AcademicBody();
            
            academicBody.setId(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
            academicBodyList.add(academicBody);
        }
        
        dataBaseManager.closeConnection();
        
        return academicBodyList;
    }
}
