package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;

public interface IStudent {
    Student getStudent(int idStudent) throws SQLException;
    Student getStudent(String email) throws SQLException;
    List <Student> getAvailableStudents() throws SQLException;
    List <Student> getStudentsPerCourse (String courseId) throws SQLException;
    List <Student> getAvailableStudentsNotInCourse(String courseId) throws SQLException;
    List <Student> getStudentPerReceptionalWork(int idRedceptionalWork) throws SQLException;
    int addStudentTransaction(Student student) throws SQLException;
    List <Student> searchStudentbyName(String name) throws SQLException;
    List <Student> getAllStudents() throws SQLException;
    List <Student> getStudentsByStatus(int status) throws SQLException;
    int updateStudentTransaction (String email, Student student) throws SQLException;
    int changeStudentStatus (String registrationTag, int status) throws SQLException;
    List<Student> getStudentsByProjectByStatus(int idProject, String estadoEstudiante) throws SQLException;
}
