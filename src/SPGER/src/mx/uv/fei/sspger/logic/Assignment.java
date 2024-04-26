package mx.uv.fei.sspger.logic;

import java.sql.Date;

public class Assignment {

    private int id;
    private String title;
    private Date startDate;
    private String description;
    private Date deadline;
    private Date publicationDate;
    private int professorId;
    private int idSubmission;
    private int idProject;

    public Assignment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getIdSubmission() {
        return idSubmission;
    }

    public void setIdSubmission(int idAdvancement) {
        this.idSubmission = idAdvancement;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getDeadline() {
        return this.deadline;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getPublicationDate() {
        return this.publicationDate;
    }

    public String getState() {
        java.util.Date now = new java.util.Date();
        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        java.util.Date utilDeadline = new java.util.Date(deadline.getTime());
        String state = "Finalizada";

        if (now.compareTo(utilDeadline) < 0) {
            state = "Disponible";
        }
        if (now.compareTo(utilStartDate) < 0) {
            state = "Por iniciar";
        }

        return state;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || (object.getClass() != this.getClass())) {
            return false;
        }
        final Assignment other = (Assignment) object;
        return this.id == other.id;
    }
}
