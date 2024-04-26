package mx.uv.fei.sspger.logic.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.sspger.dataaccess.DataBaseManager;
import mx.uv.fei.sspger.logic.AcademicBody;
import mx.uv.fei.sspger.logic.Lgac;
import mx.uv.fei.sspger.logic.Professor;
import mx.uv.fei.sspger.logic.contracts.IProject;
import mx.uv.fei.sspger.logic.Project;
import mx.uv.fei.sspger.logic.ProjectStatus;

public class ProjectDAO implements IProject {

    private final String GET_PROJECT = "SELECT * FROM anteproyecto WHERE idAnteproyecto = ?";
    private final String GET_PROJECT_STATUS = "SELECT estadoAnteproyecto FROM anteproyecto WHERE idAnteproyecto = ?";
    private final String ADD_PROJECT_COMMAND = "insert into anteproyecto(idCuerpoAcademico, nombreProyecto, descripcion, resultadosEsperados, duracionAproximada, notas, requisitos, bibliografiaRecomendada, estadoAnteproyecto, cupo, modalidad, descripcionTrabajoRecepcional, nombrePladea_Fei, lineaInvestigacion) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE_PROJECT_COMMAND = "UPDATE anteproyecto SET idCuerpoAcademico = ?, nombreProyecto = ?, descripcion = ?, resultadosEsperados = ?, duracionAproximada = ?, notas = ?, requisitos = ?, bibliografiaRecomendada = ?, estadoAnteproyecto = ?, cupo = ?, modalidad = ?, descripcionTrabajoRecepcional = ?, nombrePladea_Fei = ?, lineaInvestigacion = ? WHERE (idAnteproyecto = ?)";
    private final String REMOVE_PROFESSOR_FROM_PROJECT = "DELETE FROM profesor_anteproyecto WHERE idAnteproyecto = ?";
    private final String REMOVE_LGAC_TO_PROJECT = "DELETE FROM lgac_anteproyecto WHERE idAnteproyecto = ?";
    private final String ADD_LGAC_TO_PROJECT = "insert into lgac_anteproyecto(idAnteproyecto, idlgac) values(?,?)";
    private final String ADD_PROFESSORS_TO_PROJECT = "insert into profesor_anteproyecto(idAnteproyecto, idUsuarioProfesor, rol) values (?,?,?)";
    private final String GET_AVAILABLE_PROJECTS_CARD = "SELECT a.idAnteproyecto, a.nombreProyecto, a.duracionAproximada, a.cupo, a.modalidad FROM anteproyecto a INNER JOIN profesor_anteproyecto pa ON a.idAnteproyecto = pa.idAnteproyecto WHERE a.estadoAnteproyecto = ? AND pa.idUsuarioProfesor = ? AND pa.rol = ?";
    private final String GET_PROJECTS_PER_ROLE_CARD = "SELECT anteproyecto.idAnteproyecto, nombreProyecto, duracionAproximada, cupo, modalidad, estadoAnteproyecto FROM anteproyecto INNER JOIN profesor_anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.idUsuarioProfesor = ? AND profesor_anteproyecto.rol = ?";
    private final String GET_PROJECTS_BY_STATUS_PER_ROLE_FILTER = "SELECT anteproyecto.idAnteproyecto, nombreProyecto, duracionAproximada, cupo, modalidad, estadoAnteproyecto FROM anteproyecto INNER JOIN profesor_anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.idUsuarioProfesor = ? AND profesor_anteproyecto.rol = ? AND anteproyecto.estadoAnteproyecto = ?";
    private final String APPLY_TO_PROJECT = "INSERT INTO estudiante_anteproyecto(idEstudiante, idAnteproyecto, estadoEstudiante) values (?,?,?)";
    private final String COUNT_TOTAL_STUDENTS_SELECTED_BY_PROJECT = "SELECT COUNT(*) FROM sspger.estudiante_anteproyecto INNER JOIN sspger.anteproyecto ON estudiante_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE estadoEstudiante = ? AND idEstudiante = ? AND estadoAnteproyecto = ?";
    private final String COUNT_PROJECTS_BY_STATUS = "SELECT COUNT(*) FROM anteproyecto WHERE estadoAnteproyecto = ? AND idCuerpoAcademico = ?";
    private final String MOST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac INNER JOIN anteproyecto ON lgac_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE anteproyecto.idCuerpoAcademico = ? GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) DESC LIMIT 1";
    private final String LEAST_LGAC_USED = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac.idLGAC = lgac_anteproyecto.idlgac INNER JOIN anteproyecto ON lgac_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE anteproyecto.idCuerpoAcademico = ? GROUP BY lgac.idLGAC, lgac.nombre ORDER BY COUNT(*) ASC LIMIT 1";
    private final String MOST_MODALITY_USED = "SELECT modalidad, COUNT(*) AS count FROM anteproyecto WHERE anteproyecto.idCuerpoAcademico = ? GROUP BY modalidad ORDER BY count DESC LIMIT 1";
    private final String LEAST_MODALITY_USED = "SELECT modalidad, COUNT(*) AS count FROM anteproyecto WHERE anteproyecto.idCuerpoAcademico = ? GROUP BY modalidad ORDER BY count ASC LIMIT 1";
    private final String DIRECTOR_WITH_MOST_PROJECTS = "SELECT profesor.honorifico, profesor.nombre, profesor.apellido FROM profesor INNER JOIN profesor_anteproyecto ON profesor.idUsuarioProfesor = profesor_anteproyecto.idUsuarioProfesor INNER JOIN anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.rol = ? AND anteproyecto.idCuerpoAcademico = ? GROUP BY profesor.honorifico, profesor.nombre, profesor.apellido ORDER BY COUNT(*) DESC LIMIT 1";
    private final String DIRECTOR_WITH_LEAST_PROJECTS = "SELECT profesor.honorifico, profesor.nombre, profesor.apellido FROM profesor INNER JOIN profesor_anteproyecto ON profesor.idUsuarioProfesor = profesor_anteproyecto.idUsuarioProfesor INNER JOIN anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE profesor_anteproyecto.rol = ? AND anteproyecto.idCuerpoAcademico = ? GROUP BY profesor.honorifico, profesor.nombre, profesor.apellido ORDER BY COUNT(*) ASC LIMIT 1";
    private final String AVAILABLE_SPACES_BY_PROJECT = "SELECT (anteproyecto.cupo - (SELECT COUNT(*) FROM estudiante_anteproyecto WHERE estadoEstudiante = ? AND idAnteproyecto = ?))"
        + " AS cupo_disponible FROM anteproyecto WHERE anteproyecto.idAnteproyecto = ?";
    private final String GET_LGAC_BY_PROJECT = "SELECT lgac.idLGAC, lgac.nombre FROM lgac INNER JOIN lgac_anteproyecto ON lgac_anteproyecto.idlgac = lgac.idLGAC"
        + " WHERE lgac_anteproyecto.idAnteproyecto = ?";
    private final String GET_ACADEMIC_BODY_BY_PROJECT = "SELECT * FROM cuerpo_academico INNER JOIN anteproyecto ON anteproyecto.idCuerpoAcademico = cuerpo_academico.idCuerpoAcademico WHERE anteproyecto.idAnteproyecto = ?";
    private final String EXISTENCE_APPLICATION_TO_PROJECT = "SELECT COUNT(*) FROM estudiante_anteproyecto WHERE idEstudiante = ? AND idAnteproyecto = ? AND estadoEstudiante = ?";
    private final String UPDATE_PROJECT_STATUS = "UPDATE anteproyecto SET estadoAnteproyecto = ? WHERE idAnteproyecto = ?";
    private final String GET_PROJECTS_BY_ACADEMIC_BODY = "SELECT * FROM cuerpo_academico INNER JOIN anteproyecto ON anteproyecto.idCuerpoAcademico = cuerpo_academico.idCuerpoAcademico WHERE cuerpo_academico.idCuerpoAcademico = ?";
    private final String SEARCH_PROJECT_BY_STATUS_PER_NAME = "SELECT DISTINCT anteproyecto.idAnteproyecto, nombreProyecto, duracionAproximada, cupo, modalidad, estadoAnteproyecto FROM sspger.anteproyecto INNER JOIN sspger.profesor_anteproyecto ON profesor_anteproyecto.idAnteproyecto = anteproyecto.idAnteproyecto WHERE anteproyecto.nombreProyecto LIKE ? AND anteproyecto.estadoAnteproyecto = ?";
    private final String ADD_VALIDATED_REGISTER = "INSERT INTO valida (idUsuarioProfesor, idAnteproyecto)  values (?,?)";
    private final String COUNT_STUDENT_ACCEPTED_IN_PROJECTS = "SELECT COUNT(*) FROM estudiante_anteproyecto WHERE idEstudiante = ? AND estadoEstudiante = ?";
    private final String STATUS_VALIDATION = ProjectStatus.VALIDATED.getDisplayName();
    private final String STATUS_REJECTED = ProjectStatus.REJECTED.getDisplayName();
    private final String STATUS_STUDENT_ACCEPTED = "ACEPTADO";
    private final String STATUS_STUDENT_POSTULED = "POSTULADO";
    private final String DIRECTOR_ROLE = "Director";
    private final String CODIRECTOR_ROLE = "Codirector";
    private final int NOT_FOUND_INT = -1;
    private final String NOT_FOUND_STRING = "-1";
    private final int VALUE_BY_DEFAULT = 0;

    private Project setProjectData(ResultSet projectResult) throws SQLException {
        Project project = new Project();
        project.setIdProject(NOT_FOUND_INT);

        if (projectResult.next()) {
            project.setIdProject(projectResult.getInt("idAnteproyecto"));
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
            project.setInvestigationLine(projectResult.getString("lineaInvestigacion"));
            project.getAcademicBody().setKey(projectResult.getString("idCuerpoAcademico"));
            project.getAcademicBody().setName("Cuerpo académico");
        }

        return project;
    }

    /**
     *
     * @param project to set the data of a new project to register in the
     * database
     * @return an int (-1) if there's an error with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int addProjectTransaction(Project project) throws SQLException {
        int response = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement projectStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROJECT_COMMAND, Statement.RETURN_GENERATED_KEYS);

            projectStatement.setString(1, project.getAcademicBody().getKey());
            projectStatement.setString(2, project.getName());
            projectStatement.setString(3, project.getDescription());
            projectStatement.setString(4, project.getExpectedResults());
            projectStatement.setInt(5, project.getDuration());
            projectStatement.setString(6, project.getNotes());
            projectStatement.setString(7, project.getRequeriments());
            projectStatement.setString(8, project.getBibliography());
            projectStatement.setString(9, project.getStatus());
            projectStatement.setInt(10, project.getSpaces());
            projectStatement.setString(11, project.getModality());
            projectStatement.setString(12, project.getReceptionalWorkDescription());
            projectStatement.setString(13, project.getPladeaFeiName());
            projectStatement.setString(14, project.getInvestigationLine());

            response = projectStatement.executeUpdate();
            ResultSet rs = projectStatement.getGeneratedKeys();
            while (rs.next()) {
                response = rs.getInt(1);
            }

            if (response != VALUE_BY_DEFAULT) {
                PreparedStatement lgacStatement = DataBaseManager.getConnection().prepareStatement(ADD_LGAC_TO_PROJECT);
                List<Lgac> lgacList = project.getLgac();

                for (int i = 0; i < lgacList.size(); i++) {
                    lgacStatement.setInt(1, response);
                    lgacStatement.setString(2, lgacList.get(i).getId());
                    lgacStatement.executeUpdate();
                }

                PreparedStatement directorStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROFESSORS_TO_PROJECT);
                directorStatement.setInt(1, response);
                directorStatement.setInt(2, project.getDirector().getId());
                directorStatement.setString(3, DIRECTOR_ROLE);
                directorStatement.executeUpdate();

                PreparedStatement codirectorStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROFESSORS_TO_PROJECT);
                List<Professor> codirectorsList = project.getCodirectors();
                for (int i = 0; i < codirectorsList.size(); i++) {
                    codirectorStatement.setInt(1, response);
                    codirectorStatement.setInt(2, codirectorsList.get(i).getId());
                    codirectorStatement.setString(3, CODIRECTOR_ROLE);
                    codirectorStatement.executeUpdate();
                }
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = NOT_FOUND_INT;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param project to set the new data of a project in the database
     * @return an int (-1) if there's an error with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int modifyProjectTransaction(Project project) throws SQLException {
        int response = VALUE_BY_DEFAULT;
        DataBaseManager.getConnection().setAutoCommit(false);

        try {
            removeProfessorsFromProject(project.getIdProject());
            removeLgacFromProject(project.getIdProject());

            PreparedStatement projectStatement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_COMMAND, Statement.RETURN_GENERATED_KEYS);

            projectStatement.setString(1, project.getAcademicBody().getKey());
            projectStatement.setString(2, project.getName());
            projectStatement.setString(3, project.getDescription());
            projectStatement.setString(4, project.getExpectedResults());
            projectStatement.setInt(5, project.getDuration());
            projectStatement.setString(6, project.getNotes());
            projectStatement.setString(7, project.getRequeriments());
            projectStatement.setString(8, project.getBibliography());
            projectStatement.setString(9, project.getStatus());
            projectStatement.setInt(10, project.getSpaces());
            projectStatement.setString(11, project.getModality());
            projectStatement.setString(12, project.getReceptionalWorkDescription());
            projectStatement.setString(13, project.getPladeaFeiName());
            projectStatement.setString(14, project.getInvestigationLine());
            projectStatement.setInt(15, project.getIdProject());

            response = projectStatement.executeUpdate();

            if (response != VALUE_BY_DEFAULT) {
                PreparedStatement lgacStatement = DataBaseManager.getConnection().prepareStatement(ADD_LGAC_TO_PROJECT);
                List<Lgac> lgacList = project.getLgac();

                for (int i = 0; i < lgacList.size(); i++) {
                    lgacStatement.setInt(1, project.getIdProject());
                    lgacStatement.setString(2, lgacList.get(i).getId());
                    lgacStatement.executeUpdate();
                }

                PreparedStatement directorStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROFESSORS_TO_PROJECT);
                directorStatement.setInt(1, project.getIdProject());
                directorStatement.setInt(2, project.getDirector().getId());
                directorStatement.setString(3, DIRECTOR_ROLE);
                directorStatement.executeUpdate();

                PreparedStatement codirectorStatement = DataBaseManager.getConnection().prepareStatement(ADD_PROFESSORS_TO_PROJECT);
                List<Professor> codirectorsList = project.getCodirectors();
                for (int i = 0; i < codirectorsList.size(); i++) {
                    codirectorStatement.setInt(1, project.getIdProject());
                    codirectorStatement.setInt(2, codirectorsList.get(i).getId());
                    codirectorStatement.setString(3, CODIRECTOR_ROLE);
                    codirectorStatement.executeUpdate();
                }
            }

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            response = NOT_FOUND_INT;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }
        return response;
    }

    /**
     *
     * @param idStudent to refer the student to count the total of projects
     * where it's accepted
     * @return an int -1 if there's a problem, a positive integer if it's
     * success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int totalStudentAccepted(int idStudent) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(COUNT_STUDENT_ACCEPTED_IN_PROJECTS);

        statement.setInt(1, idStudent);
        statement.setString(2, STATUS_STUDENT_ACCEPTED);

        ResultSet result = statement.executeQuery();
        int totalStudentsAccepted = NOT_FOUND_INT;

        if (result.next()) {
            totalStudentsAccepted = result.getInt(1);
        }

        DataBaseManager.closeConnection();

        return totalStudentsAccepted;
    }

    /**
     *
     * @param idProject to refer the project to change its status to rejected
     * @param idProfessor to refer the professor that reject the project
     * @return an int -1 if there's a problem with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int rejectProject(int idProject, int idProfessor) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_STATUS);

            statement.setString(1, STATUS_REJECTED);
            statement.setInt(2, idProject);

            result += statement.executeUpdate();

            PreparedStatement statementValidate = DataBaseManager.getConnection().prepareStatement(ADD_VALIDATED_REGISTER);
            statementValidate.setInt(1, idProfessor);
            statementValidate.setInt(2, idProject);

            result += statementValidate.executeUpdate();

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            result = NOT_FOUND_INT;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return result;
    }

    /**
     *
     * @param idProject to refer the project to change its status to validated
     * @param idProfessor to refer the professor that validate the project
     * @return an int -1 if there's a problem with the transaction, a positive
     * integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int validateProject(int idProject, int idProfessor) throws SQLException {
        int result = VALUE_BY_DEFAULT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_STATUS);

            statement.setString(1, STATUS_VALIDATION);
            statement.setInt(2, idProject);

            result += statement.executeUpdate();

            PreparedStatement statementValidate = DataBaseManager.getConnection().prepareStatement(ADD_VALIDATED_REGISTER);
            statementValidate.setInt(1, idProfessor);
            statementValidate.setInt(2, idProject);

            result += statementValidate.executeUpdate();

            DataBaseManager.getConnection().commit();
        } catch (SQLException sqlException) {
            result = NOT_FOUND_INT;
            DataBaseManager.getConnection().rollback();
            throw new SQLException("Error en la transacción" + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return result;
    }

    private int removeProfessorsFromProject(int projectId) throws SQLException {
        int response = VALUE_BY_DEFAULT;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(REMOVE_PROFESSOR_FROM_PROJECT);
        statement.setInt(1, projectId);

        response = statement.executeUpdate();

        return response;
    }

    private int removeLgacFromProject(int projectId) throws SQLException {
        int response = VALUE_BY_DEFAULT;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(REMOVE_LGAC_TO_PROJECT);
        statement.setInt(1, projectId);

        response = statement.executeUpdate();

        return response;
    }

    /**
     *
     * @param idProfessor to refer the professor to get the available projects
     * related to him
     * @return a list of projects with the status available by professor
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<Project> getAvailableProjectsPerDirectorCard(int idProfessor) throws SQLException {
        DataBaseManager.getConnection();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_AVAILABLE_PROJECTS_CARD);

        statement.setString(1, STATUS_VALIDATION);
        statement.setInt(2, idProfessor);
        statement.setString(3, DIRECTOR_ROLE);

        ResultSet projectAvailableResult = statement.executeQuery();

        List<Project> projectAvailableList = new ArrayList<>();

        while (projectAvailableResult.next()) {
            Project project = new Project();

            project.setIdProject(projectAvailableResult.getInt("idAnteproyecto"));
            project.setName(projectAvailableResult.getString("nombreProyecto"));
            project.setDuration(projectAvailableResult.getInt("duracionAproximada"));
            project.setSpaces(projectAvailableResult.getInt("cupo"));
            project.setModality(projectAvailableResult.getString("modalidad"));
            projectAvailableList.add(project);
        }

        DataBaseManager.closeConnection();

        return projectAvailableList;
    }

    /**
     *
     * @param idProfessor to refer the professor to get the projects related to
     * him
     * @param role an String to refer the role that takes the professor with the
     * project
     * @return a list of projects
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<Project> getProjectsPerDirectorCard(int idProfessor, String role) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROJECTS_PER_ROLE_CARD);

        statement.setInt(1, idProfessor);
        statement.setString(2, role);

        ResultSet projectListResult = statement.executeQuery();

        List<Project> projectList = new ArrayList<>();

        while (projectListResult.next()) {
            Project project = new Project();

            project.setIdProject(projectListResult.getInt("idAnteproyecto"));
            project.setName(projectListResult.getString("nombreProyecto"));
            project.setDuration(projectListResult.getInt("duracionAproximada"));
            project.setSpaces(projectListResult.getInt("cupo"));
            project.setModality(projectListResult.getString("modalidad"));
            project.setStatus(projectListResult.getString("estadoAnteproyecto"));
            projectList.add(project);
        }

        DataBaseManager.closeConnection();

        return projectList;
    }

    /**
     *
     * @param idProfessor to refer the professor to get the projects related to
     * him
     * @param role an String to refer the role that takes the professor with the
     * project
     * @param status an String to refer the status of the project
     * @return a list of projects
     * @throws SQLException if there's an error in the database
     */
    @Override
    public List<Project> getProjectsByStatusPerDirectorCard(int idProfessor, String role, String status) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROJECTS_BY_STATUS_PER_ROLE_FILTER);

        statement.setInt(1, idProfessor);
        statement.setString(2, role);
        statement.setString(3, status);

        ResultSet projectListResult = statement.executeQuery();

        List<Project> projectList = new ArrayList<>();

        while (projectListResult.next()) {
            Project project = new Project();

            project.setIdProject(projectListResult.getInt("idAnteproyecto"));
            project.setName(projectListResult.getString("nombreProyecto"));
            project.setDuration(projectListResult.getInt("duracionAproximada"));
            project.setSpaces(projectListResult.getInt("cupo"));
            project.setModality(projectListResult.getString("modalidad"));
            project.setStatus(projectListResult.getString("estadoAnteproyecto"));
            projectList.add(project);
        }

        DataBaseManager.closeConnection();

        return projectList;
    }

    /**
     *
     * @param idProject to refer the project where the students will be related
     * @param idStudent to refer the student that will be related to the project
     * @return an int -1 if there's a problem with the transaction, a positive
     * integer (the generated key) if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int applyToProject(int idProject, int idStudent) throws SQLException {
        int response = NOT_FOUND_INT;

        try {
            DataBaseManager.getConnection().setAutoCommit(false);
            PreparedStatement projectStudentStatement = DataBaseManager.getConnection().prepareStatement(APPLY_TO_PROJECT, Statement.RETURN_GENERATED_KEYS);

            projectStudentStatement.setInt(1, idStudent);
            projectStudentStatement.setInt(2, idProject);
            projectStudentStatement.setString(3, STATUS_STUDENT_POSTULED);

            projectStudentStatement.executeUpdate();
            DataBaseManager.getConnection().commit();

            ResultSet rs = projectStudentStatement.getGeneratedKeys();
            while (rs.next()) {
                response = rs.getInt(1);
            }

        } catch (SQLException sqlException) {
            DataBaseManager.getConnection().rollback();
            response = NOT_FOUND_INT;
            throw new SQLException("Error en la transacción " + sqlException.getMessage());
        } finally {
            DataBaseManager.getConnection().close();
        }

        return response;
    }

    /**
     *
     * @param projectStatus an String that represents the status of the project
     * @param idCuerpoAcademico the id of the academic body to count the
     * projects related to it
     * @return a -1 if there is a problem, a positive integer if it's success
     * @throws SQLException if there's an error in the database
     */
    @Override
    public int getProjectsCountByStatus(String projectStatus, String idCuerpoAcademico) throws SQLException {
        int projectsCount = NOT_FOUND_INT;

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(COUNT_PROJECTS_BY_STATUS);

        statement.setString(1, projectStatus);
        statement.setString(2, idCuerpoAcademico);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            projectsCount = resultSet.getInt(1);
        }

        DataBaseManager.closeConnection();

        return projectsCount;
    }

    /**
     *
     * @param idCuerpoAcademico the id of an academic body to get the lgac most
     * used in it
     * @return a lgac, the id is setted with -1 if there is a problem
     * @throws SQLException if there's an error in the database
     */
    @Override
    public Lgac getLgacMostUsed(String idCuerpoAcademico) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(MOST_LGAC_USED);

        statement.setString(1, idCuerpoAcademico);

        ResultSet lgacResult = statement.executeQuery();

        Lgac lgac = new Lgac();
        lgac.setId(NOT_FOUND_STRING);

        if (lgacResult.next()) {
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
        }

        DataBaseManager.closeConnection();

        return lgac;
    }

    /**
     *
     * @param idCuerpoAcademico the id of an academic body to get the lgac least
     * used in it
     * @return a lgac, the id is setted with -1 if there is a problem
     * @throws SQLException if there's an error in the database
     */
    @Override
    public Lgac getLgacLeastUsed(String idCuerpoAcademico) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(LEAST_LGAC_USED);

        statement.setString(1, idCuerpoAcademico);

        ResultSet lgacResult = statement.executeQuery();

        Lgac lgac = new Lgac();
        lgac.setId(NOT_FOUND_STRING);

        if (lgacResult.next()) {
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
        }

        DataBaseManager.closeConnection();

        return lgac;
    }

    /**
     *
     * @param idCuerpoAcademico the id of an academic body to get the modality
     * most used in it
     * @return a String that represents the modality, if there's a problem
     * return -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public String getModalityMostUsed(String idCuerpoAcademico) throws SQLException {
        String modality = NOT_FOUND_STRING;

        PreparedStatement modalityStatement = DataBaseManager.getConnection().prepareStatement(MOST_MODALITY_USED);

        modalityStatement.setString(1, idCuerpoAcademico);

        ResultSet resultSet = modalityStatement.executeQuery();

        if (resultSet.next()) {
            modality = resultSet.getString("modalidad");
        }

        DataBaseManager.closeConnection();

        return modality;
    }

    /**
     *
     * @param idCuerpoAcademico the id of an academic body to get the modality
     * least used in it
     * @return a String that represents the modality, if there's a problem
     * return -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public String getModalityLeastUsed(String idCuerpoAcademico) throws SQLException {
        String modality = NOT_FOUND_STRING;

        PreparedStatement modalityStatement = DataBaseManager.getConnection().prepareStatement(LEAST_MODALITY_USED);
        modalityStatement.setString(1, idCuerpoAcademico);
        ResultSet resultSet = modalityStatement.executeQuery();

        if (resultSet.next()) {
            modality = resultSet.getString("modalidad");
        }

        DataBaseManager.closeConnection();

        return modality;
    }

    /**
     *
     * @param idProject the id of the project that will be return
     * @return a project, if there's a problem return the id of the project in
     * -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public Project getProject(int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROJECT);

        statement.setInt(1, idProject);

        ResultSet projectResult = statement.executeQuery();
        Project project = setProjectData(projectResult);

        return project;
    }

    /**
     *
     * @param idProject the id of the project that will return its status
     * @return an String that is the status of the project, if there's a problem
     * return -1
     * @throws SQLException if there's an error in the database
     */
    @Override
    public String getProjectStatus(int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROJECT_STATUS);
        String status = NOT_FOUND_STRING;

        statement.setInt(1, idProject);

        ResultSet result = statement.executeQuery();

        if (result.next()) {
            status = result.getString("estadoAnteproyecto");
        }

        return status;
    }

    /**
     *
     * @param idProject to search for a project in the database.
     * @return a list of Lgac related to the project, if project not found
     * return an empty list.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Lgac> getLgacByProject(int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_LGAC_BY_PROJECT);

        statement.setInt(1, idProject);

        ResultSet lgacResult = statement.executeQuery();
        List<Lgac> lgacList = new ArrayList<>();

        while (lgacResult.next()) {
            Lgac lgac = new Lgac();
            lgac.setId(lgacResult.getString("idLGAC"));
            lgac.setName(lgacResult.getString("nombre"));
            lgacList.add(lgac);
        }
        return lgacList;
    }

    /**
     * @param idProject to search for a project in the database.
     * @return an AcademicBody id and name related to the project.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public AcademicBody getAcademicBodyByProject(int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_ACADEMIC_BODY_BY_PROJECT);

        statement.setInt(1, idProject);

        ResultSet academicBodyResult = statement.executeQuery();
        AcademicBody academicBody = new AcademicBody();

        if (academicBodyResult.next()) {
            academicBody.setKey(academicBodyResult.getString("idCuerpoAcademico"));
            academicBody.setName(academicBodyResult.getString("nombre"));
        }

        return academicBody;
    }

    /**
     * @param idStudent to search for a student in the database.
     * @return a count of the total projects in which the student is postulated.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getTotalProjectSelectedByStudent(int idStudent) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(COUNT_TOTAL_STUDENTS_SELECTED_BY_PROJECT);

        statement.setString(1, STATUS_STUDENT_POSTULED);
        statement.setInt(2, idStudent);
        statement.setString(3, STATUS_VALIDATION);

        ResultSet totalStudentsChoicesResult = statement.executeQuery();
        int totalStudentsChoices = VALUE_BY_DEFAULT;

        if (totalStudentsChoicesResult.next()) {
            totalStudentsChoices = totalStudentsChoicesResult.getInt(1);
        }

        DataBaseManager.closeConnection();

        return totalStudentsChoices;
    }

    /**
     * @param idProject to search for a project in the database.
     * @return an int with the available spaces for a student to be added.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getAvailableSpacesByProject(int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(AVAILABLE_SPACES_BY_PROJECT);

        statement.setString(1, STATUS_STUDENT_ACCEPTED);
        statement.setInt(2, idProject);
        statement.setInt(3, idProject);

        ResultSet totalStudentsChoicesResult = statement.executeQuery();
        int availableSpaces = VALUE_BY_DEFAULT;

        if (totalStudentsChoicesResult.next()) {
            availableSpaces = totalStudentsChoicesResult.getInt(1);
        }

        DataBaseManager.closeConnection();

        return availableSpaces;
    }

    /**
     * @param idStudent to search for a student in the database.
     * @param idProject to search for a project in the database.
     * @return an int (1) if the student is postuled to that project (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int getExistenceApplicationToProject(int idStudent, int idProject) throws SQLException {
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(EXISTENCE_APPLICATION_TO_PROJECT);

        statement.setInt(1, idStudent);
        statement.setInt(2, idProject);
        statement.setString(3, STATUS_STUDENT_POSTULED);

        ResultSet existenceApplicationToProjectResult = statement.executeQuery();
        int existenceApplicationToProject = NOT_FOUND_INT;

        if (existenceApplicationToProjectResult.next()) {
            existenceApplicationToProject = existenceApplicationToProjectResult.getInt(1);
        }

        DataBaseManager.closeConnection();

        return existenceApplicationToProject;
    }

    /**
     * @param status to change the project status to this.
     * @param idProject to search for the project which status will be changed
     * in the database.
     * @return an int (1) if was succesful (0) if not.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public int changeProjectStatus(String status, int idProject) throws SQLException {
        int result = VALUE_BY_DEFAULT;
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(UPDATE_PROJECT_STATUS);

        statement.setString(1, status);
        statement.setInt(2, idProject);

        result = statement.executeUpdate();

        statement.close();

        DataBaseManager.closeConnection();

        return result;
    }

    /**
     * @param projectName to search for the name of a project in the database.
     * @param status to search for this project status in the database.
     * @return a List of projects that matched the search, if no match return an
     * empty list.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Project> getProjectsByStatusSearched(String projectName, String status) throws SQLException {
        List<Project> projectList = new ArrayList<>();
        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(SEARCH_PROJECT_BY_STATUS_PER_NAME);

        String searchedName = "%" + projectName + "%";
        statement.setString(1, searchedName);
        statement.setString(2, status);

        ResultSet projectResult = statement.executeQuery();

        while (projectResult.next()) {
            Project project = new Project();

            project.setIdProject(projectResult.getInt("idAnteproyecto"));
            project.setName(projectResult.getString("nombreProyecto"));
            project.setDuration(projectResult.getInt("duracionAproximada"));
            project.setSpaces(projectResult.getInt("cupo"));
            project.setModality(projectResult.getString("modalidad"));
            projectList.add(project);
        }

        DataBaseManager.closeConnection();

        return projectList;
    }

    /**
     * @param idAcademicBody id to search for the professor with most projects
     * in an academicBody.
     * @return the name of the professor with most projects postulated.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public String getProfessorWithMostProjects(String idAcademicBody) throws SQLException {
        String professorName = NOT_FOUND_STRING;

        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(DIRECTOR_WITH_MOST_PROJECTS);

        professorStatement.setString(1, DIRECTOR_ROLE);
        professorStatement.setString(2, idAcademicBody);

        ResultSet result = professorStatement.executeQuery();

        if (result.next()) {
            professorName = result.getString("honorifico") + " " + result.getString("nombre") + " " + result.getString("apellido");
        }

        DataBaseManager.closeConnection();

        return professorName;
    }

    /**
     *
     * @param idAcademicBody id to search for the professor with least projects
     * in an academicBody.
     * @return the name of the professor with least projects postulated.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public String getProfessorWithLeastProjects(String idAcademicBody) throws SQLException {
        String professorName = NOT_FOUND_STRING;

        PreparedStatement professorStatement = DataBaseManager.getConnection().prepareStatement(DIRECTOR_WITH_LEAST_PROJECTS);

        professorStatement.setString(1, DIRECTOR_ROLE);
        professorStatement.setString(2, idAcademicBody);

        ResultSet result = professorStatement.executeQuery();

        if (result.next()) {
            professorName = result.getString("honorifico") + " " + result.getString("nombre") + " " + result.getString("apellido");
        }

        DataBaseManager.closeConnection();

        return professorName;
    }

    /**
     * @param idAcademicBody id to search for the projects related to that
     * academicBody.
     * @return a list with the fields of the project, if no matches return an
     * empty list.
     * @throws SQLException if there's an error in the database.
     */
    @Override
    public List<Project> getProjectsByAcademicBody(String idAcademicBody) throws SQLException {
        List<Project> projectsByAcademicBody = new ArrayList<>();

        PreparedStatement statement = DataBaseManager.getConnection().prepareStatement(GET_PROJECTS_BY_ACADEMIC_BODY);

        statement.setString(1, idAcademicBody);

        ResultSet projectResult = statement.executeQuery();

        while (projectResult.next()) {
            Project project = new Project();
            project.setIdProject(projectResult.getInt("idAnteproyecto"));
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
            project.setInvestigationLine(projectResult.getString("lineaInvestigacion"));
            projectsByAcademicBody.add(project);
        }

        return projectsByAcademicBody;
    }
}
