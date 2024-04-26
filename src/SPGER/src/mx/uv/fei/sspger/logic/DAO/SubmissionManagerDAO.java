package mx.uv.fei.sspger.logic.DAO;

import java.util.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Submission;
import mx.uv.fei.sspger.logic.contracts.ISubmissionManager;
import mx.uv.fei.sspger.logic.Feedback;

public class SubmissionManagerDAO implements ISubmissionManager {

    private final int ERROR_ADDITION = -1;
    private final int ERROR_IN_COUNT = -2;
    private final int NOT_FOUND = -4;
    private final String ADD_SUBMISSION_QUERY = "insert into avance (descripcion, fechaEntrega, idArchivoAvance) values (?, ?, ?)";
    private final String ADD_FILE_QUERY = "insert into archivo_entregable "
        + "(nombre, ruta, extension) values (?, ?, ?)";
    private final String GET_FILE_QUERY = "SELECT * FROM archivo_entregable where idarchivoentregable = ?";
    private final String GET_SUBMISSION_BY_ASSIGNMENT = "SELECT * FROM avance INNER JOIN asignacion ON avance.idavance = asignacion.idAvance WHERE asignacion.idAsignacion = ?";
    private final String ADD_SUBMISSION_TO_ASIGNMENT = "UPDATE asignacion SET idAvance = ? WHERE idAsignacion = ?";
    private final String UPDATE_SUBMISSION_QUERY = "UPDATE avance SET descripcion = ?, fechaEntrega = ? WHERE idavance = ?";
    private final String UPDATE_FILE_QUERY = "UPDATE archivo_entregable SET nombre = ?, ruta = ?, extension = ? WHERE idarchivoentregable = ?";
    private final String GET_SUBMISSION_QUERY = "SELECT * FROM avance WHERE idAvance = ?";
    private final String GET_SUBMISSION_COUNT_PER_RECEPTIONAL_WORK = "SELECT COUNT(*) AS submissionCount FROM asignacion WHERE idTrabajoRecepcional = ? AND idAvance IS NOT NULL";
    private final String GET_FEEDBACK_BY_SUBMISSION = "SELECT * FROM evalua WHERE idAvance = ?";
    private final String ADD_FEEDBACK_QUERY = "INSERT INTO evalua (idAvance, retroalimentacion) values (?,?)";
    private final String UPDATE_FEEDBACK_QUERY = "UPDATE evalua SET retroalimentacion = ? WHERE idEvalua = ?";

    /**
     *
     * @param submission to save new submission in the database.
     * @return int = -1 if it is not successful, 2 if it is succesful
     * @throws SQLException if f there's an error in the database.
     */
    @Override
    public int addSubmission(Submission submission) throws SQLException {
        int response = ERROR_ADDITION;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement fileStatement = DataBaseManager.getConnection().prepareStatement(ADD_FILE_QUERY, Statement.RETURN_GENERATED_KEYS);
            DeliverableFile file = submission.getFile();
            fileStatement.setString(1, file.getName());
            fileStatement.setString(2, file.getPath());
            fileStatement.setString(3, file.getExtension());

            fileStatement.executeUpdate();

            ResultSet fileResult = fileStatement.getGeneratedKeys();

            while (fileResult.next()) {
                response = fileResult.getInt(1);
            }

            if (response != ERROR_ADDITION) {
                PreparedStatement submissionStatement = DataBaseManager.getConnection().prepareStatement(ADD_SUBMISSION_QUERY, Statement.RETURN_GENERATED_KEYS);

                Date utilDeliveryDate = submission.getDeliveryDate();
                java.sql.Timestamp timestampDeliveryDate = new java.sql.Timestamp(utilDeliveryDate.getTime());

                submissionStatement.setString(1, submission.getDescription());
                submissionStatement.setTimestamp(2, timestampDeliveryDate);
                submissionStatement.setInt(3, response);

                submissionStatement.executeUpdate();

                ResultSet submissionResult = submissionStatement.getGeneratedKeys();

                while (submissionResult.next()) {
                    response = submissionResult.getInt(1);
                }

                if (response != ERROR_ADDITION) {
                    PreparedStatement asignmentStatement = DataBaseManager.getConnection().prepareStatement(ADD_SUBMISSION_TO_ASIGNMENT);

                    asignmentStatement.setInt(1, response);
                    asignmentStatement.setInt(2, submission.getAssignment().getId());

                    response = asignmentStatement.executeUpdate();
                }
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = ERROR_ADDITION;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param submission to update new submission in the database
     * @return int = -1 if it is not successful, 2 if it is succesful
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int modifySubmission(Submission submission) throws SQLException {
        int response = ERROR_ADDITION;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement fileStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_FILE_QUERY);
            DeliverableFile file = submission.getFile();
            fileStatement.setString(1, file.getName());
            fileStatement.setString(2, file.getPath());
            fileStatement.setString(3, file.getExtension());
            fileStatement.setInt(4, file.getId());

            response += fileStatement.executeUpdate();

            if (response != ERROR_ADDITION) {
                PreparedStatement submissionStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_SUBMISSION_QUERY);

                Date utilDeliveryDate = submission.getDeliveryDate();
                java.sql.Timestamp timestampDeliveryDate = new java.sql.Timestamp(utilDeliveryDate.getTime());

                submissionStatement.setString(1, submission.getDescription());
                submissionStatement.setTimestamp(2, timestampDeliveryDate);
                submissionStatement.setInt(3, submission.getId());

                response += submissionStatement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = ERROR_ADDITION;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param idSubmission to get an existing submission in the database
     * @return a submission with id = -4 if there's no submission to retrieve
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Submission getSubmissionById(int idSubmission) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_SUBMISSION_QUERY);

        statement.setInt(1, idSubmission);

        ResultSet submissionResult = statement.executeQuery();
        Submission submission = new Submission();
        submission.setId(NOT_FOUND);

        if (submissionResult.next()) {
            submission.setId(submissionResult.getInt("idavance"));
            java.util.Date deliveryUtilDate = new java.util.Date(submissionResult.getTimestamp("fechaEntrega").getTime());
            submission.setDeliveryDate(deliveryUtilDate);
            submission.setDescription(submissionResult.getString("descripcion"));
            submission.getFile().setId(submissionResult.getInt("idArchivoAvance"));
        }

        DataBaseManager.closeConnection();

        return submission;
    }

    /**
     *
     * @param idReceptionalWork to count the number of submission in the
     * receptional work
     * @return -1 if there's an error or the count of submissions if it's
     * successful
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getCountSubmissionsPerReceptionalWork(int idReceptionalWork) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_SUBMISSION_COUNT_PER_RECEPTIONAL_WORK);
        int quantityOfSubmissions;

        statement.setInt(1, idReceptionalWork);

        ResultSet countResult = statement.executeQuery();

        if (countResult.next()) {
            quantityOfSubmissions = countResult.getInt("submissionCount");
        } else {
            quantityOfSubmissions = ERROR_IN_COUNT;
        }

        return quantityOfSubmissions;
    }

    /**
     *
     * @param idAssignment to get a submission per assignment in the database
     * @return a submission with id = -4 if it's not found or the submission if
     * it is found
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Submission getSubmissionPerAssignment(int idAssignment) throws SQLException {

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_SUBMISSION_BY_ASSIGNMENT);

        statement.setInt(1, idAssignment);

        ResultSet submissionResult = statement.executeQuery();
        Submission submission = new Submission();
        submission.setId(NOT_FOUND);

        if (submissionResult.next()) {
            submission.setId(submissionResult.getInt("idavance"));
            java.util.Date deliveryUtilDate = new java.util.Date(submissionResult.getTimestamp("fechaEntrega").getTime());
            submission.setDeliveryDate(deliveryUtilDate);
            submission.setDescription(submissionResult.getString("descripcion"));
            submission.getFile().setId(submissionResult.getInt("idArchivoAvance"));
        }

        DataBaseManager.closeConnection();

        return submission;
    }

    /**
     *
     * @param file to save new file in the database
     * @return number of rows affected, 0 if it's not successful
     * @throws SQLException if there's an error in the database.
     */
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

    /**
     *
     * @param idDeliverableFile to get a deliverable file from the database
     * @return deliverable file wiht id = -1 if there's no result
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public DeliverableFile getFile(int idDeliverableFile) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
            prepareStatement(GET_FILE_QUERY);

        statement.setInt(1, idDeliverableFile);
        DeliverableFile file = new DeliverableFile();
        file.setId(NOT_FOUND);

        ResultSet fileResult = statement.executeQuery();
        if (fileResult.next()) {
            file.setName(fileResult.getString("nombre"));
            file.setPath(fileResult.getString("ruta"));
            file.setExtension(fileResult.getString("extension"));
        }
        DataBaseManager.closeConnection();

        return file;
    }

    /**
     *
     * @param feedback to save new feedback in the database
     * @return number of rows affected
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int addFeedback(Feedback feedback) throws SQLException {
        int response = ERROR_ADDITION;
        DataBaseManager.getConnection();
        PreparedStatement feedbackStatement = DataBaseManager.getConnection().
            prepareStatement(ADD_FEEDBACK_QUERY);

        feedbackStatement.setInt(1, feedback.getSubmission().getId());
        feedbackStatement.setString(2, feedback.getComment());

        response = feedbackStatement.executeUpdate();

        DataBaseManager.closeConnection();
        return response;
    }

    /**
     *
     * @param feedback to update an existing feedback in the database
     * @return number of rows affected
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int updateFeedback(Feedback feedback) throws SQLException {
        int response = ERROR_ADDITION;
        DataBaseManager.getConnection();
        PreparedStatement feedbackStatement = DataBaseManager.getConnection().
            prepareStatement(UPDATE_FEEDBACK_QUERY);

        feedbackStatement.setString(1, feedback.getComment());
        feedbackStatement.setInt(2, feedback.getId());

        response = feedbackStatement.executeUpdate();

        DataBaseManager.closeConnection();
        return response;
    }

    /**
     *
     * @param idSubmission to get an existing feedback from database
     * @return feedback with id = -4 if there's no result
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public Feedback getFeedbackBySubmission(int idSubmission) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().
            prepareStatement(GET_FEEDBACK_BY_SUBMISSION);

        statement.setInt(1, idSubmission);
        Feedback feedback = new Feedback();
        feedback.setId(NOT_FOUND);

        ResultSet fileResult = statement.executeQuery();
        if (fileResult.next()) {
            feedback.setId(fileResult.getInt("idEvalua"));
            feedback.setComment(fileResult.getString("retroalimentacion"));
        }
        DataBaseManager.closeConnection();

        return feedback;
    }

}
