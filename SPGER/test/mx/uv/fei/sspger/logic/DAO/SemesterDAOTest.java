/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.uv.fei.sspger.logic.Semester;
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
        Date date = dateFormat.parse("2023-02-11");
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        Semester semester = new Semester();
        
        Semester expectedResult = new Semester();
        
        expectedResult.setSemesterId(2);
        semester.setStartDate(sqlDate);
        
        SemesterDAO instance = new SemesterDAO();
        Semester expResult = expectedResult;
        Semester result = instance.getSemesterPerStartDate(semester);
        
        System.out.println("=======" + result.getSemesterId() + " " + expResult.getSemesterId());
        assertEquals(expResult, result);
    }

    /**
     * Test of getAvailableSemesters method, of class SemesterDAO.
     */
    @Test
    public void testGetAvailableSemesters() throws Exception {
        System.out.println("getAvailableSemesters");
        SemesterDAO instance = new SemesterDAO();
        List<Semester> expResult = null;
        List<Semester> result = instance.getAvailableSemesters();
        assertEquals(expResult, result);

    }

    /**
     * Test of getSemesterPerId method, of class SemesterDAO.
     */
    @Test
    public void testGetSemesterPerId() throws Exception {
        System.out.println("getSemesterPerId");
        int semesterId = 2;
        SemesterDAO instance = new SemesterDAO();
        Semester expResult = new Semester();
        
        expResult.setSemesterId(2);
        
        Semester result = instance.getSemesterPerId(2);
        
        System.out.println("ayo wtf" + result.getSemesterId() + " " + expResult.getSemesterId());
        
        assertEquals(expResult, result);
    }


}
