package mx.uv.fei.sspger.GUI;


public class ComboBoxProfessor {
    private String professorName;
    private int professorId;

    public ComboBoxProfessor(String professorName, int professorId) {
        this.professorName = professorName;
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public int getProfessorId() {
        return professorId;
    }

    @Override
    public String toString() {
        return professorName;
    }
}

