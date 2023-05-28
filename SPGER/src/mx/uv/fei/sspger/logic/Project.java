package mx.uv.fei.sspger.logic;


public class Project {
    private int idProject;
    private String idLGAC;
    private String idAcademicBody;
    private String name;
    private String description;
    private String expectedResults;
    private int duration;
    private int spaces;
    private String modality;
    private String notes;
    private String requeriments;
    private String bibliography;
    private String status;
    private ReceptionalWork receptionalWork;
    private Lgac lgac;
    private Professor professor;
    
    public Project(){
        
    }
    
    public int getSpaces() {
        return spaces;
    }

    public void setSpaces(int spaces) {
        this.spaces = spaces;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(String idLGAC) {
        this.idLGAC = idLGAC;
    }

    public String getIdAcademicBody() {
        return idAcademicBody;
    }

    public void setIdAcademicBody(String idAcademicBody) {
        this.idAcademicBody = idAcademicBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRequeriments() {
        return requeriments;
    }

    public void setRequeriments(String requeriments) {
        this.requeriments = requeriments;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public ReceptionalWork getReceptionalWork(){
        return receptionalWork;
    }
    
    public void setReceptionalWork(ReceptionalWork receptionalWork){
        this.receptionalWork = receptionalWork;
    }
    
    public Lgac getLgac() {
        return lgac;
    }

    public void setLgac(Lgac lgac) {
        this.lgac = lgac;
    }
    
    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }    

    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
       final Project otherProject = (Project) object;
       
       return (this.idAcademicBody == null? otherProject.idAcademicBody == null : this.idAcademicBody.equals(otherProject.idAcademicBody))
           && (this.name == null? otherProject.name == null : this.name.equals(otherProject.name))
           && (this.description == null? otherProject.description == null : this.description.equals(otherProject.description))
           && (this.expectedResults == null? otherProject.expectedResults == null : this.expectedResults.equals(otherProject.expectedResults))
           && (this.duration == otherProject.duration)
           && (this.notes == null? otherProject.notes == null : this.notes.equals(otherProject.notes))
           && (this.requeriments == null? otherProject.requeriments == null : this.requeriments.equals(otherProject.requeriments))
           && (this.bibliography == null? otherProject.bibliography == null : this.bibliography.equals(otherProject.bibliography))
           && (this.status == null? otherProject.status == null : this.status.equals(otherProject.status))
           && (this.spaces == otherProject.spaces)&& (this.modality == null? otherProject.modality == null : this.modality.equals(otherProject.modality));
    }
}