package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.contracts.ILgac;


public class LgacDAO implements ILgac {

    @Override
    public int addLgac(Lgac lgac) throws SQLException {
        int result;
        String query = "INSERT INTO lgac(idLGAC, nombre, descripcion) values (?,?,?)";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, lgac.getId());
        statement.setString(2, lgac.getName());
        statement.setString(3, lgac.getDescription());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public Lgac getLgac(String idLgac) throws SQLException {
        String query = "SELECT * FROM lgac WHERE idLGAC = ?";
        DataBaseManager.getConnection();
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(query);
        
        Lgac lgac = new Lgac();
        
        preparedStatement.setString(1, idLgac);
        ResultSet lgacResult = preparedStatement.executeQuery();
        lgacResult.next();
        
        lgac.setId(lgacResult.getString("idLGAC"));
        lgac.setName(lgacResult.getString("nombre"));
        lgac.setDescription(lgacResult.getString("descripcion"));
        
        
        DataBaseManager.closeConnection();
        
        return lgac;
    }
    
    @Override
    public List<Lgac> getAllLgac() throws SQLException{
        String query = "SELECT * FROM lgac";
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().prepareStatement(query);
        ResultSet lgacResult = statement.executeQuery(query);
        List<Lgac> lgacList = new ArrayList<>();

        while(lgacResult.next()){
            Lgac lgac = new Lgac();
            
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
            lgac.setDescription(lgacResult.getString("descripcion"));
            lgacList.add(lgac);
        }
        
        DataBaseManager.closeConnection();
        
        return lgacList;
    }
}