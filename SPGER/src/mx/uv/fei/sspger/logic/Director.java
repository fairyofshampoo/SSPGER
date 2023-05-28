package mx.uv.fei.sspger.logic;


public class Director extends Professor{
    private int idDirector;
    private String role;

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    @Override
    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
       final Director otherDirector = (Director) object;
       
       return (this.idDirector == otherDirector.idDirector)
           && (this.role == null? otherDirector.role == null : this.role.equals(otherDirector.role));
    }
}
