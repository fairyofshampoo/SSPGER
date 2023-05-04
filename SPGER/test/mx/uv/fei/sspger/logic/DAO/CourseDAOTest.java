/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic.DAO;


import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mario
 */
public class CourseDAOTest {
    
    public CourseDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerCourse method, of class CourseDAO.
     */
    @Test
    public void testRegisterCourse() throws SQLException {
        System.out.println("registerCourse");
        Course course = new Course();

        course.setName("CursoPrue12");
        course.setBlock(1);
        course.setSection(2);
        course.setNrc("12345");
        course.setState("Activo");
        course.setCourseId(2);
        
        int idSchollarYear = 1;
        CourseDAO instance = new CourseDAO();
        int expectedResult = 1;
        int result = instance.registerCourse(course, idSchollarYear);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetCoursesPerState() throws SQLException {
        System.out.println("getCoursesPerState");
        String state = "Activo";
        CourseDAO instance = new CourseDAO();
        List<Course> expResult = null;
        List<Course> result = instance.getCoursesPerState(state);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAllCourses method, of class CourseDAO.
     */
    @Test
    public void testGetAllCourses() throws SQLException {
        System.out.println("getAllCourses");
        CourseDAO instance = new CourseDAO();
        List<Course> expResult = null;
        List<Course> result = instance.getAllCourses();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCourseByID method, of class CourseDAO.
     */
    @Test
    public void testGetCourseByID() throws Exception {
        System.out.println("getCourseByID");
        String id = "Exp22";
        CourseDAO instance = new CourseDAO();
        
        Course expResult = new Course();
        expResult.manualSetOfCourseId("Exp22");
        expResult.setBlock(1);
        expResult.setName("Experiencia recepcional");
        expResult.setNrc("1234");
        expResult.setSection(2);
        expResult.setState("Disponible");
        instance.enrollProfessorToCourse(3, expResult);
        
        Course result = instance.getCourseByID(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of enrollStudentToCourse method, of class CourseDAO.
     */
    @Test
    public void testEnrollStudentToCourse() throws Exception {
        System.out.println("enrollStudentToCourse");
        EnrollToCourse enrollToCourse = new EnrollToCourse();
        enrollToCourse.setStudentId(3);
        enrollToCourse.setCourseId("CursoPrue21");
        
        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.enrollStudentToCourse(enrollToCourse);
        assertEquals(expResult, result);
    }

    @Test
    public void testExpellStudentFromCourse() throws Exception {
        System.out.println("expellStudentFromCourse");
        EnrollToCourse expellFromCourse = null;
        CourseDAO instance = new CourseDAO();
        int expResult = 0;
        int result = instance.expellStudentFromCourse(expellFromCourse);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifyCourse method, of class CourseDAO.
     */
    @Test
    public void testModifyCourse() throws Exception {
        System.out.println("modifyCourse");
        Course course = null;
        int idSemester = 0;
        int professorId = 0;
        CourseDAO instance = new CourseDAO();
        int expResult = 0;
        int result = instance.modifyCourse(course, idSemester, professorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enrollProfessorToCourse method, of class CourseDAO.
     */
    @Test
    public void testEnrollProfessorToCourse() throws Exception {
        System.out.println("enrollProfessorToCourse");
        int professorId = 3;
        Course course = new Course();
        course.manualSetOfCourseId("Exp42");
        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.enrollProfessorToCourse(professorId, course);
        assertEquals(expResult, result);
    }
    
}
