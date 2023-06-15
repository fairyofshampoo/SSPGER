package mx.uv.fei.sspger.logic.contracts;

import mx.uv.fei.sspger.logic.Submission;
import java.sql.SQLException;
import mx.uv.fei.sspger.logic.DeliverableFile;


public interface ISubmissionManager {
    int addSubmission(Submission submission, DeliverableFile file, int idAsignment) throws SQLException;
    int modifySubmission(Submission submission, int idSubmission) throws SQLException;
    Submission getSubmissionById(int idSubmission) throws SQLException;
    int getCountSubmissionsPerReceptionalWork (int idReceptionalWork) throws SQLException;
}