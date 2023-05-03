package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;


public interface IStudent {
    int addStudent(Student student) throws SQLException;
    Student getStudent(String registrationTag) throws SQLException;
    List <Student> getAllStudents() throws SQLException;
    int updateStudent (String registrationTag, Student student) throws SQLException;
    int deleteStudent (String registrationTag) throws SQLException;
    
}
