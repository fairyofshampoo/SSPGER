package mx.uv.fei.sspger.logic.contracts;

import mx.uv.fei.sspger.logic.DeliverableFile;
import java.sql.SQLException;


public interface IDeliverableFile {
    int addFile(DeliverableFile file) throws SQLException;
    DeliverableFile getFile(int idDeliverableFile) throws SQLException;
}