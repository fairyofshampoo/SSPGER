package mx.uv.fei.sspger.logic.DAO;


import java.util.List;
import mx.uv.fei.sspger.logic.Professor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class UserManagerDAOTest {
    
    public UserManagerDAOTest() {
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
    public void testUserProfessorAdditionTransaction() throws Exception {
        System.out.println("userProfessorAdditionTransaction");
        Professor professorAccount = new Professor();
        professorAccount.setEMail("miaumiau@uv.mx");
        professorAccount.setPassword("Miri0301");
        professorAccount.setPrivileges(1);
        professorAccount.setUserStatus(1);
        professorAccount.setName("Profesor de Prueba");
        professorAccount.setLastName("Noriega");
        professorAccount.setPersonalNumber("34X");
        professorAccount.setHonorificTitle("Dr.");
        
        UserManagerDAO instance = new UserManagerDAO();
        int expResult = 2;
        int result = instance.professorAdditionTransaction(professorAccount);
        assertEquals(expResult, result);
    }

    /**
     * Test of getActiveProfessors method, of class UserManagerDAO.
     */
    @Test
    public void testGetActiveProfessors() throws Exception {
        System.out.println("getActiveProfessors");
        UserManagerDAO instance = new UserManagerDAO();
        List<Professor> expResult = null;
        List<Professor> result = instance.getActiveProfessors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDisabledProfessors method, of class UserManagerDAO.
     */
    @Test
    public void testGetDisabledProfessors() throws Exception {
        System.out.println("getDisabledProfessors");
        UserManagerDAO instance = new UserManagerDAO();
        List<Professor> expResult = null;
        List<Professor> result = instance.getDisabledProfessors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userStudentAdditionTransaction method, of class UserManagerDAO.
     */
    @Test
    public void testUserStudentAdditionTransaction() throws Exception {
        System.out.println("userStudentAdditionTransaction");
        UserManagerDAO instance = new UserManagerDAO();
        int expResult = 0;
        int result = instance.studentAdditionTransaction();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
