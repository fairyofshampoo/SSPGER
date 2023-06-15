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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    }
        
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }
    
    @Override
    public boolean equals (Object object){
               
        if(object == this){
            return true;
        }
        if (object == null){
            return false;
        }
        final ReceptionalWork other = (ReceptionalWork) object;
        return this.idReceptionalWork == other.getIdReceptionalWork();
        
    }
}
