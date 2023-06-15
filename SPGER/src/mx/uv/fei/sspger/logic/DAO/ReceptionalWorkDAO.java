package mx.uv.fei.sspger.logic.DAO;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import mx.uv.fei.sspger.logic.Student;
import mx.uv.fei.sspger.logic.contracts.IReceptionalWork;


public class ReceptionalWorkDAO implements IReceptionalWork{
    
    private final int VALUE_BY_DEFAULT = 0;
    private final String ACTIVE_RECEPTIONAL_WORK = "VIGENTE";
    private final String STATUS_STUDENT_ACTIVE = "Activo";
    private final String GET_RECEPTIONAL_WORK_BY_ID = "SELECT * FROM trabajo_recepcional WHERE idTrabajoRecepcional = ?";
    private final String GET_ACTIVE_RECEPTIONAL_WORK_BY_STUDENT = "SELECT nombre, descripcion, modalidad, idTrabajoRecepcional, idAnteproyecto FROM trabajo_recepcional NATURAL JOIN estudiante_trabajo_recepcional "
            + "WHERE idEstudiante = ? AND trabajo_recepcional.estado = 'activo'";
    private final String GET_RECEPTIONAL_WORK_INFO_WITH_COUNT = "SELECT nombre, modalidad, idTrabajoRecepcional, estado, COUNT(*) AS count FROM trabajo_recepcional"
            + " NATURAL JOIN estudiante_trabajo_recepcional WHERE idTrabajoRecepcional = ? GROUP BY nombre, modalidad, idTrabajoRecepcional, estado";
    private final String GET_RECEPTIONAL_WORKS_BY_PROFESSOR = "SELECT idTrabajoRecepcional FROM trabajo_recepcional NATURAL JOIN profesor_anteproyecto WHERE idUsuarioProfesor = ?";
    
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
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
        }
        
        DataBaseManager.closeConnection();
        
        return receptionalWork;
    }
    
    @Override
    public int addStudentToReceptionalWork(List<Student> studentList, int idReceptionalWork) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
        DataBaseManager.getConnection();
        
        for(int i = 0; i < studentList.size(); i++){
            String query = "INSERT INTO estudiante_trabajo_recepcional(estadoEstudiante,idEstudiante,idTrabajoRecepcional) VALUES (?,?,?)";
            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

            statement.setString(1, STATUS_STUDENT_ACTIVE);
            statement.setInt(2, studentList.get(i).getId());
            statement.setInt(3, idReceptionalWork);

            result = statement.executeUpdate();
        }
        
        DataBaseManager.closeConnection();
        return result;
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
    public ReceptionalWork getReceptionalWorkWithNumberOfStudents(int idReceptionalWork) throws SQLException {
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORK_INFO_WITH_COUNT);
        
        statement.setInt(1, idReceptionalWork);
        
        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        
        if(receptionalWorkResult.next()){
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setSpace(receptionalWorkResult.getInt("count"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
        }
        
        DataBaseManager.closeConnection();
        
        return receptionalWork;
    }
    
    @Override
    public int getTotalActiveReceptionalWorks(int idProject, String status) throws SQLException{
        String query = "SELECT COUNT(*) FROM trabajo_recepcional WHERE idAnteproyecto = ? AND estado = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        statement.setString(2, status);
        
        ResultSet totalReceptionalWorkResult = statement.executeQuery();
        int totalReceptionalWorks = VALUE_BY_DEFAULT;
        
        if(totalReceptionalWorkResult.next()){
            totalReceptionalWorks = totalReceptionalWorkResult.getInt(1);
        }

        statement.close();
        DataBaseManager.closeConnection();
        
        return totalReceptionalWorks;
    }
    
    @Override
    public List<ReceptionalWork> getReceptionalWorksByProfessor(int idProfessor) throws SQLException {
        DataBaseManager.getConnection();
        
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_RECEPTIONAL_WORKS_BY_PROFESSOR);
        
        statement.setInt(1, idProfessor);
        
        ResultSet receptionalWorkResult = statement.executeQuery();
        List<ReceptionalWork> professorReceptionalWorks = new ArrayList<>();
        
        while(receptionalWorkResult.next()){
            ReceptionalWork receptionalWork = new ReceptionalWork();
            
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            professorReceptionalWorks.add(receptionalWork);
        }
        
        DataBaseManager.closeConnection();
        
        return professorReceptionalWorks;
    }
    
    @Override
    public ReceptionalWork getReceptionalWorkByStatus(int idProject, String status) throws SQLException{
        String query = "SELECT * FROM trabajo_recepcional WHERE idAnteproyecto = ? AND estado = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        statement.setString(2, status);
        
        ResultSet receptionalWorkResult = statement.executeQuery();
        ReceptionalWork receptionalWork = new ReceptionalWork();
        
        if(receptionalWorkResult.next()){
            receptionalWork.setIdReceptionalWork(receptionalWorkResult.getInt("idTrabajoRecepcional"));
            receptionalWork.setName(receptionalWorkResult.getString("nombre"));
            receptionalWork.setDescription(receptionalWorkResult.getString("descripcion"));
            receptionalWork.setModality(receptionalWorkResult.getString("modalidad"));
            receptionalWork.setState(receptionalWorkResult.getString("estado"));
            receptionalWork.setResults(receptionalWorkResult.getString("resultados"));
            receptionalWork.setIdProject(receptionalWorkResult.getInt("idAnteproyecto"));
        }
        
        DataBaseManager.closeConnection();
        
        return receptionalWork;
    }
    
    @Override
    public int expellStudent(int idProject, List<Student> studentList) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            
            for(int i = 0; i < studentList.size(); i++){
                String query = "DELETE FROM estudiante_trabajo_recepcional WHERE idEstudiante = ? AND EXISTS(SELECT * FROM trabajo_recepcional WHERE idAnteproyecto = ? AND estado = ? AND trabajo_recepcional.idTrabajoRecepcional = estudiante_trabajo_recepcional.idTrabajoRecepcional)";
                PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

                statement.setInt(1, studentList.get(i).getId());
                statement.setInt(2, idProject);
                statement.setString(3, ACTIVE_RECEPTIONAL_WORK);

                result += statement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(ReceptionalWorkDAO.class.getName()).log(Level.SEVERE, null, ex);
            DataBaseManager.getConnection().rollback();
        }finally{
            DataBaseManager.getConnection().close();
        }
        
        return result;
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
