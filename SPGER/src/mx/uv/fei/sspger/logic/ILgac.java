package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author fabin
 */
public interface ILgac {
    int addLgac (Lgac lgac) throws SQLException;
    Lgac getLgac(String id) throws SQLException;
    List<Lgac> getAllLgac() throws SQLException;
}
