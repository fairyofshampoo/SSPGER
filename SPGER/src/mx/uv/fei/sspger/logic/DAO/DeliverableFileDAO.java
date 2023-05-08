package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.contracts.IDeliverableFile;


public class DeliverableFileDAO implements IDeliverableFile{

    private final String ADD_FILE_QUERY = "insert into archivo_entregable "
            + "(nombre, ruta, extension) values (?, ?, ?)";
    private final String GET_FILE_QUERY = "SELECT * FROM where "
            + "idarchivoentregable = ?";
    
    @Override
    public int addFile(DeliverableFile file) throws SQLException {
        int result;
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(ADD_FILE_QUERY);
        
        statement.setString(1, file.getName());
        statement.setString(2, file.getExtension());
        statement.setString(3, file.getPath());
        
        result = statement.executeUpdate();
        
        DataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public DeliverableFile getFile(int idDeliverableFile) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
                prepareStatement(GET_FILE_QUERY);

        statement.setInt(1,idDeliverableFile);

        ResultSet fileResult = statement.executeQuery();
        fileResult.next();
        
        DeliverableFile file = new DeliverableFile();
        file.setName(fileResult.getString("nombre"));
        file.setPath(fileResult.getString("ruta"));
        file.setExtension(fileResult.getString("extension"));
        
        DataBaseManager.closeConnection();

        return file;
    }
    
}
