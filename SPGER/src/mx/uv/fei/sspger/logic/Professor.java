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
        return (this.personalNumber == null ? other.personalNumber == null: this.personalNumber.equals(other.personalNumber));
    }
    
    @Override
    public String toString(){
        return honorificTitle + " " + name + " " + lastName;
    }
}
