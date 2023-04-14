/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

/**
 *
 * @author miche
 */
public class Project {
    private String name;
    private String description;
    private String expectedResults;
    private int duration;
    private String notes;
    private String requeriments;
    private String bibliography;
    
    public Project(){
        
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getExpectedResults(){
        return expectedResults;
    }
    public void setExpectedResults(String expectedResults){
        this.expectedResults = expectedResults;
    }
    public int getDuration(){
        return duration;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    public String getRequeriments(){
        return requeriments;
    }
    public void setRequeriments(String requeriments){
        this.requeriments = requeriments;
    }
    public String getBibliography(){
        return bibliography;
    }
    public void setBibliography(String bibliography){
        this.bibliography = bibliography;
    }
}
