package mx.uv.fei.sspger.logic;

public class ReceptionalWork {

    private int idReceptionalWork;
    private int idFile;
    private int idProject;
    private String name;
    private String description;
    private String modality;
    private String results;
    private int space;
    private String state;

    private final String RECEPTIONAL_WORK_NAME_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,300}$";
    private final String RECEPTIONAL_WORK_DESCRIPTION_REGEX = "^.{1,5000}$";
    private final String RESULTS_REGEX = "^(?!.*\\s{2})[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\d\\s\\p{Punct}]{1,1000}$";

    public int getIdReceptionalWork() {
        return idReceptionalWork;
    }

    public void setIdReceptionalWork(int idReceptionalWork) {
        this.idReceptionalWork = idReceptionalWork;
    }

    public int getIdArchivo() {
        return idFile;
    }

    public void setIdArchivo(int idFile) {
        this.idFile = idFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        validateName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        validateDescription(description);
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
        validateResults(results);
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    private void validateName(String name) {
        if (!name.matches(RECEPTIONAL_WORK_NAME_REGEX)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDescription(String description) {
        if (!description.matches(RECEPTIONAL_WORK_DESCRIPTION_REGEX)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateResults(String results) {
        if (!results.matches(RESULTS_REGEX)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object object) {

        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        final ReceptionalWork other = (ReceptionalWork) object;
        return this.idReceptionalWork == other.getIdReceptionalWork();

    }
}
