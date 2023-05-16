
package mx.uv.fei.sspger.logic.DAO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author miche
 */
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
        String email = "spiderman@uv.mx";
        String password = "miMimiau@12";
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.login(email, password);
        assertEquals(expResult, result);
    }

    /**
     * Test of isProfessor method, of class UserDAO.
     */
    @Test
    public void testIsProfessorSuccess() throws Exception {
        System.out.println("isProfessor");
        String email = "spiderman@uv.mx";
        UserDAO instance = new UserDAO();
        int expResult = 20;
        int result = instance.isProfessor(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of isStudent method, of class UserDAO.
     */
    @Test
    public void testIsStudentFail() throws Exception {
        System.out.println("isStudent");
        String email = "spiderman@uv.mx";
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
        int expResult = 0;
        int result = instance.usersAvailables();
        assertEquals(expResult, result);
    }

    /**
     * Test of professorPrivileges method, of class UserDAO.
     */
    @Test
    public void testProfessorPrivileges() throws Exception {
        System.out.println("professorPrivileges");
        int id = 20;
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.professorPrivileges(id);
        assertEquals(expResult, result);
    }
    
}
