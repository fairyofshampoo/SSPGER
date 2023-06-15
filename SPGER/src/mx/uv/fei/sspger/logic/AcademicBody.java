package mx.uv.fei.sspger.logic;


import java.util.List;


public class AcademicBody {
    private String key;
    private String name;
    private List<AcademicBodyMember> member;
    
    public static final String KEY_VALID_REGEX = "^(?=\\S+$)UV-CA-\\d+$";
    
    public void setKey(String key){
        this.key = key;
        isKeyValid(key);
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

    public List<AcademicBodyMember> getMember() {
        return member;
    }

    public void setMember(List<AcademicBodyMember> member) {
        this.member = member;
    }
    
    @Override
    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
        final AcademicBody otherAcademicBody = (AcademicBody) object;
       
        return (this.key == null? otherAcademicBody.key == null : this.key.equals(otherAcademicBody.key))
           && (this.name == null? otherAcademicBody.name == null : this.name.equals(otherAcademicBody.name));
    }
    
    private void isKeyValid(String key){
        if(!key.matches(KEY_VALID_REGEX)){
            throw new IllegalArgumentException("La clave debe contener las siguientes características:\n"
                + "1.- Debe comenzar con UV-CA-"
                + "2.- Debe tener un límite de 10 caracteres"
                + "3.- No debe tener espacios en blanco"); 
        }
    }
}