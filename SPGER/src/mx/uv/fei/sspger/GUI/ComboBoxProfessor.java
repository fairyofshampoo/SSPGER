/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.GUI;

/**
 *
 * @author mario
 */
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

