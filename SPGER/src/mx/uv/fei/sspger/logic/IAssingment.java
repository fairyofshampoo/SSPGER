package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

public interface IAssingment {
    int registerAssignment (Assignment assignment, String personalNumber, int idProject)throws SQLException;
    List<Assignment> getAssignmentsPerProject (int idProject) throws SQLException;
}
