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

    private final String GET_LGAC = "SELECT * FROM lgac WHERE idLGAC = ?";
    private final String GET_ALL_LGAC = "SELECT * FROM lgac";
    private final String ERROR_ID = "Error";

    /**
     * @param idLgac to search in the database the Lgac with that id.
     * @return an Lgac with all its atributes if found on the database, if not
     * return an Lgac with an error id.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Lgac getLgac(String idLgac) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement preparedStatement = DataBaseManager.getConnection().prepareStatement(GET_LGAC);

        Lgac lgac = new Lgac();

        preparedStatement.setString(1, idLgac);
        ResultSet lgacResult = preparedStatement.executeQuery();

        if (lgacResult.next()) {
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
            lgac.setDescription(lgacResult.getString("descripcion"));
        } else {
            lgac.setId(ERROR_ID);
        }

        DataBaseManager.closeConnection();

        return lgac;
    }

    /**
     * @return A list with all the objects of Lgac, if no objects found return
     * an empty list.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Lgac> getAllLgac() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet lgacResult = statement.executeQuery(GET_ALL_LGAC);
        List<Lgac> lgacList = new ArrayList<>();

        while (lgacResult.next()) {
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
