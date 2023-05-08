package mx.uv.fei.sspger.logic.DAO;

import java.util.Date;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Submission;
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
        Date currentDate = new Date();
        java.sql.Date deliveryDate = new java.sql.Date(currentDate.getTime());
        Submission submission = new Submission();
        submission.setDescription("describiendo una descripción de prueba");
        submission.setDeliveryDate(deliveryDate);
        DeliverableFile file = new DeliverableFile();
        file.setName("alo");
        file.setExtension(".pdf");
        file.setPath("ruta/nueva/");
        int idAsignment = 11;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 1;
        int result = instance.addSubmission(submission, file, idAsignment);
        assertEquals(expResult, result);
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
    
}
