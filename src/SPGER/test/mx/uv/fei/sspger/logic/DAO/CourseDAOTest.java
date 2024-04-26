package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.Course;
import mx.uv.fei.sspger.logic.CourseStates;
import mx.uv.fei.sspger.logic.EnrollToCourse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseDAOTest {
    private final String INVALID_ID = "InvaId";

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

    @Test
    public void testGetCoursesPerState() throws SQLException {
        System.out.println("getCoursesPerState");
        String state = CourseStates.AVAILABLE.getCourseState();
        CourseDAO instance = new CourseDAO();
        List<Course> expResult = new ArrayList<>();
        
        Course courseOne = new Course();
        Course courseTwo = new Course();
        
        courseOne.manualSetOfCourseId("Exp5123454512");
        courseTwo.manualSetOfCourseId("Exp6124154411");
        
        expResult.add(courseOne);
        expResult.add(courseTwo);
        
        List<Course> result = instance.getCoursesPerState(state);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCourseByID method, of class CourseDAO.
     */
    @Test
    public void testGetCourseByIdSuccesfull() throws SQLException {
        System.out.println("getCourseById");
        String id = "Exp6124154411";
        CourseDAO instance = new CourseDAO();

        Course expResult = new Course();
        expResult.manualSetOfCourseId("Exp6124154411");

        Course result = instance.getCourseById(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getCourseByID method, of class CourseDAO.
     */
    @Test
    public void testGetCourseByIdUnsuccesfull() throws SQLException {
        System.out.println("getCourseByID");
        String id = "Expa12341441";
        CourseDAO instance = new CourseDAO();

        Course expResult = new Course();
        expResult.manualSetOfCourseId(INVALID_ID);

        Course result = instance.getCourseById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of enrollStudentToCourse method, of class CourseDAO.
     */
    @Test
    public void testEnrollStudentToCourseSuccesful() throws SQLException {
        System.out.println("enrollStudentToCourse");
        EnrollToCourse enrollToCourse = new EnrollToCourse();
        enrollToCourse.setStudentId(14);
        enrollToCourse.setCourseId("Pro5123424412");

        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.enrollStudentToCourse(enrollToCourse);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of expellStudentToCourse method, of class CourseDAO.
     */
    @Test
    public void testExpellStudentFromCourseSuccesful() throws SQLException {
        System.out.println("expellStudentFromCourse");
        EnrollToCourse expellFromCourse = new EnrollToCourse();

        expellFromCourse.setCourseId("Pro5123424412");
        expellFromCourse.setStudentId(14);

        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.expellStudentFromCourse(expellFromCourse);

        assertEquals(expResult, result);
    }

    /**
     * Test of searchCourseCount method, of class CourseDAO.
     */
    @Test
    public void testSearchCourseCountSuccesful() throws Exception {
        System.out.println("searchCourseCount");
        String idCourse = "Pro5123424412";
        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.searchCourseCount(idCourse);
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchCourseCountUnsuccesful() throws Exception {
        System.out.println("searchCourseCount");
        String idCourse = "Pro424412";
        CourseDAO instance = new CourseDAO();
        int expResult = 0;
        int result = instance.searchCourseCount(idCourse);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of modifyCourse method, of class CourseDAO.
     */
    @Test
    public void testModifyCourse() throws Exception {
        System.out.println("modifyCourse");
        Course course = new Course();
        CourseDAO instance = new CourseDAO();
        
        course.setBlock(5);
        course.setName("Proyecto Guiado");
        course.setSection(4);
        course.setNrc("52181");
        course.setSemesterId(4);
        course.setProfessorId(12);
        course.setCourseId();
        
        String courseId = course.getCourseId();
        
        int professorId = 3;
        int expResult = 0;
        int result = instance.modifyCourse(course, professorId, courseId);
        assertEquals(expResult, result);
    }

    /**
     * Test of registerCourseTransaction method, of class CourseDAO.
     */
    @Test
    public void testRegisterCourseTransaction() throws Exception {
        System.out.println("registerCourseTransaction");
        Course course = new Course();
        List<EnrollToCourse> studentsList = new ArrayList<>();
        CourseDAO instance = new CourseDAO();
        
        course.setBlock(4);
        course.setName("Proyecto Guiado");
        course.setSection(3);
        course.setNrc("52181");
        course.setSemesterId(4);
        course.setProfessorId(12);
        course.setCourseId();
        
        int expResult = 1;
        int result = instance.registerCourseTransaction(course.getProfessorId(), course, studentsList, course.getSemesterId());
        assertEquals(expResult, result);
    }

    /**
     * Test of getCoursesPerProfessor method, of class CourseDAO.
     */
    @Test
    public void testGetCoursesPerProfessorNoCourses() throws SQLException {
        System.out.println("getCoursesPerProfessorNoCourses");
        
        int professorId = 7;
        CourseDAO instance = new CourseDAO();
        
        List<Course> expResult = new ArrayList<>();
        List<Course> result = instance.getCoursesPerProfessor(professorId);
        assertEquals(expResult, result);
    }

    /**
     * Test of disableCourse method, of class CourseDAO.
     */
    @Test
    public void testDisableCourse() throws SQLException {
        System.out.println("disableCourse");
        String courseId = "Pro5123424412";
        
        CourseDAO instance = new CourseDAO();
        int expResult = 1;
        int result = instance.disableCourse(courseId);
        assertEquals(expResult, result);    
    }

    @Test
    public void testDisableCourseFailed() throws SQLException {
        System.out.println("disableCourseFailed");
        String courseId = "Exp1";
        
        CourseDAO instance = new CourseDAO();
        int expResult = 0;
        int result = instance.disableCourse(courseId);
        assertEquals(expResult, result);    
    }
    
    /**
     * Test of getCoursebyStudent method, of class CourseDAO.
     */
    @Test
    public void testGetCoursebyStudent() throws Exception {
        System.out.println("getCoursebyStudent");
        int idStudent = 16;
        CourseDAO instance = new CourseDAO();
        List<Course> expResult = new ArrayList<>();
        Course course = new Course();
        
        course.manualSetOfCourseId("Exp6124154411");
        
        expResult.add(course);
        
        List<Course> result = instance.getCoursebyStudent(idStudent);
        assertEquals(expResult, result);
    }
}
