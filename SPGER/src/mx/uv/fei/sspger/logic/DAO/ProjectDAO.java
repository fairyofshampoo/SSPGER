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


public class ProjectDAO implements IProject{

    @Override
    public int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException {
    int result;
        String query = "insert into anteproyecto(idLGAC, idCuerpoAcademico, nombreProyecto, descripcion, resultadosEsperados, duracionAproximada, notas, requisitos, bibliografiaRecomendada) values(?,?,?,?,?,?,?,?,?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        
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
        String query = "SELECT * FROM anteproyecto";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
        ResultSet projectResult = statement.executeQuery(query);
        
        List<Project> projectList = new ArrayList<>();
        
        while(projectResult.next()){
            Project project = new Project();
            project.setName(projectResult.getString("nombreProyecto"));
            project.setDescription(projectResult.getString("descripcion"));
            project.setExpectedResults(projectResult.getString("resultadosEsperados"));
            project.setDuration(projectResult.getInt("duracionAproximada"));
            project.setNotes(projectResult.getString("notas"));
            project.setRequeriments(projectResult.getString("requisitos"));
            project.setBibliography(projectResult.getString("bibliografiaRecomendada"));
            
            projectList.add(project);
        }
        
        dataBaseManager.closeConnection();
        
        return projectList;
    }
    
}
