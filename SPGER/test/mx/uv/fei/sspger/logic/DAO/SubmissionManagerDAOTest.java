/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic.DAO;

import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Submission;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SubmissionManagerDAOTest {
    
    public SubmissionManagerDAOTest() {
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
     * Test of addSubmission method, of class SubmissionManagerDAO.
     */
    @Test
    public void testAddSubmission() throws Exception {
        System.out.println("addSubmission");
        Submission submission = null;
        DeliverableFile file = null;
        int idAsignment = 0;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 0;
        int result = instance.addSubmission(submission, file, idAsignment);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifySubmission method, of class SubmissionManagerDAO.
     */
    @Test
    public void testModifySubmission() throws Exception {
        System.out.println("modifySubmission");
        Submission submission = null;
        int idSubmission = 0;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 0;
        int result = instance.modifySubmission(submission, idSubmission);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCountSubmissionsPerReceptionalWork method, of class SubmissionManagerDAO.
     */
    @Test
    public void testGetCountSubmissionsPerReceptionalWork() throws Exception {
        System.out.println("getCountSubmissionsPerReceptionalWork");
        int idReceptionalWork = 2;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 1;
        
        int result = instance.getCountSubmissionsPerReceptionalWork(idReceptionalWork);
        assertEquals(expResult, result);
    }
    
}
