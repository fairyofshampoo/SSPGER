package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.uv.fei.sspger.logic.Semester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SemesterDAOTest {
    
    public SemesterDAOTest() {
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
     * Test of getSemesterPerStartDate method, of class SemesterDAO.
     */
    @Test
    public void testGetSemesterPerStartDate() throws Exception {
        System.out.println("getSemesterPerStartDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2022-07-15");
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        Semester semester = new Semester();
        
        Semester expectedResult = new Semester();
        
        expectedResult.setSemesterId(3);
        semester.setStartDate(sqlDate);
        
        SemesterDAO instance = new SemesterDAO();
        Semester expResult = expectedResult;
        Semester result = instance.getSemesterPerStartDate(semester);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getAvailableSemesters method, of class SemesterDAO.
     */
    @Test
    public void testGetAvailableSemesters() throws Exception {
        System.out.println("getAvailableSemesters");
        
        SemesterDAO instance = new SemesterDAO();
        Semester semester1 = new Semester();
        Semester semester2 = new Semester();
        Semester semester3 = new Semester();
        
        semester1.setSemesterId(3);
        semester2.setSemesterId(4);
        semester3.setSemesterId(5);
        
        List<Semester> expResult = new ArrayList<>();
        
        expResult.add(semester1);
        expResult.add(semester2);
        expResult.add(semester3);
        
        List<Semester> result = instance.getAvailableSemesters();     
        
        assertEquals(expResult, result);

    }

    /**
     * Test of getSemesterPerId method, of class SemesterDAO.
     */
    @Test
    public void testGetSemesterPerId() throws Exception {
        System.out.println("getSemesterPerId");
        
        int semesterId = 3;
        SemesterDAO instance = new SemesterDAO();
        Semester expResult = new Semester();
        
        expResult.setSemesterId(3);
        
        Semester result = instance.getSemesterPerId(3);
        
        assertEquals(expResult, result);
    }


}
