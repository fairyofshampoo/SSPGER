package mx.uv.fei.sspger.logic.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.contracts.IProject;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Student;


public class ProjectDAO implements IProject{
    private final String ADD_PROJECT_QUERY = "insert into anteproyecto(idLGAC, idCuerpoAcademico, nombreProyecto, descripcion, resultadosEsperados, duracionAproximada, notas, requisitos, bibliografiaRecomendada) values(?,?,?,?,?,?,?,?,?)";
    private final String GET_ALL_PROJECTS_QUERY = "SELECT * FROM anteproyecto";
    private final String GET_STUDENT_PROJECT = "SELECT idAnteproyecto, nombreProyecto FROM estudiante_anteproyecto NATURAL JOIN anteproyecto WHERE idEstudianteAnteproyecto = ?";
    
    @Override
    public int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException {
    int result;
    
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(ADD_PROJECT_QUERY);
        
        statement.setString(1, idLgac);
        statement.setString(2, idCuerpoAcademico);
        statement.setString(3, project.getName());
        statement.setString(4, project.getDescription());
        statement.setString(5, project.getExpectedResults());
        statement.setInt(6, project.getDuration());
        statement.setString(7, project.getNotes());
        statement.setString(8, project.getRequeriments());
        statement.setString(9, project.getBibliography());
        
        result = statement.executeUpdate();
        
        dataBaseManager.closeConnection();
        
        return result;
    }

    @Override
    public List<Project> getAllProjects() throws SQLException {
        DataBaseManager.getConnection();
        Statement statement = DataBaseManager.getConnection().createStatement();
        ResultSet projectResult = statement.executeQuery(GET_ALL_PROJECTS_QUERY);
        
        List<Project> projectList = new ArrayList<>();
        
        while(projectResult.next()){
            Project project = new Project();
            project.setIdProject(projectResult.getInt("idAnteproyecto"));
            project.setIdLGAC(projectResult.getString("idLGAC"));
            project.setStatus(projectResult.getString("estadoAnteproyecto"));
            project.setName(projectResult.getString("nombreProyecto"));
            project.setDescription(projectResult.getString("descripcion"));
            project.setExpectedResults(projectResult.getString("resultadosEsperados"));
            project.setDuration(projectResult.getInt("duracionAproximada"));
            project.setNotes(projectResult.getString("notas"));
            project.setRequeriments(projectResult.getString("requisitos"));
            project.setBibliography(projectResult.getString("bibliografiaRecomendada"));
            
            projectList.add(project);
        }
        
        DataBaseManager.closeConnection();
        
        return projectList;
    }

    @Override
    public Project getProject(String email) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int updateProject(int idProject, Project project) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int deleteProject(int idProject) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Project getProjectByStudent(int studentId) throws SQLException{
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_PROJECT);
        
        statement.setInt(1, studentId);
        
        Project project = new Project();
        
        ResultSet projectResult = statement.executeQuery();

        if(projectResult.next()){
            project.setName(projectResult.getString("nombreProyecto"));
            project.setIdProject(projectResult.getInt("idAnteproyecto"));
        }
        
        DataBaseManager.closeConnection();
        
        return project;
    }
}
