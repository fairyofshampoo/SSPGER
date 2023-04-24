/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.GUI;

import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.DAO.StudentDAO;
import mx.uv.fei.sspger.logic.Student;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author mario
 */
public class NewClass {
    public static void main (String args[]) throws SQLException{
        StudentDAO studentDao = new StudentDAO();
        List<Student> studentList = new ArrayList<>();
        List<Student> prueba = new ArrayList<>();
        Student student = new Student();
        
        student.setRegistrationTag("ZS21931203");
        studentList.add(student);
        prueba.add(student);
        student.setRegistrationTag("ZS21931201");
        studentList.add(student);
        prueba.add(student);
        
        assertEquals(prueba, studentList);
        
    }
}
