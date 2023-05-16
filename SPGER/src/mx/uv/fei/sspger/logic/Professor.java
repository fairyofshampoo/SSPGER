package mx.uv.fei.sspger.logic;


public class Professor extends User{
    private String honorificTitle;
    private String personalNumber;
    private int id;
    private int isAdmin;
    
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
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
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
        return " ";
    }
}
