package mx.uv.fei.sspger.logic.contracts;

import java.sql.SQLException;
import java.util.List;
import mx.uv.fei.sspger.logic.Project;


public interface IProject {
    int addProject(Project project, String idCuerpoAcademico, String idLgac) throws SQLException;
    List <Project> getAllProjects() throws SQLException;
}
