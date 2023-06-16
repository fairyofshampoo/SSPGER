package mx.uv.fei.sspger.logic;


public class AcademicBodyMember extends Professor{
    private int idAcademicBodyMember;
    private String role;
    
    public void setIdAcademicBodyMember(int idAcademicBodyMember){
        this.idAcademicBodyMember = idAcademicBodyMember;
    }
    
    public int getIdAcademicBodyMember(){
        return idAcademicBodyMember;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getRole(){
        return role;
    }
   
    public boolean equals(Object object){
        if((object == null) || (object.getClass() != this.getClass())) {
            return false;
        } 
        final AcademicBodyMember otherAcademicBodyMember = (AcademicBodyMember) object;
       
        return (this.role == null? otherAcademicBodyMember.role == null : this.role.equals(otherAcademicBodyMember.role));
    }
}
