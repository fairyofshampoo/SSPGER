package mx.uv.fei.sspger.logic.DAO;


import java.util.List;
import java.sql.SQLException;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ReceptionalWorkDAOTest {
    
    public ReceptionalWorkDAOTest() {
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
     * Test of getRecepetionalWorkById method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetRecepetionalWorkById() throws Exception {
        System.out.println("getRecepetionalWorkById");
        int idReceptionalWork = 2;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
        
        assertEquals(SQLException.class, instance.getRecepetionalWorkById(idReceptionalWork));
    }
    
    @Test(expected = SQLException.class)
    public void testNoConnectionGetRecepetionalWorkById() throws Exception {
        System.out.println("getRecepetionalWorkById");
        int idReceptionalWork = 2;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
    }

    /**
     * Test of getActiveReceptionalWorkByStudent method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetActiveReceptionalWorkByStudent() throws Exception {
        System.out.println("getActiveReceptionalWorkByStudent");
        int idStudent = 3;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
        
        ReceptionalWork result = instance.getActiveReceptionalWorkByStudent(idStudent);
        assertEquals(expResult, result);
    }

    @Test(expected = SQLException.class)
    public void testNoConnectionGetActiveReceptionalWorkByStudent() throws Exception {
        System.out.println("getActiveReceptionalWorkByStudent");
        int idStudent = 3;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
        
        ReceptionalWork result = instance.getActiveReceptionalWorkByStudent(idStudent);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getReceptionalWorksByStudent method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetReceptionalWorksByStudent() throws Exception {
        System.out.println("getReceptionalWorksByStudent");
        int idStudent = 0;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        List<ReceptionalWork> expResult = null;
        List<ReceptionalWork> result = instance.getReceptionalWorksByStudent(idStudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addReceptionalWork method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testAddReceptionalWork() throws Exception {
        System.out.println("addReceptionalWork");
        ReceptionalWork receptionalWork = null;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        int expResult = 0;
        int result = instance.addReceptionalWork(receptionalWork);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifyReceptionalWork method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testModifyReceptionalWork() throws Exception {
        System.out.println("modifyReceptionalWork");
        ReceptionalWork receptionalWork = null;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        int expResult = 0;
        int result = instance.modifyReceptionalWork(receptionalWork);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeReceptionalWorkState method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testChangeReceptionalWorkState() throws Exception {
        System.out.println("changeReceptionalWorkState");
        String receptionalWorkState = "";
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        int expResult = 0;
        int result = instance.changeReceptionalWorkState(receptionalWorkState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
