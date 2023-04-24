package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;

public interface IStudent {
    int registrar(Student estudiante) throws SQLException;
    List <Student> getStudentList() throws SQLException;
    List <Student> getAvailableStudents() throws SQLException;
    List <Student> getStudentsPerCourse (String courseId) throws SQLException;
}
