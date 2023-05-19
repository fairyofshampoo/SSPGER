package mx.uv.fei.sspger.logic.contracts;


import mx.uv.fei.sspger.logic.ReceptionalWork;
import java.sql.SQLException;
import java.util.List;


public interface IReceptionalWork {
    ReceptionalWork getRecepetionalWorkById(int idReceptionalWork) throws SQLException;
    ReceptionalWork getActiveReceptionalWorkByStudent (int idStudent) throws SQLException;
    List<ReceptionalWork> getReceptionalWorksByStudent (int idStudent) throws SQLException;
    int addReceptionalWork (ReceptionalWork receptionalWork) throws SQLException;
    int modifyReceptionalWork (ReceptionalWork receptionalWork) throws SQLException;
    int changeReceptionalWorkState (String receptionalWorkState) throws SQLException;
}
