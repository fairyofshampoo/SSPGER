package mx.uv.fei.sspger.logic;

import java.util.List;


public class Project {
    private int idProject;
    private String name;
    private String description;
    private String expectedResults;
    private int duration;
    private int spaces;
    private String notes;
    private String requeriments;
    private String bibliography;
    private String status;
    private String modality;
    private String receptionalWorkDescription;
    private String pladeaFeiName;
    private Professor director;
    private List <Professor> codirectors;
    private AcademicBodyMember responsible;
    private AcademicBody academicBody;
    private List<Lgac> lgac;
     private List<Student> student;
    
    public Project(){}

    public String getReceptionalWorkDescription() {
        return receptionalWorkDescription;
    }

    public void setReceptionalWorkDescription(String receptionalWorkDescription) {
        this.receptionalWorkDescription = receptionalWorkDescription;
    }

    public String getPladeaFeiName() {
        return pladeaFeiName;
    }

    public void setPladeaFeiName(String pladeaFeiName) {
        this.pladeaFeiName = pladeaFeiName;
    }

    public Professor getDirector() {
        return director;
    }

    public void setDirector(Professor director) {
        this.director = director;
    }

    public List<Professor> getCodirectors() {
        return codirectors;
    }

    public void setCodirectors(List<Professor> codirectors) {
        this.codirectors = codirectors;
    }

    public AcademicBodyMember getResponsible() {
        return responsible;
    }

    public void setResponsible(AcademicBodyMember responsible) {
        this.responsible = responsible;
    }

    public AcademicBody getAcademicBody() {
        return academicBody;
    }

    public void setAcademicBody(AcademicBody academicBody) {
        this.academicBody = academicBody;
    }

    public List<Lgac> getLgac() {
        return lgac;
    }

    public void setLgac(List<Lgac> lgac) {
        this.lgac = lgac;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
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

}
