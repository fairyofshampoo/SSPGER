/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;


public class CourseDAO implements ICourse{

    @Override
    public int registerCourse(Course course, int idSchollarYear, String idCourse) throws SQLException {
        int result;
        String query = "INSERT INTO curso(idCurso, idPeriodoEscolar, nombre, nrc, seccion, bloque, estado) values (?,?,?,?,?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, idCourse);
        statement.setInt(2, idSchollarYear);
        statement.setString(3, course.getName());
        statement.setString(4, course.getNrc());
        statement.setInt(5, course.getSection());
        statement.setInt(6, course.getBlock());
        statement.setString(7, course.getState());

        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public List<Course> getCoursesPerState(String state) throws SQLException {
        String query = "Select * From asignacion Where estado= ?";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, state);
        
        ResultSet coursesResult = statement.executeQuery();
        List<Course> coursesList = new ArrayList<>();
        
        while(coursesResult.next()){
            Course course = new Course();
            
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            coursesList.add(course);
        } 
        dataBaseManager.closeConnection();
        
        return coursesList;
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        String query = "Select * From curso";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet coursesResult = statement.executeQuery(query);
        List<Course> coursesList = new ArrayList<>();
        
        while(coursesResult.next()){
            Course course = new Course();
            
            course.setName(coursesResult.getString("nombre"));
            course.setNrc(coursesResult.getString("nrc"));
            course.setSection(coursesResult.getInt("seccion"));
            course.setState(coursesResult.getString("estado"));
            course.setBlock(coursesResult.getInt("bloque"));
            coursesList.add(course);
        } 
        dataBaseManager.closeConnection();
        
        return coursesList;
    }
    
    
}
