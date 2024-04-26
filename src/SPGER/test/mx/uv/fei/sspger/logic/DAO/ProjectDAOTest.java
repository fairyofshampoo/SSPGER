package mx.uv.fei.sspger.logic.DAO;

import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.Project;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
     * Test of addProjectTransaction method, of class ProjectDAO.
     */
    @Test
    public void testAddProjectTransaction() throws Exception {
        System.out.println("addProjectTransaction");
        Project project = new Project();
        ProjectDAO instance = new ProjectDAO();
        project.setName("Análisis de las tecnologías para desarrollo de Development Bots");
        project.getAcademicBody().setKey("UV-CA-127");
        project.setDescription("No aplica");
        project.setReceptionalWorkDescription("Los robots "
                + "de software o bots son aplicaciones de software autónomo que "
                + "se han utilizado para realizar tareas repetititivas o "
                + "simples. Actualmente, existe una importante investigación "
                + "al redor de bots sociales y de chat que interactúan con "
                + "personas para asistirlos con sus tareas. Por otro lado, "
                + "los bots se utilizan para automatizar tareas que realizan "
                + "los ingenieros de software y equipos de software en su "
                + "trabajo diario. Investigaciones recientes buscan que los "
                + "bots pueden ahorrar tiempo a los desarrolladores y aumentar "
                + "significativamente su productividad. En particular, se "
                + "busca que los bots asistan en las diferentes actividades de "
                + "la ingeniería de software.");
        project.setExpectedResults("Reporte de la revisión multivocal "
                + "de la literatura, paquete de artfactos producto de la "
                + "revisión, borrador de artículo");
        project.setDuration(6);
        project.setNotes("1) Casos excepcionales serán evaluados por la "
                + "Academia de ER. 2) Tratando de un CA externo a la "
                + "Licenciatura en Ingeniería de Software, el proyecto deberá "
                + "llevar el aval de los CA de la misma que se asocie con el "
                + "tema. 3) El Vo. Bo. del Responsable de CA se obtiene en "
                + "la reunión de cada CA, donde se presentan los temas del "
                + "mismo para su aprobación. 4) El Vo. Bo. de la Coordinación "
                + "de ER se obtiene en una reunión de la academia que se "
                + "programa para ello.");
        project.setRequeriments("Lectura de documentos en inglés, "
                + "trabajo autónomo, Interés por la investigación. "
                + "Diseño de Software. Tecnologías para la construcción, "
                + "Desarrollo de Sistemas en Red y Desarrollo de Aplicaciones");
        project.setBibliography("Salah, D. (2011, May). A framework "
                + "for the integration of user centered design and agile "
                + "software development processes. In Proceedings of the 33rd "
                + "International Conference on Software Engineering (pp. "
                + "1132-1133). Salah, D., Paige, R. F., & Cairns, P. (2014, "
                + "May). A systematic literature review for agile development "
                + "processes and user centred design integration. In "
                + "Proceedings of the 18th international conference on "
                + "evaluation and assessment in software engineering (pp. 1-10)"
                + ". Da Silva, T. S., Martin, A., Maurer, F., & Silveira, M. "
                + "(2011, August). User-centered design  and agile methods: a "
                + "systematic review. In 2011 AGILE conference (pp. 77-86). "
                + "IEEE. Argumanis Escalante, D. Propuesta de marco de trabajo "
                + "basado en la integración de Scrum y el diseño centrado en el"
                + " usuario para el proceso de desarrollo de software. "
                + "Sommerville, I. Software engineering 9th Edition. Pearson. "
                + "2011 Dix, A. J., Finlay, J., Abowd, G. D., & Beale, R. "
                + "Human- computer interaction. Pearson Education. 2003 Norman,"
                + " D. The design of everyday things: Revised and expanded "
                + "edition. Basic books. 2013 Alan Dix, Janet Finlay, Gregory "
                + "Abowd & Russell Beale. Human-Computer Interaction. 3rd "
                + "Edition. Prentice Hall, 2004. ISBN 0-13-046109-1.Soren "
                + "Lausen. User Interface design: A Software Engineering "
                + "perspective. Addison Wesley, 2005. Interaction Design beyond "
                + "human-computer interaction. Third Editiom WiIley. 2011. "
                + "Artículos científicos en temas de vanguardia en Experiencia "
                + "de Usuario de la IEEE, ACM y Springer.");
        project.setStatus("PROPUESTO");
        project.setSpaces(1);
        project.setModality("Revisión sistemática de la literatura");
        project.setPladeaFeiName("No aplica");
        project.setInvestigationLine("No aplica");
        Lgac lgac = new Lgac();
        lgac.setId("L2");
        project.addLgac(lgac);
        Professor director = new Professor();
        director.setId(11);
        project.setDirector(director);
        Professor codirector = new Professor();
        codirector.setId(13);
        project.addCodirector(codirector);
        int expResult = 13;
        int result = instance.addProjectTransaction(project);
        assertEquals(expResult, result);
    }

    /**
     * Test of modifyProjectTransaction method, of class ProjectDAO.
     */
    @Test
    public void testModifyProjectTransaction() throws Exception {
        System.out.println("modifyProjectTransaction");
        Project project = new Project();
        ProjectDAO instance = new ProjectDAO();
        project.setIdProject(13);
        project.setName("Análisis de las tecnologías para desarrollo de Development Bots");
        project.getAcademicBody().setKey("UV-CA-127");
        project.setDescription("No aplica");
        project.setReceptionalWorkDescription("Los robots "
                + "de software o bots son aplicaciones de software autónomo que "
                + "se han utilizado para realizar tareas repetititivas o "
                + "simples. Actualmente, existe una importante investigación "
                + "al redor de bots sociales y de chat que interactúan con "
                + "personas para asistirlos con sus tareas. Por otro lado, "
                + "los bots se utilizan para automatizar tareas que realizan "
                + "los ingenieros de software y equipos de software en su "
                + "trabajo diario. Investigaciones recientes buscan que los "
                + "bots pueden ahorrar tiempo a los desarrolladores y aumentar "
                + "significativamente su productividad. En particular, se "
                + "busca que los bots asistan en las diferentes actividades de "
                + "la ingeniería de software.");
        project.setExpectedResults("Reporte de la revisión multivocal "
                + "de la literatura, paquete de artfactos producto de la "
                + "revisión, borrador de artículo");
        project.setDuration(6);
        project.setNotes("1) Casos excepcionales serán evaluados por la "
                + "Academia de ER. 2) Tratando de un CA externo a la "
                + "Licenciatura en Ingeniería de Software, el proyecto deberá "
                + "llevar el aval de los CA de la misma que se asocie con el "
                + "tema. 3) El Vo. Bo. del Responsable de CA se obtiene en "
                + "la reunión de cada CA, donde se presentan los temas del "
                + "mismo para su aprobación. 4) El Vo. Bo. de la Coordinación "
                + "de ER se obtiene en una reunión de la academia que se "
                + "programa para ello.");
        project.setRequeriments("Lectura de documentos en inglés, "
                + "trabajo autónomo, Interés por la investigación. "
                + "Diseño de Software. Tecnologías para la construcción, "
                + "Desarrollo de Sistemas en Red y Desarrollo de Aplicaciones");
        project.setBibliography("Salah, D. (2011, May). A framework "
                + "for the integration of user centered design and agile "
                + "software development processes. In Proceedings of the 33rd "
                + "International Conference on Software Engineering (pp. "
                + "1132-1133). Salah, D., Paige, R. F., & Cairns, P. (2014, "
                + "May). A systematic literature review for agile development "
                + "processes and user centred design integration. In "
                + "Proceedings of the 18th international conference on "
                + "evaluation and assessment in software engineering (pp. 1-10)"
                + ". Da Silva, T. S., Martin, A., Maurer, F., & Silveira, M. "
                + "(2011, August). User-centered design  and agile methods: a "
                + "systematic review. In 2011 AGILE conference (pp. 77-86). "
                + "IEEE. Argumanis Escalante, D. Propuesta de marco de trabajo "
                + "basado en la integración de Scrum y el diseño centrado en el"
                + " usuario para el proceso de desarrollo de software. "
                + "Sommerville, I. Software engineering 9th Edition. Pearson. "
                + "2011 Dix, A. J., Finlay, J., Abowd, G. D., & Beale, R. "
                + "Human- computer interaction. Pearson Education. 2003 Norman,"
                + " D. The design of everyday things: Revised and expanded "
                + "edition. Basic books. 2013 Alan Dix, Janet Finlay, Gregory "
                + "Abowd & Russell Beale. Human-Computer Interaction. 3rd "
                + "Edition. Prentice Hall, 2004. ISBN 0-13-046109-1.Soren "
                + "Lausen. User Interface design: A Software Engineering "
                + "perspective. Addison Wesley, 2005. Interaction Design beyond "
                + "human-computer interaction. Third Editiom WiIley. 2011. "
                + "Artículos científicos en temas de vanguardia en Experiencia "
                + "de Usuario de la IEEE, ACM y Springer.");
        project.setStatus("PROPUESTO");
        project.setSpaces(1);
        project.setModality("Tesis");
        project.setPladeaFeiName("No aplica");
        project.setInvestigationLine("No aplica");
        Lgac lgac = new Lgac();
        lgac.setId("L1");
        project.addLgac(lgac);
        Professor director = new Professor();
        director.setId(11);
        project.setDirector(director);
        Professor codirector = new Professor();
        codirector.setId(13);
        project.addCodirector(codirector);
        int expResult = 1;
        int result = instance.modifyProjectTransaction(project);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProjectsCountByStatus method, of class ProjectDAO.
     */
    @Test
    public void testGetProjectsCountByStatus() throws Exception {
        System.out.println("getProjectsCountByStatus");
        String projectStatus = "VALIDADO";
        String idCuerpoAcademico = "UV-CA-127";
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.getProjectsCountByStatus(projectStatus, idCuerpoAcademico);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of rejectProject method, of class ProjectDAO.
     */
    @Test
    public void testRejectProject() throws SQLException {
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
    @Test
    public void testValidateProject() throws SQLException {
        System.out.println("validateProject");
        int idProject = 11;
        int idProfessor = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 2;
        int result = instance.validateProject(idProject, idProfessor);
        assertEquals(expResult, result);
    }
        
    /**
     * Test of totalStudentAccepted method, of class ProjectDAO.
     */
    @Test
    public void testTotalStudentAccepted() throws SQLException {
        System.out.println("totalStudentAccepted");
        int idStudent = 16;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 1;
        int result = instance.totalStudentAccepted(idStudent);
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of applyToProject method, of class ProjectDAO.
     */
    @Test
    public void testApplyToProject() throws SQLException {
        System.out.println("applyToProject");
        int idProject = 11;
        int idStudent = 18;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 72;
        int result = instance.applyToProject(idProject, idStudent);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getExistenceApplicationToProject method, of class ProjectDAO.
     */
    @Test
    public void testGetExistenceApplicationToProjectPostuled() throws SQLException {
        System.out.println("getExistenceApplicationToProject");
        int idStudent = 18;
        int idProject = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 1;
        int result = instance.getExistenceApplicationToProject(idStudent, idProject);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getExistenceApplicationToProject method, of class ProjectDAO.
     */
    @Test
    public void testGetExistenceApplicationToProjectNotPostuled() throws SQLException {
        System.out.println("getExistenceApplicationToProject");
        int idStudent = 18;
        int idProject = 10;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.getExistenceApplicationToProject(idStudent, idProject);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of changeProjectStatus method, of class ProjectDAO.
     */
    @Test
    public void testChangeProjectStatusToAssigned() throws SQLException {
        System.out.println("changeProjectStatus");
        String status = "ASIGNADO";
        int idProject = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 1;
        int result = instance.changeProjectStatus(status, idProject);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of changeProjectStatus method, of class ProjectDAO.
     */
    @Test
    public void testChangeProjectStatusToValidated() throws SQLException {
        System.out.println("changeProjectStatus");
        String status = "VALIDADO";
        int idProject = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 1;
        int result = instance.changeProjectStatus(status, idProject);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTotalProjectSelectedByStudent method, of class ProjectDAO.
     */
    @Test
    public void testGetTotalProjectSelectedByStudentFailed() throws SQLException {
        System.out.println("getTotalProjectSelectedByStudent");
        int idStudent = 18;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.getTotalProjectSelectedByStudent(idStudent);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getAvailableSpacesByProject method, of class ProjectDAO.
     */
    @Test
    public void testGetAvailableSpacesByProject() throws SQLException {
        System.out.println("getAvailableSpacesByProject");
        int idProject = 11;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 2;
        int result = instance.getAvailableSpacesByProject(idProject);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getAvailableSpacesByProject method, of class ProjectDAO.
     */
    @Test
    public void testGetAvailableSpacesByProjectNoSpaces() throws SQLException {
        System.out.println("getAvailableSpacesByProject");
        int idProject = 9;
        ProjectDAO instance = new ProjectDAO();
        int expResult = 0;
        int result = instance.getAvailableSpacesByProject(idProject);
        assertEquals(expResult, result);
    }
    
}
