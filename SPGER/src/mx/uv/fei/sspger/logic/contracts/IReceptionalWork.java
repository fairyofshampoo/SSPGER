package mx.uv.fei.sspger.logic.contracts;


import mx.uv.fei.sspger.logic.ReceptionalWork;
import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;


public interface IReceptionalWork {
    ReceptionalWork getRecepetionalWorkById(int idReceptionalWork) throws SQLException;
    ReceptionalWork getActiveReceptionalWorkByStudent (int idStudent) throws SQLException;
    ReceptionalWork getReceptionalWorkWithNumberOfStudents (int idReceptionalWork) throws SQLException;
    List<ReceptionalWork> getReceptionalWorksByStudent (int idStudent) throws SQLException;
    ReceptionalWork getReceptionalWorkByStatus(int idProject, String status) throws SQLException;
    List<ReceptionalWork> getReceptionalWorksByProfessor (int idProfessor) throws SQLException;
    int expellStudent(int idProject, List<Student> studentList) throws SQLException;
    int addStudentToReceptionalWork(List<Student> studentList, int idReceptionalWork) throws SQLException;
    int getTotalActiveReceptionalWorks(int idProject, String status) throws SQLException;
    int addReceptionalWork (ReceptionalWork receptionalWork) throws SQLException;
    int modifyReceptionalWork (ReceptionalWork receptionalWork) throws SQLException;
    int changeReceptionalWorkState (String receptionalWorkState) throws SQLException;
}
