package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Submission;
import mx.uv.fei.sspger.logic.contracts.ISubmissionManager;


public class SubmissionManagerDAO implements ISubmissionManager{

    private final int ERROR_ADDITION = -1;
    private final int ERROR_IN_COUNT = -2;
    private final String ADD_SUBMISSION_QUERY = "insert into avance (descripcion, fechaEntrega, idArchivoAvance) values (?, ?, ?)";
    private final String ADD_FILE_QUERY = "insert into archivo_entregable "
            + "(nombre, ruta, extension) values (?, ?, ?)";
    private final String ADD_SUBMISSION_TO_ASIGNMENT = "UPDATE asignacion SET idAvance = ? WHERE idAsignacion = ?";
    private final String GET_SUBMISSION_QUERY = "SELECT * FROM avance WHERE idAvance = ?";
    private final String GET_SUBMISSION_COUNT_PER_RECEPTIONAL_WORK = "SELECT COUNT(*) AS submissionCount FROM asignacion WHERE idTrabajoRecepcional = ? AND idAvance IS NOT NULL";
    
    @Override
    public int addSubmission(Submission submission, DeliverableFile file, int idAsignment) throws SQLException {
    int response = ERROR_ADDITION;
    try{
        DataBaseManager.getConnection().setAutoCommit(false);
        PreparedStatement fileStatement = DataBaseManager.getConnection().prepareStatement(ADD_FILE_QUERY, Statement. RETURN_GENERATED_KEYS);
        
        fileStatement.setString(1, file.getName());
        fileStatement.setString(2, file.getPath());
        fileStatement.setString(3, file.getExtension());
        
        fileStatement.executeUpdate();
        
        ResultSet fileResult = fileStatement.getGeneratedKeys();
        
        while(fileResult.next()){
            response = fileResult.getInt(1);
        }
        
        if(response != ERROR_ADDITION){
            PreparedStatement submissionStatement = DataBaseManager.getConnection().prepareStatement(ADD_SUBMISSION_QUERY, Statement. RETURN_GENERATED_KEYS);
        
            submissionStatement.setString(1, submission.getDescription());
            submissionStatement.setDate(2, submission.getDeliveryDate());
            submissionStatement.setInt(3, response);
            
            submissionStatement.executeUpdate();
            
            ResultSet submissionResult = submissionStatement.getGeneratedKeys();
            
            while(submissionResult.next()){
                response = submissionResult.getInt(1);
            }
            
            if(response != ERROR_ADDITION){
                PreparedStatement asignmentStatement = DataBaseManager.getConnection().prepareStatement(ADD_SUBMISSION_TO_ASIGNMENT);
                
                asignmentStatement.setInt(1, response);
                asignmentStatement.setInt(2, idAsignment);
                
                response = asignmentStatement.executeUpdate();
            }
        }
        
        DataBaseManager.getConnection().commit();
    } catch (SQLException ex){
        DataBaseManager.getConnection().rollback();
    } finally{
        DataBaseManager.getConnection().close();
    }
    return response;
    }

    @Override
    public int modifySubmission(Submission submission, int idSubmission) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Submission getSubmissionById(int idSubmission) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_SUBMISSION_QUERY);
        
        statement.setInt(1, idSubmission);
        
        ResultSet submissionResult = statement.executeQuery();
        Submission submission = new Submission();
               
        if (submissionResult.next()){
            submission.setId(submissionResult.getInt("idavance"));
            submission.setDeliveryDate(submissionResult.getDate("fechaEntrega"));
            submission.setDescription(submissionResult.getString("descripcion"));
            submission.setIdDeliverableFile(submissionResult.getInt("idArchivoAvance"));
        }
        
        DataBaseManager.closeConnection();
        
        return submission;
    }

    @Override
    public int getCountSubmissionsPerReceptionalWork(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_SUBMISSION_COUNT_PER_RECEPTIONAL_WORK);
        int quantityOfSubmissions;
        
        statement.setInt(1, idReceptionalWork);
        
        ResultSet countResult = statement.executeQuery();
        
        if (countResult.next()){
            quantityOfSubmissions = countResult.getInt("submissionCount");
        }
        else {
            quantityOfSubmissions = ERROR_IN_COUNT;
        }
        
        return quantityOfSubmissions;
    }
    
}