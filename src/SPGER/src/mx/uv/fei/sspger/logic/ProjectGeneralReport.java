package mx.uv.fei.sspger.logic;

public class ProjectGeneralReport extends Report {

    private int validatedProjects;
    private int rejectedProjects;
    private int proposedProjects;
    private int assignedProjects;
    private Lgac lgacMostUsed;
    private Lgac lgacLeastUsed;
    private String modalityMostUsed;
    private String modalityLeastUsed;
    private String directorMostProjects;
    private String directorLeastProjects;

    public int getValidatedProjects() {
        return validatedProjects;
    }

    public void setValidatedProjects(int validatedProjects) {
        this.validatedProjects = validatedProjects;
    }

    public int getRejectedProjects() {
        return rejectedProjects;
    }

    public void setRejectedProjects(int rejectedProjects) {
        this.rejectedProjects = rejectedProjects;
    }

    public int getAssignedProjects() {
        return assignedProjects;
    }

    public void setAssignedProjects(int assignedProjects) {
        this.assignedProjects = assignedProjects;
    }

    public int getProposedProjects() {
        return proposedProjects;
    }

    public void setProposedProjects(int proposedProjects) {
        this.proposedProjects = proposedProjects;
    }

    public String getModalityMostUsed() {
        return modalityMostUsed;
    }

    public void setModalityMostUsed(String modalityMostUsed) {
        this.modalityMostUsed = modalityMostUsed;
    }

    public String getModalityLeastUsed() {
        return modalityLeastUsed;
    }

    public void setModalityLeastUsed(String modalityLeastUsed) {
        this.modalityLeastUsed = modalityLeastUsed;
    }

    public Lgac getLgacMostUsed() {
        return lgacMostUsed;
    }

    public void setLgacMostUsed(Lgac lgacMostUsed) {
        this.lgacMostUsed = lgacMostUsed;
    }

    public Lgac getLgacLeastUsed() {
        return lgacLeastUsed;
    }

    public void setLgacLeastUsed(Lgac lgacLeastUsed) {
        this.lgacLeastUsed = lgacLeastUsed;
    }

    public String getDirectorMostProjects() {
        return directorMostProjects;
    }

    public void setDirectorMostProjects(String directorMostProjects) {
        this.directorMostProjects = directorMostProjects;
    }

    public String getDirectorLeastProjects() {
        return directorLeastProjects;
    }

    public void setDirectorLeastProjects(String directorLeastProjects) {
        this.directorLeastProjects = directorLeastProjects;
    }

}
