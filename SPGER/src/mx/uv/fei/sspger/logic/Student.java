package mx.uv.fei.sspger.logic;


public class Student extends User{
    
    private String registrationTag;
    private int id;
    
    public Student(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
 
    public String getRegistrationTag(){
        return registrationTag;
    }
    
    public void setRegistrationTag(String registrationTag){
        this.registrationTag = registrationTag;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Student other = (Student)object;
        return (this.registrationTag == null ? other.registrationTag == null: this.registrationTag.equals(other.registrationTag));
    }
}
