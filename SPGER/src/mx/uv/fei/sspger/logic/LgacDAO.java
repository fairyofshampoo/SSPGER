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
public class LgacDAO implements ILgac {

    @Override
    public int addLgac(Lgac lgac) throws SQLException {
        int result;
        String query = "INSERT INTO lgac(idLGAC, nombre, descripcion) values (?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, lgac.getId());
        statement.setString(2, lgac.getName());
        statement.setString(3, lgac.getDescription());
        
        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public Lgac getLgac(String id) throws SQLException {
        String query = "SELECT * FROM lgac WHERE idLGAC = ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        
        Lgac lgac = new Lgac();
        
        preparedStatement.setString(1, id);
        ResultSet lgacResult = preparedStatement.executeQuery();
        lgacResult.next();
        
        lgac.setId(lgacResult.getString("idLGAC"));
        lgac.setName(lgacResult.getString("nombre"));
        lgac.setDescription(lgacResult.getString("descripcion"));
        
        
        dataBaseManager.closeConnection();
        
        return lgac;
    }
    
    @Override
    public List<Lgac> getAllLgac() throws SQLException{
        String query = "SELECT * FROM lgac";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet lgacResult = statement.executeQuery(query);
        List<Lgac> lgacList = new ArrayList<>();

        while(lgacResult.next()){
            Lgac lgac = new Lgac();
            
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
            lgac.setDescription(lgacResult.getString("descripcion"));
            lgacList.add(lgac);
        }
        
        dataBaseManager.closeConnection();
        
        return lgacList;
    }
}
