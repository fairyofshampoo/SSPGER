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
        
        student.setEMail("miriam@uv.mx");
        student.setName("Miriam");
        student.setLastName("Ramírez Zárate");
        student.setRegistrationTag("zs21931203");
        student.setStatus(1);
        student.setPassword("Miri0301");
        
        StudentDAO studentDAO = new StudentDAO();
        
        int expResult = 2;
        int result = studentDAO.addStudentTransaction(student);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStudent() throws Exception {
        int id = 14;
        StudentDAO studentDAO = new StudentDAO();
        Student studentTest = studentDAO.getStudent(id);
        Student student = new Student();
        
        student.setEMail("zs21013859@estudiantes.uv.mx");
        student.setName("Aneth Michelle");
        student.setLastName("Tamariz Moreno");
        student.setRegistrationTag("zs21013859");
        student.setStatus(1);
        student.setId(14);
        Student expResult = student;
        Student result = studentTest;
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        System.out.println("updateStudent");
        String email = "miriam@uv.mx";
        Student student = new Student();
        StudentDAO studentDAO = new StudentDAO();
        
        student.setEMail("miriamramirez@uv.mx");
        student.setName("Miriam");
        student.setLastName("Ramírez Zárate");
        student.setRegistrationTag("zs21931203");
        student.setStatus(1);
        student.setId(18);
        
        int expResult = 2;
        int result = studentDAO.updateStudentTransaction(email, student);
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchStudentbyName() throws Exception {
        System.out.println("searchStudentbyName");
        String name = "Naomi";
        StudentDAO instance = new StudentDAO();
        Student student = new Student();
        student.setName("Naomi");
        student.setEMail("zs21013891@estudiantes.uv.mx");
        student.setLastName("Sánchez Chiquito");
        student.setId(16);
        student.setRegistrationTag("zs21013891");
        student.setStatus(1);
        List<Student> expResult = new ArrayList<>();
        expResult.add(student);
        List<Student> result = instance.searchStudentbyName(name);
        assertEquals(expResult, result);
    }


    
}
