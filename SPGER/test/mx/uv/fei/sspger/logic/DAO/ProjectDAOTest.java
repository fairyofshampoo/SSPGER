package mx.uv.fei.sspger.logic.DAO;

import java.util.List;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Project;
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
public class ProjectDAOTest {
    
    public ProjectDAOTest() {
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
     * Test of addProject method, of class ProjectDAO.
     */
    @Test
    public void testAddProject() throws Exception {
        System.out.println("addProject");
        Project project = null;
        String idCuerpoAcademico = "";
        String idLgac = "";
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.addProject(project, idCuerpoAcademico, idLgac);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllProjects method, of class ProjectDAO.
     */
    @Test
    public void testGetAllProjects() throws Exception {
        System.out.println("getAllProjects");
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getAllProjects();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProject method, of class ProjectDAO.
     */
    @Test
    public void testGetProject() throws Exception {
        System.out.println("getProject");
        String email = "";
        ProjectDAO instance = new ProjectDAO();
        Project expResult = null;
        Project result = instance.getProject(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProject method, of class ProjectDAO.
     */
    @Test
    public void testUpdateProject() throws Exception {
        System.out.println("updateProject");
        int idProject = 0;
        Project project = null;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.updateProject(idProject, project);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteProject method, of class ProjectDAO.
     */
    @Test
    public void testDeleteProject() throws Exception {
        System.out.println("deleteProject");
        int idProject = 0;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.deleteProject(idProject);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableProjects method, of class ProjectDAO.
     */
    @Test
    public void testGetAvailableProjects() throws Exception {
        System.out.println("getAvailableProjects");
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getAvailableProjects();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableProjectCard method, of class ProjectDAO.
     */
    @Test
    public void testGetAvailableProjectCard() throws Exception {
        System.out.println("getAvailableProjectCard");
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getAvailableProjectCard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAvailableProjectsPerDirectorCard method, of class ProjectDAO.
     */
    @Test
    public void testGetAvailableProjectsPerDirectorCard() throws Exception {
        System.out.println("getAvailableProjectsPerDirectorCard");
        int idProfessor = 0;
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getAvailableProjectsPerDirectorCard(idProfessor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProjectsPerDirectorCard method, of class ProjectDAO.
     */
    @Test
    public void testGetProjectsPerDirectorCard() throws Exception {
        System.out.println("getProjectsPerDirectorCard");
        int idProfessor = 0;
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getProjectsPerDirectorCard(idProfessor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyToProject method, of class ProjectDAO.
     */
    @Test
    public void testApplyToProject() throws Exception {
        System.out.println("applyToProject");
        int idProject = 0;
        int idStudent = 0;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.applyToProject(idProject, idStudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of acceptToProject method, of class ProjectDAO.
     */
    @Test
    public void testAcceptToProject() throws Exception {
        System.out.println("acceptToProject");
        int idProject = 0;
        int idStudent = 0;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.acceptToProject(idProject, idStudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countStudentsProjectSelected method, of class ProjectDAO.
     */
    @Test
    public void testCountStudentsProjectSelected() throws Exception {
        System.out.println("countStudentsProjectSelected");
        int idStudent = 0;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.countStudentsProjectSelected(idStudent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidatedProjectsCount method, of class ProjectDAO.
     */
    @Test
    public void testGetProjectsCountByStatus() throws Exception {
        System.out.println("getProjectsCountByStatus");
        String projectStatus = "RECHAZADO";
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.getProjectsCountByStatus(projectStatus);
        assertEquals(expResult, result);
    }

    /**
     * Test of getLgacMostUsed method, of class ProjectDAO.
     */
    @Test
    public void testGetLgacMostUsed() throws Exception {
        System.out.println("getLgacMostUsed");
        ProjectDAO instance = new ProjectDAO();
        Lgac expResult = null;
        Lgac result = instance.getLgacMostUsed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLgacLeastUsed method, of class ProjectDAO.
     */
    @Test
    public void testGetLgacLeastUsed() throws Exception {
        System.out.println("getLgacLeastUsed");
        ProjectDAO instance = new ProjectDAO();
        Lgac expResult = null;
        Lgac result = instance.getLgacLeastUsed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getModalityMostUsed method, of class ProjectDAO.
     */
    @Test
    public void testGetModalityMostUsed() throws Exception {
        System.out.println("getModalityMostUsed");
        ProjectDAO instance = new ProjectDAO();
        String expResult = "Monografía";
        String result = instance.getModalityMostUsed();
        assertEquals(expResult, result);
    }

    /**
     * Test of getModalityLeastUsed method, of class ProjectDAO.
     */
    @Test
    public void testGetModalityLeastUsed() throws Exception {
        System.out.println("getModalityLeastUsed");
        ProjectDAO instance = new ProjectDAO();
        String expResult = "Trabajo práctico-teórico";
        String result = instance.getModalityLeastUsed();
        assertEquals(expResult, result);
    }
    
}
