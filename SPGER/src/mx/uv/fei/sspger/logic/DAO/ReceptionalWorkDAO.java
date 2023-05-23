package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.contracts.IReceptionalWork;


public class ReceptionalWorkDAO implements IReceptionalWork{
    
    private final String GET_RECEPTIONAL_WORK_BY_ID = "SELECT * FROM trabajo_recepcional WHERE idTrabajoRecepcional = ?";
    private final String GET_ACTIVE_RECEPTIONAL_WORK_BY_STUDENT = "SELECT nombre, descripcion, modalidad, idTrabajoRecepcional, idAnteproyecto FROM trabajo_recepcional NATURAL JOIN estudiante_trabajo_recepcional "
            + "WHERE idEstudiante = ? AND trabajo_recepcional.estado = 'activo'";

    @Override
    public ReceptionalWork getRecepetionalWorkById(int idReceptionalWork) throws SQLException {
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_BY_ID);
        
        statement.setInt(1, idReceptionalWork);
        
        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        
        if(receptionalWorkResult.next()){
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setSpace(receptionalWorkResult.getInt("cupoActual"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
        }
        
        DataBaseManager.closeConnection();
        
        return receptionalWork;
    }

    @Override
    public ReceptionalWork getActiveReceptionalWorkByStudent(int idStudent) throws SQLException {
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ACTIVE_RECEPTIONAL_WORK_BY_STUDENT);
        
        statement.setInt(1, idStudent);
        
        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        
        if(receptionalWorkResult.next()){
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
        }
        
        DataBaseManager.closeConnection();
        
        return receptionalWork;
    }

    @Override
    public List<ReceptionalWork> getReceptionalWorksByStudent(int idStudent) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int addReceptionalWork(ReceptionalWork receptionalWork) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int modifyReceptionalWork(ReceptionalWork receptionalWork) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int changeReceptionalWorkState(String receptionalWorkState) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
