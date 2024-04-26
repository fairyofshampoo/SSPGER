package mx.uv.fei.sspger.logic.DAO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.*;

public class UserDAOTest {
    
    public UserDAOTest() {
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
     * Test of login method, of class UserDAO.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String email = "miriam@uv.mx";
        String password = "Miri0301";
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.login(email, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of isProfessor method, of class UserDAO.
     * @throws SQLException
     */
    @Test
    public void testIsProfessorSuccess() throws SQLException {
        System.out.println("isProfessor");
        String email = "jorge@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.isProfessor(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStudent method, of class UserDAO.
     */
    @Test
    public void testIsStudentFail() throws Exception {
        System.out.println("isStudent");
        String email = "jorge@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = -1;
        int result = instance.isStudent(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of usersAvailables method, of class UserDAO.
     */
    @Test
    public void testUsersAvailables() throws Exception {
        System.out.println("usersAvailables");
        UserDAO instance = new UserDAO();
        boolean expResult = true;
        boolean result = instance.usersAvailables();
        assertEquals(expResult, result);
    }

    
    
    @Test
    public void testProfessorPrivileges() throws SQLException {
        System.out.println("professorPrivileges");
        int id = 10;
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.professorPrivileges(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateStatus method, of class UserDAO.
     * @throws java.sql.SQLException
     */
    @Test
    public void testUpdateStatusSuccessful() throws SQLException {
        System.out.println("updateStatus");
        String email = "oalonso@uv.mx";
        int status = 0;
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.updateStatus(email, status);
        assertEquals(expResult, result);
    }

    /**
     * Test of isProfessor method, of class UserDAO.
     */
    @Test
    public void testIsProfessor() throws Exception {
        System.out.println("isProfessor");
        String email = "jorge@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = 11;
        int result = instance.isProfessor(email);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testIsProfessorFailed() throws Exception {
        System.out.println("isProfessor");
        String email = "naomi@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = -1;
        int result = instance.isProfessor(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStudent method, of class UserDAO.
     */
    @Test
    public void testIsStudent() throws Exception {
        System.out.println("isStudent");
        String email = "michelle@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.isStudent(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testIsStudentFailed() throws Exception {
        System.out.println("isStudent");
        String email = "ocharan@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = -1;
        int result = instance.isStudent(email);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of searchEmailDuplication method, of class UserDAO.
     */
    @Test
    public void testSearchEmailDuplication() throws SQLException {
        System.out.println("searchEmailDuplication");
        String email = "juaperez@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = 0;
        int result = instance.searchEmailDuplication(email);
        assertEquals(expResult, result);
    }
    
}
