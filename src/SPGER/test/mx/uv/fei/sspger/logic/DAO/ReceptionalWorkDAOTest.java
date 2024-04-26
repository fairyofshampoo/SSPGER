package mx.uv.fei.sspger.logic.DAO;

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
        int idReceptionalWork = 15;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(idReceptionalWork);
        
        assertEquals(expResult, instance.getRecepetionalWorkById(idReceptionalWork));
    }
    
    /**
     * Test of getActiveReceptionalWorkByStudent method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetActiveReceptionalWorkByStudent() throws Exception {
        System.out.println("getActiveReceptionalWorkByStudent");
        int idStudent = 16;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(14);
        
        ReceptionalWork result = instance.getActiveReceptionalWorkByStudent(idStudent);
        assertEquals(expResult, result);
    }
}
