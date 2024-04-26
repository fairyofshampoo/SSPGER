package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.EnrollToCourse;

public interface ICourse {

    List<Course> getCoursesPerProfessor(int ProfessorId) throws SQLException;

    List<Course> getCoursesPerState(String state) throws SQLException;

    int searchCourseCount(String idCourse) throws SQLException;

    List<Course> getAllCourses() throws SQLException;

    Course getCourseById(String id) throws SQLException;

    int modifyCourse(Course course, int professorId, String courseId) throws SQLException;

    int enrollStudentToCourse(EnrollToCourse enrollToCourse) throws SQLException;

    List<EnrollToCourse> getEnrollId(String idCourse) throws SQLException;

    int expellStudentFromCourse(EnrollToCourse expellFromCourse) throws SQLException;

    int registerCourseTransaction(int professorId, Course course, List<EnrollToCourse> studentsList, int idSemester) throws SQLException;

    public List<Course> getCoursesPerStateAndProfessor(String state, int professorId) throws SQLException;

    int disableCourse(String courseId) throws SQLException;

    List<Course> getCoursebyStudent(int idStudent) throws SQLException;
}
