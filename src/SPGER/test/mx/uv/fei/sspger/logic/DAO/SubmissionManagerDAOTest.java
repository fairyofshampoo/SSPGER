package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import mx.uv.fei.sspger.logic.DeliverableFile;
import mx.uv.fei.sspger.logic.Feedback;
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
     * Test of modifySubmission method, of class SubmissionManagerDAO.
     */
    @Test
    public void testModifySubmission() throws Exception {
        System.out.println("modifySubmission");
        Submission submission = new Submission();
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 2;
        int result = instance.modifySubmission(submission);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCountSubmissionsPerReceptionalWork method, of class SubmissionManagerDAO.
     */
    @Test
    public void testGetCountSubmissionsPerReceptionalWork() throws Exception {
        System.out.println("getCountSubmissionsPerReceptionalWork");
        int idReceptionalWork = 14;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 4;
        int result = instance.getCountSubmissionsPerReceptionalWork(idReceptionalWork);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubmissionPerAssignment method, of class SubmissionManagerDAO.
     */
    @Test
    public void testGetSubmissionPerAssignment() throws Exception {
        System.out.println("getSubmissionPerAssignment");
        int idAssignment = 20;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        Submission expResult = new Submission();
        expResult.setId(6);
        expResult.setDescription("Hola, buenas tardes, profesor. Adjunto mi evidencia sobre la revisión del estado del arte. Gracias de antemano.");
        Submission result = instance.getSubmissionPerAssignment(idAssignment);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFile method, of class SubmissionManagerDAO.
     */
    @Test
    public void testGetFile() throws Exception {
        System.out.println("getFile");
        int idDeliverableFile = 12;
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        DeliverableFile expResult = new DeliverableFile();
        expResult.setExtension("pdf");
        expResult.setName("EO_TamarizMorenoAnethMichelle_19.pdf");
        expResult.setPath("C:\\Users\\miche\\OneDrive - Universidad Veracruzana\\Cuarto semestre\\Principios de Construcción de Software\\SPGER\\SPGER/src/mx/uv/fei/sspger/files/");
        DeliverableFile result = instance.getFile(idDeliverableFile);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateFeedback method, of class SubmissionManagerDAO.
     */
    @Test
    public void testUpdateFeedback() throws Exception {
        System.out.println("updateFeedback");
        Feedback feedback = new Feedback();
        feedback.setComment("Gran trabajo. Me parece que aún tienes áreas de oportunidad.");
        feedback.setId(5);
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 1;
        int result = instance.updateFeedback(feedback);
        assertEquals(expResult, result);
    }
    
    
    
}
