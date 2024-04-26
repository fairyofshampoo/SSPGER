package mx.uv.fei.sspger.logic.contracts;

import mx.uv.fei.sspger.logic.Submission;
import java.sql.SQLException;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Feedback;

public interface ISubmissionManager {

    int addSubmission(Submission submission) throws SQLException;

    int modifySubmission(Submission submission) throws SQLException;

    Submission getSubmissionById(int idSubmission) throws SQLException;

    int getCountSubmissionsPerReceptionalWork(int idReceptionalWork) throws SQLException;

    Submission getSubmissionPerAssignment(int idAssignment) throws SQLException;

    int addFile(DeliverableFile file) throws SQLException;

    DeliverableFile getFile(int idFile) throws SQLException;

    int addFeedback(Feedback feedback) throws SQLException;

    int updateFeedback(Feedback feedback) throws SQLException;

    Feedback getFeedbackBySubmission(int idSubmission) throws SQLException;
}
