/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.sspger.logic;


import java.sql.SQLException;
import java.util.List;

public interface ICourse {
    int registerCourse (Course course, int idSchollarYear, String idCourse) throws SQLException; 
    List<Course> getCoursesPerState(String state) throws SQLException;
    List<Course> getAllCourses () throws SQLException;
}
