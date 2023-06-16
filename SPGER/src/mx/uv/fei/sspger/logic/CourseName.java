package mx.uv.fei.sspger.logic;


public enum CourseName {
    
    GUIDED_PROYECT("Proyecto Guiado"), 
    RECEPCIONAL_EXPERIENCE("Experiencia Recepcional");

    private final String courseName;

    CourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
