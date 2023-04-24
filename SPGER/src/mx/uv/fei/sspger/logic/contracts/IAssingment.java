package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Assignment;

public interface IAssingment {
    int registerAssignment (Assignment assignment, String personalNumber, int idProject)throws SQLException;
    List<Assignment> getAssignmentsPerProject (int idProject) throws SQLException;
}
