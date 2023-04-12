package mx.uv.fei.sspger.logic;


import java.sql.SQLException;

/**
 *
 * @author fabin
 */
public interface ILgac {
    int addLgac (Lgac lgac) throws SQLException;
    Lgac getLgac() throws SQLException;
}
