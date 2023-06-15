package mx.uv.fei.sspger.logic;


public enum UserTypes {
    PROFESSOR("Profesor"), 
    STUDENT("Estudiante");
    
    private String displayName;

    UserTypes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
