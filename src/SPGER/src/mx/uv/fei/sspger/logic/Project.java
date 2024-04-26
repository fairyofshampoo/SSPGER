package mx.uv.fei.sspger.logic;

import java.util.ArrayList;
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
    private String investigationLine;
    private Professor director;
    private List <Professor> codirectors;
    private AcademicBodyMember responsible;
    private AcademicBody academicBody = new AcademicBody();
    private List<Lgac> lgac;
    private List<Student> student;
    
    private final String PROJECT_NAME_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,300}$";
    private final String PROJECT_DESCRIPTION_REGEX = "^.{1,5000}$";
    private final String EXPECTED_RESULTS_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}\\n]{1,1000}$";
    private final String NOTES_REGEX = "^.{1,1000}$";
    private final String REQUERIMENTS_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}\\n]{1,1000}$";
    private final String BIBLIOGRAPHY_REGEX = "^.{1,5000}$";
    private final String RECEPTIONAL_WORK_DESCRIPTION_REGEX = "^.{1,5000}$";
    private final String PLADEA_NAME_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,300}$";
    private final String INVESTIGATION_LINE_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,1000}$";

    
    public Project(){}

    public String getReceptionalWorkDescription() {
        return receptionalWorkDescription;
    }

    public void setReceptionalWorkDescription(String receptionalWorkDescription) {
        this.receptionalWorkDescription = receptionalWorkDescription;
        validateReceptionalWorkDescription(receptionalWorkDescription);
    }

    public String getPladeaFeiName() {
        return pladeaFeiName;
    }

    public void setPladeaFeiName(String pladeaFeiName) {
        this.pladeaFeiName = pladeaFeiName;
        validatePladeaName(pladeaFeiName);
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
        validateProjectName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        validateProjectDescription(description);
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults;
        validateExpectedResults(expectedResults);
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
        validateNotes(notes);
    }

    public String getRequeriments() {
        return requeriments;
    }

    public void setRequeriments(String requeriments) {
        this.requeriments = requeriments;
        validateRequeriments(requeriments);
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
        validateBibliography(bibliography);
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

    public String getInvestigationLine() {
        return investigationLine;
    }

    public void setInvestigationLine(String investigationLine) {
        this.investigationLine = investigationLine;
        validateInvestigationLine(investigationLine);
    }
    
    private void validateProjectName(String name){
        if (!name.matches(PROJECT_NAME_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateProjectDescription(String description){
        if (!description.matches(PROJECT_DESCRIPTION_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateExpectedResults(String expectedResults){
        if (!expectedResults.matches(EXPECTED_RESULTS_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateNotes(String notes){
        if (!notes.matches(NOTES_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateRequeriments(String requeriments){
        if (!requeriments.matches(REQUERIMENTS_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateBibliography(String bibliography){
        if (!bibliography.matches(BIBLIOGRAPHY_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateReceptionalWorkDescription(String receptionalWorkDescription){
        if (!receptionalWorkDescription.matches(RECEPTIONAL_WORK_DESCRIPTION_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validatePladeaName(String pladeaFeiName){
        if (!pladeaFeiName.matches(PLADEA_NAME_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    private void validateInvestigationLine(String investigationLine){
        if (!investigationLine.matches(INVESTIGATION_LINE_REGEX)) {
            throw new IllegalArgumentException();
        }
    }
    
    public void addCodirector(Professor codirector) {
        if (codirectors == null) {
            codirectors = new ArrayList<>();
        }
    
        codirectors.add(codirector);
    }
    
    public void addLgac(Lgac singleLgac) {
        if (lgac == null) {
            lgac = new ArrayList<>();
        }
    
        lgac.add(singleLgac);
    }
    
    @Override
    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
       final Project otherProject = (Project) object;
       
       return (this.name == null? otherProject.name == null : this.name.equals(otherProject.name))
           && (this.description == null? otherProject.description == null : this.description.equals(otherProject.description))
           && (this.expectedResults == null? otherProject.expectedResults == null : this.expectedResults.equals(otherProject.expectedResults))
           && (this.duration == otherProject.duration)
           && (this.modality == null? otherProject.modality == null : this.modality.equals(otherProject.modality))
           && (this.notes == null? otherProject.notes == null : this.notes.equals(otherProject.notes))
           && (this.requeriments == null? otherProject.requeriments == null : this.requeriments.equals(otherProject.requeriments))
           && (this.bibliography == null? otherProject.bibliography == null : this.bibliography.equals(otherProject.bibliography))
           && (this.status == null? otherProject.status == null : this.status.equals(otherProject.status))
           && (this.spaces == otherProject.spaces)&& (this.modality == null? otherProject.modality == null : this.modality.equals(otherProject.modality))
           && (this.receptionalWorkDescription == null? otherProject.receptionalWorkDescription == null : this.receptionalWorkDescription.equals(otherProject.receptionalWorkDescription))
           && (this.pladeaFeiName == null? otherProject.pladeaFeiName == null : this.pladeaFeiName.equals(otherProject.pladeaFeiName))
           && (this.investigationLine == null? otherProject.investigationLine == null : this.investigationLine.equals(otherProject.investigationLine));
    }
}