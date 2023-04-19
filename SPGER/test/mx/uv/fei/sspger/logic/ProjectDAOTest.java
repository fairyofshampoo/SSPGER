/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import mx.uv.fei.sspger.logic.DAO.ProjectDAO;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import java.sql.SQLException;

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
    public void testAddProject() throws SQLException {
        System.out.println("addProject");
        Project project = new Project();
        project.setName("Nombre1");
        project.setDescription("Descripcion ejemplo");
        project.setExpectedResults("Resultados esperados ejemplo");
        project.setDuration(12);
        project.setNotes("Notas ejemplo");
        project.setRequeriments("Requisitos ejemplo");
        project.setBibliography("Bibliografia ejemplo");
        String idCuerpoAcademico = "1";
        String idLgac = "L2";
        
        ProjectDAO instance = new ProjectDAO();
        
        int expectedResult = 1;
        
        int result = instance.addProject(project, idCuerpoAcademico, idLgac);
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getAllProjects method, of class ProjectDAO.
     */
    @Test
    public void testGetAllProjects() throws SQLException {
        System.out.println("getAllProjects");
        ProjectDAO instance = new ProjectDAO();
        List<Project> expResult = null;
        List<Project> result = instance.getAllProjects();
        assertEquals(expResult, result);
    }
    
}
