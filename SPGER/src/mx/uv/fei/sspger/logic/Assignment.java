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
    private int idAdvancement;
    private int idProject;
    
    public Assignment () {}

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

    public int getIdAdvancement() {
        return idAdvancement;
    }

    public void setIdAdvancement(int idAdvancement) {
        this.idAdvancement = idAdvancement;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public void setTitle (String title){
        this.title = title;
    }
    
    public void setStartDate (Date startDate){
        this.startDate = startDate;
    }
    
    public void setDeadline (Date deadline){
        this.deadline = deadline;
    }
    
    public void setDescription (String description){
        this.description = description;
    }
    
    public void setPublicationDate (Date publicationDate){
        this.publicationDate = publicationDate;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public Date getStartDate () {
        return this.startDate;
    }
    
    public Date getDeadline () {
        return this.deadline;
    }
    
    public String getDescription () {
        return this.description;
    }
    
    public Date getPublicationDate () {
        return this.publicationDate;
    }
}