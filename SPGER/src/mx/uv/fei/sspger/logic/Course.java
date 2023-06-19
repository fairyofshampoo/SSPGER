package mx.uv.fei.sspger.logic;

import java.util.Objects;


public class Course {
    private String courseId;
    private String name;
    private String nrc;
    private int section;
    private String state;
    private int block;
    private int semesterId;
    private int professorId;
    
    public Course () {}
    
    public void manualSetOfCourseId(String courseId){
        this.courseId = courseId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
    
    public void setCourseId(int courseId){
        this.courseId = createCourseId(semesterId);
    }
    
    public void setName (String name){
        this.name = name;
    }
    
    public void setNrc (String nrc){
        this.nrc = nrc;
    }
    
    public void setSection (int section){
        this.section = section;
    }
    
    public void setState (String state){
        this.state = state;
    }
    
    public void setBlock (int block){
        this.block = block;
    }
    
    public String getCourseId(){
        return this.courseId;
    }
    
    public String getName () {
        return this.name;
    }
    
    public String getNrc () {
        return this.nrc;
    }
    
    public int getSection () {
        return this.section;
    }
    
    public String getState () {
        return this.state;
    }
    
    public int getBlock () {
        return this.block;
    }
    
    public String createCourseId(int semesterId){
        return name.substring(0, 3) + section + block +semesterId;
    }
    
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Course other = (Course)object;
         return this.courseId.equals(other.courseId);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.courseId);
        return hash;
    }

}
