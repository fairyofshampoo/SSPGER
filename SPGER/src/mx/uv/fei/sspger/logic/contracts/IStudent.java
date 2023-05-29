package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;


public interface IStudent {
    int addStudentTransaction(Student student) throws SQLException;
    Student getStudent(String email) throws SQLException;
    Student searchStudentbyRegistrationTag(String registrationTag) throws SQLException;
    List <Student> getAllStudents() throws SQLException;
    List <Student> getStudentsByStatus(int status) throws SQLException;
    List <Student> getStudentPerReceptionalWork(int idRedceptionalWork) throws SQLException;
    int updateStudentTransaction (String email, Student student) throws SQLException;
    int changeStudentStatus (String registrationTag, int status) throws SQLException;
    
}
