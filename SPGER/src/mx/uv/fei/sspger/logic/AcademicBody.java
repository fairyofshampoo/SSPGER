package mx.uv.fei.sspger.logic;


public class AcademicBody {
    private String key;
    private String name;
    
    public void setKey(String key){
        this.key = key;
    }
    
    public String getKey(){
        return key;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public boolean equals(Object object){
        if(object instanceof AcademicBody){
            AcademicBody academicBody = (AcademicBody)object;
            
            return this.getKey().equals(academicBody.getKey()) && this.getName().equals(academicBody.getName());
        }else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return key + " " + name;
    }
}