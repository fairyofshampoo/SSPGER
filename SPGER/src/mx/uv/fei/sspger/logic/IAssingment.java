package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

public interface IAssingment {
    int registerAssignment (Assignment assignment, String personalNumber)throws SQLException;
    List<Assignment> getAssignmentsPerProyect () throws SQLException;
}
