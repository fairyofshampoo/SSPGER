package mx.uv.fei.sspger.logic.DAO;


import java.util.List;
import java.util.ArrayList;
import mx.uv.fei.sspger.logic.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class StudentDAOTest {
    
    public StudentDAOTest() {
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
    public void testAddStudent() throws Exception {
        System.out.println("addStudent");
        Student student = new Student();
        
        student.setEMail("Asd@uv.mx");
        student.setName("Mario");
        student.setLastName("Morales");
        student.setRegistrationTag("ZS21931203");
        
        StudentDAO studentDAO = new StudentDAO();
        
        int expResult = 1;
        int result = studentDAO.addStudentTransaction(student);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStudent() throws Exception {
        System.out.println("getStudent");
        String email = "Asd@uv.mx";
        StudentDAO studentDAO = new StudentDAO();
        Student studentTest = studentDAO.getStudent(email);
        Student student = new Student();
        
        student.setEMail("Asd@uv.mx");
        student.setName("Mario");
        student.setLastName("Morales");
        student.setRegistrationTag("ZS21931203");
        student.setId(1);
        
        Student expResult = student;
        Student result = studentTest;
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        System.out.println("getAllStudents");
        StudentDAO studentDAO = new StudentDAO();
        
        Student student1 = new Student();
        student1.setEMail("Asd@uv.mx");
        student1.setRegistrationTag("ZS21931203");
        
        Student student2 = new Student();
        student2.setEMail("miau@uv.mx");
        student2.setRegistrationTag("ZS2021");
        
        List<Student> expResult = new ArrayList<>();
        expResult.add(student1);
        expResult.add(student2);
        
        List<Student> result = studentDAO.getAllStudents();
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        System.out.println("updateStudent");
        String email = "miau@uv.mx";
        Student student = new Student();
        StudentDAO studentDAO = new StudentDAO();
        
        student.setEMail("miau@uv.mx");
        student.setName("Aneth Michelle");
        student.setLastName("Tamariz");
        student.setRegistrationTag("ZS2020");
        
        int expResult = 1;
        int result = studentDAO.updateStudentTransaction(email, student);
        assertEquals(expResult, result);
    }

    
}
