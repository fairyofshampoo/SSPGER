/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.sspger.logic;

import java.sql.Timestamp;

public class Assignment {
    private String title;
    private Timestamp startDate;
    private String description;
    private Timestamp deadline;
    private Timestamp publicationDate;
    
    public Assignment () {}

    public void setTitle (String title){
        this.title = title;
    }
    
    public void setStartDate (Timestamp startDate){
        this.startDate = startDate;
    }
    
    public void setDeadline (Timestamp deadline){
        this.deadline = deadline;
    }
    
    public void setDescription (String description){
        this.description = description;
    }
    
    public void setPublicationDate (Timestamp publicationDate){
        this.publicationDate = publicationDate;
    }
    
    public String getTitle () {
        return this.title;
    }
    
    public Timestamp getStartDate () {
        return this.startDate;
    }
    
    public Timestamp getDeadline () {
        return this.deadline;
    }
    
    public String getDescription () {
        return this.description;
    }
    
    public Timestamp getPublicationDate () {
        return this.publicationDate;
    }
}