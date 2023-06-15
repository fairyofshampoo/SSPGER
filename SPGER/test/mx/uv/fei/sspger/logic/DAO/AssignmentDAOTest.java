/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic.DAO;

import mx.uv.fei.sspger.logic.DAO.AssignmentDAO;
import java.sql.Timestamp;
import java.util.List;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mario
 */
public class AssignmentDAOTest {
    
    public AssignmentDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of registerAssignment method, of class AssignmentDAO.
     */
    @Test
    public void testRegisterAssignment() throws Exception {
           
    }

    /**
     * Test of getAssignmentsPerProject method, of class AssignmentDAO.
     */
    @Test
    public void testGetAssignmentsPerProject() throws Exception {
        System.out.println("getAssignmentsPerProject");
        int idReceptionalWork = 1;
        AssignmentDAO instance = new AssignmentDAO();
        List<Assignment> expResult = null;
        List<Assignment> result = instance.getAssignmentsPerReceptionalWork(idReceptionalWork);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAssignment method, of class AssignmentDAO.
     */
    @org.junit.Test
    public void testUpdateAssignment() throws Exception {
        System.out.println("updateAssignment");
        Assignment assignment = new Assignment();
        
        Professor professor = new Professor();
        Project project = new Project();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        assignment = assignmentDao.getAssignmentById(15);
                
        assignment.setDescription("Test para ver si el DAO funciona.");
        assignment.setTitle("Titulo del test");
        
        professor.setId(1);    
        
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        int result = instance.updateAssignment(assignment);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteAssignment method, of class AssignmentDAO.
     */
    @org.junit.Test
    public void testDeleteAssignment() throws Exception {
        System.out.println("deleteAssignment");
        Assignment assignment = new Assignment();
        
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        assignment = assignmentDao.getAssignmentById(17);
          
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        int result = instance.deleteAssignment(assignment);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAssignmentById method, of class AssignmentDAO.
     */
    @org.junit.Test
    public void testGetAssignmentById() throws Exception {
        System.out.println("getAssignmentById");
        int assignmentId = 0;
        AssignmentDAO instance = new AssignmentDAO();
        Assignment expResult = null;
        Assignment result = instance.getAssignmentById(assignmentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
