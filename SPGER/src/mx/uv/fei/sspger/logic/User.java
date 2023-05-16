package mx.uv.fei.sspger.logic;


public class User {
    protected String email;
    protected String password;
    protected String name;
    protected String lastName;
    protected int status;
    
    public User(){
        
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int userStatus) {
        this.status = userStatus;
    }

    
    @Override
    public boolean equals(Object object){
        if(object == null || (object.getClass() != this.getClass())){
            return false;
        }
        final User other = (User)object;
        return (this.email == null ? other.email == null: this.email.equals(other.email));
    }
}
   
