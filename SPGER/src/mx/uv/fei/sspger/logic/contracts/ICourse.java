/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.sspger.logic.contracts;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.EnrollToCourse;

public interface ICourse {
    int registerCourse (Course course, int idSchollarYear) throws SQLException; 
    List<Course> getCoursesPerState(String state) throws SQLException;
    List<Course> getAllCourses () throws SQLException;
    Course getCourseByID (String id) throws SQLException;
    int modifyCourse (Course course, int idSemester, int professorId) throws SQLException;
    int enrollStudentToCourse (EnrollToCourse enrollToCourse) throws SQLException;
    int expellStudentFromCourse (EnrollToCourse expellFromCourse) throws SQLException;
    int enrollProfessorToCourse (int professorId, Course course) throws SQLException;
}
