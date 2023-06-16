package mx.uv.fei.sspger.logic;


public class Student extends User{
    
    private String registrationTag;
    private int id;
    
    private final String REGISTRATION_TAG_REGEX = "^(?i)zs\\d{8}$";

    
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
        validateRegistrationTag(registrationTag);
    }
    
    private void validateRegistrationTag(String registrationTag){
        if (!registrationTag.matches(REGISTRATION_TAG_REGEX)) {
            throw new IllegalArgumentException("La matricula debe contener las siguientes características:\n"
                    + "1. La longitud de la matrícula es de 10. \n"
                    + "2. Debe comenzar con zs seguido de dígitos \n");
        }
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
