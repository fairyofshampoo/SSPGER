package mx.uv.fei.sspger.GUI;


import mx.uv.fei.sspger.logic.AssignmentDAO;
import mx.uv.fei.sspger.logic.Assignment;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseDAO;

public class SPGER {

    public static void main(String[] args) throws SQLException {
        Course course = new Course();
        CourseDAO courseDao = new CourseDAO();
        
        course.setName("CursoPrueba1");
        course.setBlock(1);
        course.setSection(2);
        course.setNrc("12345");
        course.setState("Activo");
        
        try{
            if(courseDao.registerCourse(course, 1, "CursoPrueba") > 0){
                System.out.println("Registro exitoso.");
            }else {
                System.out.println("Registro fallido.");
            }
        }catch (SQLException registerCourseError){
            System.out.println("Error al registrar el curso");
        }
        
        List<Course> courseList = new ArrayList<>();
        courseList = courseDao.getAllCourses();
        
        for(int i = 0 ; i < courseList.size() ; i++){
            System.out.println(courseList.get(i).getName());
        }
        
        List<Assignment> assignmentList = new ArrayList<>();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        assignmentList = assignmentDao.getAssignmentsPerProject(1);
        
        for(int i = 0 ; i < assignmentList.size() ; i++){
            System.out.println(assignmentList.get(i).getTitle());
        }
        
        Assignment assignment = new Assignment();
        assignment.setTitle("TercerAsignación");
        assignment.setDeadline(Timestamp.valueOf("2008-11-11 00:00:00"));
        assignment.setStartDate(Timestamp.valueOf("2008-11-11 00:00:00"));
        assignment.setDescription("Ejemplo de descripción");
        assignment.setPublicationDate(Timestamp.valueOf("2008-11-11 13:23:44"));
        
        
        try{
            if(assignmentDao.registerAssignment(assignment, "1", 1) > 0){
                System.out.println("Registro exitoso.");
            }else {
                System.out.println("Registro fallido.");
            }
        }catch (SQLException assignmentRegisterError){
            System.out.println("Error al registrar la asignación");
        }
    }
}
