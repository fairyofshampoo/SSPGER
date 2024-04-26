package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Lgac;

public interface ILgac {

    Lgac getLgac(String idLgac) throws SQLException;

    List<Lgac> getAllLgac() throws SQLException;
}
