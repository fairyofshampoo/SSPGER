package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import mx.uv.fei.sspger.logic.Feedback;
import mx.uv.fei.sspger.logic.ReceptionalWork;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqlExceptionTest {
    
    public SqlExceptionTest(){}
    
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
     * Test of responsibleExistence method, of class AcademicBodyDAO.
     */
    @Test(expected = SQLException.class)
    public void testResponsibleExistenceNoConnection() throws SQLException {
        System.out.println("responsibleExistence");
        String key = "UV-CA-214";
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.responsibleExistence(key);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getActiveReceptionalWorkByStudent method, of class ReceptionalWorkDAO.
     */
    @Test(expected = SQLException.class)
    public void testGetActiveReceptionalWorkByStudentNoConnection() throws Exception {
        System.out.println("getActiveReceptionalWorkByStudent");
        int idStudent = 3;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
        
        ReceptionalWork result = instance.getActiveReceptionalWorkByStudent(idStudent);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getActiveReceptionalWorkByStudent method, of class ReceptionalWorkDAO.
     */
    @Test(expected = SQLException.class)
    public void testGetRecepetionalWorkByIdNoConnection() throws Exception {
        System.out.println("getRecepetionalWorkById");
        int idReceptionalWork = 2;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        
        expResult.setIdReceptionalWork(2);
    }
    
    @Test (expected = SQLException.class)
    public void testUpdateFeedbackNoConnection() throws Exception {
        System.out.println("updateFeedback");
        Feedback feedback = new Feedback();
        feedback.setComment("Gran trabajo. Me parece que aún tienes áreas de oportunidad.");
        feedback.setId(5);
        SubmissionManagerDAO instance = new SubmissionManagerDAO();
        int expResult = 1;
        int result = instance.updateFeedback(feedback);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of professorPrivileges method, of class UserDAO.
     */
    @Test (expected = SQLException.class)
    public void testProfessorPrivilegesNoConnection() throws SQLException {
        System.out.println("professorPrivileges");
        int id = 1;
        UserDAO instance = new UserDAO();
        int expResult = 1;
        int result = instance.professorPrivileges(id);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of rejectProject method, of class ProjectDAO.
     */
    @Test(expected = SQLException.class)
    public void testRejectProjectNoConnection() throws SQLException {
        System.out.println("rejectProject");
        int idProject = 11;
        int idProfessor = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 2;
        int result = instance.rejectProject(idProject, idProfessor);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of validateProject method, of class ProjectDAO.
     */
    @Test(expected = SQLException.class)
    public void testValidateProjectNoConnection() throws SQLException {
        System.out.println("validateProject");
        int idProject = 11;
        int idProfessor = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 2;
        int result = instance.validateProject(idProject, idProfessor);
        assertEquals(expResult, result);
    }
}
