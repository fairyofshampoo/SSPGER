package mx.uv.fei.sspger.logic;


public class Course {
    private String name;
    private String nrc;
    private int section;
    private String state;
    private int block;
    
    public Course () {}
    
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
}
