package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

public interface IAssingment {
    public int registerAssignment (Assignment assignment, String personalNumber)throws SQLException;
    public List<Assignment> getAssignmentsPerProyect () throws SQLException;
}
