package mx.uv.fei.sspger.logic;


public abstract class User extends AccessAccount{
    protected String name;
    protected String lastName;
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
