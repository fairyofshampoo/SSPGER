package mx.uv.fei.sspger.logic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;

/**
 *
 * @author fabin
 */
public class LgacDAO implements ILgac {

    @Override
    public int addLgac(Lgac lgac) throws SQLException {
        int result;
        String query = "insert into lgac(idLGAC, nombre, descripcion) values (?,?,?)";
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
    public Lgac getLgac() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
