package mx.uv.fei.sspger.logic.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.logic.Assignment;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssignmentDAOTest {
    
    public AssignmentDAOTest() {
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
     * Test of registerAssignment method, of class AssignmentDAO.
     */
    @Test
    public void testRegisterAssignment() throws Exception {
        System.out.println("getAssignmentsPerProject");
        Assignment assignment = new Assignment();
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        
        java.sql.Date date = new java.sql.Date(2023, 9, 1);
        
        assignment.setDeadline(date);
        assignment.setPublicationDate(date);
        assignment.setStartDate(date);
        assignment.setDescription("Se le solicita al alumno que suba su documento "
                + "relacionado al avance funcional del trabajo recepcional.");
        assignment.setIdProject(15);
        assignment.setProfessorId(10);
        assignment.setTitle("Documento de avance funcional");
        
        int result = instance.registerAssignment(assignment, assignment.getProfessorId());
        assertEquals(expResult, result);
    }

    /**
     * Test of registerAssignment method, of class AssignmentDAO.
     */
    @Test(expected = SQLException.class)
    public void testRegisterAssignmentException() throws Exception {
        System.out.println("getAssignmentsPerProject");
        Assignment assignment = new Assignment();
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        
        java.sql.Date date = new java.sql.Date(2023, 9, 1);
        
        assignment.setDeadline(date);
        assignment.setPublicationDate(date);
        assignment.setStartDate(date);
        assignment.setIdProject(13);
        assignment.setTitle("Asignación enfocada en el encuentro liberal del 30% del trabajo recepcional"
                + "en busqueda de una nueva manera de resolver los problemas enfocados en el trabajo unitiario"
                + "del desarrollo de computadoras y diseño de software");
        
        int result = instance.registerAssignment(assignment, assignment.getProfessorId());
        assertEquals(expResult, result);
    }    
    
    /**
     * Test of getAssignmentsPerProject method, of class AssignmentDAO.
     */
    @Test
    public void testGetAssignmentsPerProject() throws Exception {
        System.out.println("getAssignmentsPerProject");
        int idReceptionalWork = 15;
        AssignmentDAO instance = new AssignmentDAO();
        List<Assignment> expResult = new ArrayList<>();
        
        Assignment assignmentOne = new Assignment();
        Assignment assignmentTwo = new Assignment();
        
        assignmentOne.setId(19);
        assignmentTwo.setId(20);
        
        expResult.add(assignmentOne);
        expResult.add(assignmentTwo);
        
        List<Assignment> result = instance.getAssignmentsPerReceptionalWork(idReceptionalWork);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateAssignment method, of class AssignmentDAO.
     */
    @Test
    public void testUpdateAssignment() throws Exception {
        System.out.println("updateAssignment");
        Assignment assignment = new Assignment();
        
        Professor professor = new Professor();
        Project project = new Project();
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        assignment = assignmentDao.getAssignmentById(19);
                
        assignment.setDescription("Actividad del 20% enfocada en el avance procedural del trabaj"
                + "o recepcional. Se espera que se incluyan las diversas fuentes de información utilizadas");
        assignment.setTitle("Enfoque procedural del trabajo recepcional");
        
        professor.setId(1);    
        
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        int result = instance.updateAssignment(assignment);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteAssignment method, of class AssignmentDAO.
     */
    @Test
    public void testDeleteAssignment() throws Exception {
        System.out.println("deleteAssignment");
        Assignment assignment = new Assignment();
        
        AssignmentDAO assignmentDao = new AssignmentDAO();
        
        assignment = assignmentDao.getAssignmentById(24);
          
        AssignmentDAO instance = new AssignmentDAO();
        int expResult = 1;
        int result = instance.deleteAssignment(assignment);
        assertEquals(expResult, result);

    }

    /**
     * Test of getAssignmentById method, of class AssignmentDAO.
     */
    @Test
    public void testGetAssignmentById() throws Exception {
        System.out.println("getAssignmentById");
        int assignmentId = 19;
        AssignmentDAO instance = new AssignmentDAO();
        Assignment expResult = new Assignment();
        
        expResult.setId(19);
        
        Assignment result = instance.getAssignmentById(assignmentId);
        assertEquals(expResult, result);
    }

    
}
