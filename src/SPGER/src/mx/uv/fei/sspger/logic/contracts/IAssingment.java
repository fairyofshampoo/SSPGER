package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Assignment;

public interface IAssingment {

    int registerAssignment(Assignment assignment, int idProfessor) throws SQLException;

    int updateAssignment(Assignment assignment) throws SQLException;

    List<Assignment> getAssignmentsPerReceptionalWork(int idReceptionalWork) throws SQLException;

    int deleteAssignment(Assignment assignment) throws SQLException;

    Assignment getAssignmentById(int assignmentId) throws SQLException;

    int getCountAssignmentPerReceptionalWork(int idReceptionalWork) throws SQLException;
}
