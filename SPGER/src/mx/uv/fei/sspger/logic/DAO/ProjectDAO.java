package mx.uv.fei.sspger.logic.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.Director;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.contracts.IProject;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.Student;


public class ProjectDAO implements IProject{
    private final String DIRECTOR_ROLE = "Director";
    private final String ADD_PROJECT_QUERY = "insert into anteproyecto(idLGAC, idCuerpoAcademico, nombreProyecto, descripcion, resultadosEsperados, duracionAproximada, notas, requisitos, bibliografiaRecomendada) values(?,?,?,?,?,?,?,?,?)";
    private final String GET_ALL_PROJECTS_QUERY = "SELECT * FROM anteproyecto";
    private final String GET_STUDENT_PROJECT = "SELECT idTrabajo_Recepcional, nombre FROM estudiante_trabajo_recepcional NATURAL JOIN trabajo_recepcional WHERE idEstudiante = ?";
    private final String APPLY_TO_PROJECT = "INSERT INTO estudiante_anteproyecto(idEstudiante, idAnteproyecto, estadoEstudiante) values (?,?,?)";
    private final String COUNT_PROJECTS_BY_STATUS = "SELECT COUNT(*) FROM anteproyecto WHERE estadoAnteproyecto = ?";
    private final String MOST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) DESC LIMIT 1";
    private final String LEAST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) ASC LIMIT 1";
    private final String MOST_MODALITY_USED = "SELECT modalidad, COUNT(*) AS count FROM anteproyecto GROUP BY modalidad ORDER BY count DESC LIMIT 1";
    private final String LEAST_MODALITY_USED = "SELECT modalidad, COUNT(*) AS count FROM anteproyecto GROUP BY modalidad ORDER BY count ASC LIMIT 1";
    private final String STATUS_VALIDATION = "VALIDADO";
    private final String STATUS_STUDENT_ACCEPTED = "ACEPTADO";
    private final String STATUS_STUDENT_POSTULED = "POSTULANTE";
    private final int ERROR_ADDITION = -1;
    private final int VALUE_BY_DEFAULT = 0;
    
    @Override
    public int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException {
    int result;
        Connection connection = DataBaseManager.getConnection();
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
        
        DataBaseManager.closeConnection();
        
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
    public Project getProjectByStudent(int studentId) throws SQLException{
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_STUDENT_PROJECT);
        
        statement.setInt(1, studentId);
        
        Project project = new Project();
        
        ResultSet projectResult = statement.executeQuery();

        if(projectResult.next()){
            project.setName(projectResult.getString("nombre"));
            project.setIdProject(projectResult.getInt("idTrabajo_Recepcional"));
        }
        
        DataBaseManager.closeConnection();
        
        return project;
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
    
    @Override
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
    
    @Override
    public int getProjectsCountByStatus(String projectStatus) throws SQLException {
        int projectsCount = 0;

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

    @Override
    public String getModalityMostUsed() throws SQLException {
    String modality = " ";
    
    PreparedStatement modalityStatement = DataBaseManager.getConnection().prepareStatement(MOST_MODALITY_USED);
    ResultSet resultSet = modalityStatement.executeQuery();
    
    if (resultSet.next()) {
        modality = resultSet.getString("modalidad");
    }
    modalityStatement.close();
    DataBaseManager.closeConnection();
    
    return modality;
    }

    @Override
    public String getModalityLeastUsed() throws SQLException {
        String modality = " ";
    
        PreparedStatement modalityStatement = DataBaseManager.getConnection().prepareStatement(LEAST_MODALITY_USED);
        ResultSet resultSet = modalityStatement.executeQuery();
    
        if (resultSet.next()) {
            modality = resultSet.getString("modalidad");
        }
        modalityStatement.close();
        DataBaseManager.closeConnection();
    
        return modality;
    }   
    
    @Override
    public Project getProject(int idProject) throws SQLException{
        String query = "SELECT * FROM anteproyecto WHERE idAnteproyecto = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        
        ResultSet projectResult = statement.executeQuery();
        
        Project project = new Project();
        
        if(projectResult.next()){
            project.setName(projectResult.getString("nombreProyecto"));
            project.setDescription(projectResult.getString("descripcion"));
            project.setExpectedResults(projectResult.getString("resultadosEsperados"));
            project.setDuration(projectResult.getInt("duracionAproximada"));
            project.setNotes(projectResult.getString("notas"));
            project.setRequeriments(projectResult.getString("requisitos"));
            project.setBibliography(projectResult.getString("bibliografiaRecomendada"));
            project.setStatus(projectResult.getString("estadoAnteproyecto"));
            project.setSpaces(projectResult.getInt("cupo"));
            project.setModality(projectResult.getString("modalidad"));
            project.setReceptionalWorkDescription(projectResult.getString("descripcionTrabajoRecepcional"));
            project.setPladeaFeiName(projectResult.getString("nombrePladea_Fei"));
            
        }
        
        return project;
    }
    
    @Override
    public List<Lgac> getLgacByProject(int idProject) throws SQLException{
        String query = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac_anteproyecto.idlgac = lgac.idLGAC"
            + " WHERE lgac_anteproyecto.idAnteproyecto = ?";
        
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        
        ResultSet lgacResult = statement.executeQuery();
        List<Lgac> lgacList = new ArrayList<>();
        
        while(lgacResult.next()){
            Lgac lgac = new Lgac();
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
            lgacList.add(lgac);
        }
        return lgacList;
    }
    
    @Override
    public AcademicBody getAcademicBodyByProject(int idProject) throws SQLException{
        String query = "SELECT * FROM cuerpo_academico INNER JOIN anteproyecto ON anteproyecto.idCuerpoAcademico = cuerpo_academico.idCuerpoAcademico WHERE anteproyecto.idAnteproyecto = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        
        ResultSet academicBodyResult = statement.executeQuery();
        AcademicBody academicBody = new AcademicBody();
        
        if(academicBodyResult.next()){
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
        }
        
        return academicBody;
    }
    
    @Override
    public List<Director> getDirectorsByProject(int idProject) throws SQLException{
        String query = "SELECT profesor_anteproyecto.idProfesorAnteproyecto, profesor_anteproyecto.rol, profesor.honorifico, profesor.nombre, profesor.apellido FROM profesor_anteproyecto"
            + " INNER JOIN anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto INNER JOIN profesor ON profesor_anteproyecto.idUsuarioProfesor = profesor.idUsuarioProfesor"
            + " WHERE anteproyecto.idAnteproyecto = ?";
        
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idProject);
        
        ResultSet directorResult = statement.executeQuery();
        List<Director> directorList = new ArrayList<>();
        
        while(directorResult.next()){
            Director director = new Director();
            director.setId(directorResult.getInt("idProfesorAnteproyecto"));
            director.setRole(directorResult.getString("rol"));
            director.setHonorificTitle(directorResult.getString("honorifico"));
            director.setName(directorResult.getString("nombre"));
            director.setLastName(directorResult.getString("apellido"));
            directorList.add(director);
        }
        return directorList;
    }
    
    @Override
    public int acceptToProject(int idProject, List<Student> studentList) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
            try{
                DataBaseManager.getConnection().setAutoCommit(false);
                
                for(int i = 0; i < studentList.size(); i++){
                    result += updateStudentStatusToAccepted(studentList.get(i).getId(), idProject);
                    result += deleteStudentPostuledToProject(studentList.get(i).getId());
                }
                DataBaseManager.getConnection().commit();
            }catch (SQLException ex) {
                DataBaseManager.getConnection().rollback();
            } finally {
                DataBaseManager.getConnection().close();
            }
        
        return result;
    }
    
    @Override
    public int expellStudent(int idProject, List<Student> studentList) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        
        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            
            for(int i = 0; i < studentList.size(); i++){
                String query = "DELETE FROM estudiante_anteproyecto WHERE idEstudiante = ? AND estadoEstudiante = ? AND idAnteproyecto = ?";
                PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);

                statement.setInt(1, studentList.get(i).getId());
                statement.setString(2, STATUS_STUDENT_ACCEPTED);
                statement.setInt(3, idProject);
                
                result += statement.executeUpdate();
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            DataBaseManager.getConnection().rollback();
        }finally{
            DataBaseManager.getConnection().close();
        }
        
        return result;
    }
    
    @Override
    public int getTotalProjectSelectedByStudent(int idStudent) throws SQLException{
        String query = "SELECT COUNT(*) FROM estudiante_anteproyecto WHERE estadoEstudiante = ? AND idEstudiante = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_STUDENT_POSTULED);
        statement.setInt(2, idStudent);
        
        ResultSet totalStudentsChoicesResult = statement.executeQuery();
        int totalStudentsChoices = VALUE_BY_DEFAULT;
        
        if(totalStudentsChoicesResult.next()){
            totalStudentsChoices = totalStudentsChoicesResult.getInt(1);
        }

        statement.close();
        DataBaseManager.closeConnection();
        
        return totalStudentsChoices;
    }
    
    @Override
    public int getAvailableSpacesByProject(int idProject) throws SQLException{
        String query = "SELECT (anteproyecto.cupo - (SELECT COUNT(*) FROM estudiante_anteproyecto WHERE estadoEstudiante = ? AND idAnteproyecto = ?))"
            + " AS cupo_disponible FROM anteproyecto WHERE anteproyecto.idAnteproyecto = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_STUDENT_ACCEPTED);
        statement.setInt(2, idProject);
        statement.setInt(3, idProject);
        
        ResultSet totalStudentsChoicesResult = statement.executeQuery();
        int availableSpaces = VALUE_BY_DEFAULT;
        
        if(totalStudentsChoicesResult.next()){
            availableSpaces = totalStudentsChoicesResult.getInt(1);
        }

        statement.close();
        DataBaseManager.closeConnection();
        
        return availableSpaces;
    }
    
    @Override
    public int getExistenceApplicationToProject(int idStudent, int idProject) throws SQLException{
        String query = "SELECT COUNT(*) FROM estudiante_anteproyecto WHERE idEstudiante = ? AND idAnteproyecto = ?";
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idStudent);
        statement.setInt(2, idProject);
        
        ResultSet existenceApplicationToProjectResult = statement.executeQuery();
        int existenceApplicationToProject = ERROR_ADDITION;
        
        if(existenceApplicationToProjectResult.next()){
            existenceApplicationToProject = existenceApplicationToProjectResult.getInt(1);
        }
        
        statement.close();
        DataBaseManager.closeConnection();
        
        return existenceApplicationToProject;
    }   
    
    @Override
    public int updateStudentStatusToAccepted(int idStudent, int idProject) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        String query = "UPDATE estudiante_anteproyecto SET estadoEstudiante = ? WHERE idEstudiante = ? AND idAnteproyecto = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, STATUS_STUDENT_ACCEPTED);
        statement.setInt(2, idStudent);
        statement.setInt(3, idProject);
        
        result = statement.executeUpdate();
        return result;
    }
    
    @Override
    public int deleteStudentPostuledToProject(int idStudent) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        String query = "DELETE FROM estudiante_anteproyecto WHERE idEstudiante = ? AND estadoEstudiante = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setInt(1, idStudent);
        statement.setString(2, STATUS_STUDENT_POSTULED);
        
        result = statement.executeUpdate();
        return result;
    }
    
    @Override
    public int changeProjectStatus(String status, int idAnteproyecto) throws SQLException{
        int result = VALUE_BY_DEFAULT;
        String query = "UPDATE anteproyecto SET estadoAnteproyecto = ? WHERE idAnteproyecto = ?";
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(query);
        
        statement.setString(1, status);
        statement.setInt(2, idAnteproyecto);
        
        result = statement.executeUpdate();
        DataBaseManager.closeConnection();
        return result;
    }
}
