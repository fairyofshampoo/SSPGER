package mx.uv.fei.sspger.logic;

import java.util.List;


public class AcademicBody {
    private String key;
    private String name;
    private int status;
    private List<AcademicBodyMember> member;
    
    public static final String KEY_VALID_REGEX = "^UV-CA-\\d{1,4}$";
    public static final String NAME_VALID_REGEX = "^(?!\s)(?!.*\s$)[a-zA-ZÁáÉéÍíÓóÚúÜüÑñ ]{1,100}$";
    
    public void setKey(String key){
        this.key = key;
        isKeyValid(key);
    }
    
    public String getKey(){
        return key;
    }
    
    public void setName(String name){
        this.name = name;
        isNameValid(name);
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
        final AcademicBody otherAcademicBody = (AcademicBody) object;
       
        return (this.key == null? otherAcademicBody.key == null : this.key.equals(otherAcademicBody.key))
           && (this.name == null? otherAcademicBody.name == null : this.name.equals(otherAcademicBody.name))
           && (this.status == otherAcademicBody.status);
    }
    
    private void isKeyValid(String key){
        if(!key.matches(KEY_VALID_REGEX)){
            throw new IllegalArgumentException("La clave debe contener las siguientes características:\n"
                + "1.- Debe comenzar con UV-CA-\n"
                + "2.- Debe tener un límite de 4 dígitos numéricos\n"
                + "3.- No debe tener espacios en blanco"); 
        }
    }
    
    private void isNameValid(String name){
        if(!name.matches(NAME_VALID_REGEX)){
            throw new IllegalArgumentException("El nombre debe contener las siguientes características:\n"
                + "1.- No debe tener espacios en blanco\n"
                + "2.- No debe sobrepasar los 100 caracteres");
        }
    }
}
