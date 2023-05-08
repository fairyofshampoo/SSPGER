package mx.uv.fei.sspger.logic.contracts;


import mx.uv.fei.sspger.logic.Semester;
import java.sql.SQLException;
import java.util.List;


public interface ISemester {
    Semester getSemesterPerStartDate (Semester semester) throws SQLException;
    Semester getSemesterPerId (int semesterId) throws SQLException;
    List<Semester> getAvailableSemesters () throws SQLException;
}