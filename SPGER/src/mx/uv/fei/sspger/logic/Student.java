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
        return (this.registrationTag == null ? other.registrationTag == null: this.registrationTag.equals(other.registrationTag)) &&
                (this.id == other.id) &&
                (this.email == null ? other.email == null: this.email.equals(other.email)) &&
                (this.password == null ? other.password == null: this.password.equals(other.password)) &&
                (this.name == null ? other.name == null: this.name.equals(other.name)) &&
                (this.lastName == null ? other.lastName == null: this.lastName.equals(other.lastName)) &&
                (this.status == other.status);
    }
}
