package mx.uv.fei.sspger.logic.DAO;


import java.util.List;
import mx.uv.fei.sspger.logic.Professor;
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
public class ProfessorDAOTest {
    
    public ProfessorDAOTest() {
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
     * Test of addProfessor method, of class ProfessorDAO.
     */
    @Test
    public void testAddProfessor() throws Exception {
        System.out.println("addProfessor");
        Professor professor = null;
        ProfessorDAO instance = new ProfessorDAO();
        int expResult = 0;
        int result = instance.addProfessor(professor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProfessor method, of class ProfessorDAO.
     */
    @Test
    public void testGetProfessor() throws Exception {
        System.out.println("getProfessor");
        String email = "";
        ProfessorDAO instance = new ProfessorDAO();
        Professor expResult = null;
        Professor result = instance.getProfessor(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllProfessors method, of class ProfessorDAO.
     */
    @Test
    public void testGetAllProfessors() throws Exception {
        System.out.println("getAllProfessors");
        ProfessorDAO instance = new ProfessorDAO();
        List<Professor> expResult = null;
        List<Professor> result = instance.getAllProfessors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProfessor method, of class ProfessorDAO.
     */
    @Test
    public void testUpdateProfessor() throws Exception {
        System.out.println("updateProfessor");
        String email = "";
        Professor professor = null;
        ProfessorDAO instance = new ProfessorDAO();
        int expResult = 0;
        int result = instance.updateProfessor(email, professor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteProfessor method, of class ProfessorDAO.
     */
    @Test
    public void testDeleteProfessor() throws Exception {
        System.out.println("deleteProfessor");
        String email = "";
        ProfessorDAO instance = new ProfessorDAO();
        int expResult = 0;
        int result = instance.deleteProfessor(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProfessorByCourse method, of class ProfessorDAO.
     */
    @Test
    public void testGetProfessorByCourse() throws Exception {
        System.out.println("getProfessorByCourse");
        String courseId = "Exp212";
        ProfessorDAO instance = new ProfessorDAO();
        Professor expResult = new Professor();
        
        expResult.setPersonalNumber("111");
        

        System.out.println("Numero personal esperado " + expResult.getPersonalNumber());
        
        Professor result = instance.getProfessorByCourse(courseId);
        System.out.println("Numero personal del resultado " + result.getPersonalNumber());
        System.out.println("Numero personal esperado " + expResult.getPersonalNumber());
        assertEquals(expResult, result);
    }

    /**
     * Test of getProfessorById method, of class ProfessorDAO.
     */
    @Test
    public void testGetProfessorById() throws Exception {
        System.out.println("getProfessorById");
        int professorId = 3;
        ProfessorDAO instance = new ProfessorDAO();
        Professor expResult = new Professor();
        
        expResult.setPersonalNumber("111");
        
        Professor result = instance.getProfessorById(professorId);
        assertEquals(expResult, result);
    }
    
}
