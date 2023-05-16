package mx.uv.fei.sspger.logic.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.contracts.IProject;
import mx.uv.fei.sspger.logic.Project;


public class ProjectDAO implements IProject{
    private final String ADD_PROJECT_QUERY = "insert into anteproyecto(idLGAC, idCuerpoAcademico, nombreProyecto, descripcion, resultadosEsperados, duracionAproximada, notas, requisitos, bibliografiaRecomendada) values(?,?,?,?,?,?,?,?,?)";
    private final String GET_ALL_PROJECTS_QUERY = "SELECT * FROM anteproyecto";
    private final String APPLY_TO_PROJECT = "INSERT INTO estudiante_anteproyecto(idEstudiante, idAnteproyecto, estadoEstudiante) values (?,?,?)";
    private final String COUNT_PROJECTS_BY_STATUS = "SELECT COUNT(*) FROM anteproyecto WHERE estadoAnteproyecto = ?";
    private final String MOST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) DESC LIMIT 1";
    private final String LEAST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) ASC LIMIT 1";
    private final String STATUS_VALIDATION = "VALIDADO";
    private final String STATUS_STUDENT_POSTULED = "POSTULANTE";
    private final int ERROR_ADDITION = -1;
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
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        Statement statement = connection.createStatement();
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
        
        dataBaseManager.closeConnection();
        
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
    public List<Project> getAvailableProjects() throws SQLException {
        String query = "SELECT * FROM anteproyecto WHERE estadoAnteproyecto = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_VALIDATION);
        
        ResultSet projectAvailableResult = statement.executeQuery(); 
        
        List<Project> projectAvailableList = new ArrayList<>();
        
        while(projectAvailableResult.next()){
            Project project = new Project();
            project.setIdProject(projectAvailableResult.getInt("idAnteproyecto"));
            project.setIdLGAC(projectAvailableResult.getString("idLGAC"));
            project.setIdAcademicBody(projectAvailableResult.getString("idCuerpoAcademico")); 
            project.setName(projectAvailableResult.getString("nombreProyecto"));
            project.setDescription(projectAvailableResult.getString("descripcion"));
            project.setExpectedResults(projectAvailableResult.getString("resultadosEsperados"));
            project.setDuration(projectAvailableResult.getInt("duracionAproximada"));
            project.setNotes(projectAvailableResult.getString("notas"));
            project.setRequeriments(projectAvailableResult.getString("requisitos"));
            project.setBibliography(projectAvailableResult.getString("bibliografiaRecomendada"));
            project.setStatus(projectAvailableResult.getString("estadoAnteproyecto"));
            project.setSpaces(projectAvailableResult.getInt("cupo"));
            project.setModality(projectAvailableResult.getString("modalidad"));
 
            projectAvailableList.add(project);
        }
        
        statement.close();
        DataBaseManager.closeConnection();
        
        return projectAvailableList;
    }
    
    @Override
    public List<Project> getAvailableProjectCard() throws SQLException{
        String query = "SELECT idAnteproyecto, nombreProyecto, duracionAproximada, cupo, modalidad FROM anteproyecto WHERE estadoAnteproyecto = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_VALIDATION);
        
        ResultSet projectAvailableResult = statement.executeQuery(); 
        
        List<Project> projectAvailableList = new ArrayList<>();
        
        while(projectAvailableResult.next()){
            Project project = new Project();
            
            project.setIdProject(projectAvailableResult.getInt("idAnteproyecto"));
            project.setName(projectAvailableResult.getString("nombreProyecto"));
            project.setDuration(projectAvailableResult.getInt("duracionAproximada"));
            project.setSpaces(projectAvailableResult.getInt("cupo"));
            project.setModality(projectAvailableResult.getString("modalidad"));
            projectAvailableList.add(project);
        }
        
        statement.close();
        DataBaseManager.closeConnection();
        
        return projectAvailableList;
    }
    
    private final String DIRECTOR_ROLE = "Director";
    
    @Override
    public List<Project> getAvailableProjectsPerDirectorCard(int idProfessor) throws SQLException{
        String query = "SELECT a.idAnteproyecto, a.nombreProyecto, a.duracionAproximada, a.cupo, a.modalidad FROM anteproyecto a INNER JOIN profesor_anteproyecto pa ON a.idAnteproyecto = pa.idAnteproyecto WHERE a.estadoAnteproyecto = ? AND pa.idUsuarioProfesor = ? AND pa.rol = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_VALIDATION);
        statement.setInt(2, idProfessor);
        statement.setString(3, DIRECTOR_ROLE);
        
        ResultSet projectAvailableResult = statement.executeQuery(); 
        
        List<Project> projectAvailableList = new ArrayList<>();
        
        while(projectAvailableResult.next()){
            Project project = new Project();
            
            project.setIdProject(projectAvailableResult.getInt("idAnteproyecto"));
            project.setName(projectAvailableResult.getString("nombreProyecto"));
            project.setDuration(projectAvailableResult.getInt("duracionAproximada"));
            project.setSpaces(projectAvailableResult.getInt("cupo"));
            project.setModality(projectAvailableResult.getString("modalidad"));
            projectAvailableList.add(project);
        }
        
        statement.close();
        DataBaseManager.closeConnection();
        
        return projectAvailableList;
    }
    
    @Override
    public List<Project> getProjectsPerDirectorCard(int idProfessor) throws SQLException{
        String query = "SELECT anteproyecto.idAnteproyecto, nombreProyecto, duracionAproximada, cupo, modalidad, estadoAnteproyecto FROM anteproyecto INNER JOIN profesor_anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.idUsuarioProfesor = ? AND profesor_anteproyecto.rol = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProfessor);
        statement.setString(2, DIRECTOR_ROLE);
        
        ResultSet projectAvailableResult = statement.executeQuery(); 
        
        List<Project> projectAvailableList = new ArrayList<>();
        
        while(projectAvailableResult.next()){
            Project project = new Project();
            
            project.setIdProject(projectAvailableResult.getInt("idAnteproyecto"));
            project.setName(projectAvailableResult.getString("nombreProyecto"));
            project.setDuration(projectAvailableResult.getInt("duracionAproximada"));
            project.setSpaces(projectAvailableResult.getInt("cupo"));
            project.setModality(projectAvailableResult.getString("modalidad"));
            project.setStatus(projectAvailableResult.getString("estadoAnteproyecto"));
            projectAvailableList.add(project);
        }
        
        statement.close();
        DataBaseManager.closeConnection();
        
        return projectAvailableList;
    }
    
    @Override
    public int applyToProject(int idProject, int idStudent) throws SQLException{
        int response = ERROR_ADDITION;
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement projectStudentStatement = DataBaseManager.getConnection().prepareStatement(APPLY_TO_PROJECT, Statement.RETURN_GENERATED_KEYS);

            
            projectStudentStatement.setInt(1, idStudent);
            projectStudentStatement.setInt(2, idProject);
            projectStudentStatement.setString(3, STATUS_STUDENT_POSTULED);
            
            projectStudentStatement.executeUpdate();
            DataBaseManager.getConnection().commit();
            
            ResultSet rs = projectStudentStatement.getGeneratedKeys();
            while(rs.next()){
                response = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            DataBaseManager.getConnection().rollback();
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }
    
    public int acceptToProject(int idProject, int idStudent) throws SQLException{
        int response = ERROR_ADDITION;
        
        return response;
    }
    
    
    public int countStudentsProjectSelected(int idStudent) throws SQLException{
        String query = "SELECT COUNT(*) FROM estudiante_anteproyecto WHERE estadoEstudiante = ? AND idStudent = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_STUDENT_POSTULED);
        statement.setInt(2, idStudent);
        
        ResultSet totalStudentsChoicesResult = statement.executeQuery(); 
        
        totalStudentsChoicesResult.next();
        int totalStudentsChoices = totalStudentsChoicesResult.getInt(query);

        statement.close();
        DataBaseManager.closeConnection();
        
        return totalStudentsChoices;
    }
    
    //public List<Student> studentsByProject()

    @Override
    public int getProjectsCountByStatus(String projectStatus) throws SQLException {
    
    int projectsCount = 0;
    
    DataBaseManager.getConnection();
    PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(COUNT_PROJECTS_BY_STATUS);
    
    statement.setString(1, projectStatus);
    ResultSet resultSet = statement.executeQuery();
    
    if (resultSet.next()) {
        projectsCount = resultSet.getInt(1);
    }
    statement.close();
    DataBaseManager.closeConnection();
    
    return projectsCount;
    }

    @Override
    public Lgac getLgacMostUsed() throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(MOST_LGAC_USED);

        ResultSet lgacResult = statement.executeQuery();
        lgacResult.next();
        
        Lgac lgac = new Lgac();
        lgac.setId(lgacResult.getString("idLGAC"));
        lgac.setName(lgacResult.getString("nombre"));
        
        DataBaseManager.closeConnection();

        return lgac;
    }

    @Override
    public Lgac getLgacLeastUsed() throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(LEAST_LGAC_USED);

        ResultSet lgacResult = statement.executeQuery();
        lgacResult.next();
        
        Lgac lgac = new Lgac();
        lgac.setId(lgacResult.getString("idLGAC"));
        lgac.setName(lgacResult.getString("nombre"));
        
        DataBaseManager.closeConnection();

        return lgac;
    }
}