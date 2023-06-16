package mx.uv.fei.sspger.logic;


public class Professor extends User{
    private String honorificTitle;
    private String personalNumber;
    private int id;
    private int isAdmin;
    
    private final String PERSONAL_NUMBER_REGEX = "^\\d{4,5}$";
    
    public Professor(){
        
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getHonorificTitle(){
        return honorificTitle;
    }
    
    public void setHonorificTitle(String honorificTitle){
        this.honorificTitle = honorificTitle;
    }
    
    public String getPersonalNumber(){
        return personalNumber;
    }
    
    public void setPersonalNumber(String personalNumber){
        this.personalNumber = personalNumber;
        validatePersonalNumber(personalNumber);
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    private void validatePersonalNumber(String personalNumber){
        if (!personalNumber.matches(PERSONAL_NUMBER_REGEX)) {
            throw new IllegalArgumentException("El número de personal debe contener las siguientes características:\n"
                    + "1. La longitud es de 4 a 5 dígitos. \n");
        }
        
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final Professor other = (Professor)object;
        return (this.email == null ? other.email == null : this.email.equals(other.email)) &&
            (this.password == null ? other.password == null : this.password.equals(other.password)) &&
            (this.name == null ? other.name == null : this.name.equals(other.name)) &&
            (this.lastName == null ? other.lastName == null : this.lastName.equals(other.lastName)) &&
            (this.status == other.status) &&
            (this.honorificTitle == null ? other.honorificTitle == null : this.honorificTitle.equals(other.honorificTitle)) &&
            (this.personalNumber == null ? other.personalNumber == null : this.personalNumber.equals(other.personalNumber)) &&
            (this.id == other.id) &&
            (this.isAdmin == other.isAdmin);
    }
    
    @Override
    public String toString(){
        return honorificTitle + " " + name + " " + lastName;
    }
}
