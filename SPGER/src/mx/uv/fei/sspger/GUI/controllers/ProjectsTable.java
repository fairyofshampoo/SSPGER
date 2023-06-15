package mx.uv.fei.sspger.GUI.controllers;

/**
 *
 * @author miche
 */
public class ProjectsTable {
    int identificator;
    String projectName;
    String directorName;
    String projectStatus;

    public ProjectsTable(int identificator, String projectName, String directorName, String projectStatus) {
        this.identificator = identificator;
        this.projectName = projectName;
        this.directorName = directorName;
        this.projectStatus = projectStatus;
    }

    public int getIdentificator() {
        return identificator;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }
    
   
}
