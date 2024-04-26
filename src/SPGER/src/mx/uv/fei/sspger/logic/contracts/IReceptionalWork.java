package mx.uv.fei.sspger.logic.contracts;

import mx.uv.fei.sspger.logic.ReceptionalWork;
import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Student;

public interface IReceptionalWork {

    ReceptionalWork getRecepetionalWorkById(int idReceptionalWork) throws SQLException;

    int expellStudent(int idProject, List<Student> studentList, int idReceptionalWork) throws SQLException;

    ReceptionalWork getActiveReceptionalWorkByStudent(int idStudent) throws SQLException;

    ReceptionalWork getReceptionalWorkWithNumberOfStudents(int idReceptionalWork) throws SQLException;

    ReceptionalWork getReceptionalWorkByStatus(int idProject, String status) throws SQLException;

    List<ReceptionalWork> getReceptionalWorksByProfessorAndState(int idProfessor, String state) throws SQLException;

    List<ReceptionalWork> getReceptionalWorksByProfessor(int idProfessor) throws SQLException;

    int updateReceptionalWork(ReceptionalWork receptionalWork) throws SQLException;

    int abandoneReceptionalWork(List<Student> studentList, ReceptionalWork receptionalWork) throws SQLException;

    int addStudentToReceptionalWork(List<Student> studentList, int idReceptionalWork) throws SQLException;

    int getTotalActiveReceptionalWorks(int idProject, String status) throws SQLException;

    int addStudentsToProjectTransaction(List<Student> studentList, int idReceptionalWork, int idProject) throws SQLException;

    int addReceptionalWorkTransaction(List<Student> studentList, ReceptionalWork receptionalWork, int idProject) throws SQLException;

    int concludeReceptionalWork(ReceptionalWork receptionalWork) throws SQLException;

    ReceptionalWork getReceptionalWorkName(int receptionalWorkId) throws SQLException;
}
