package mx.uv.fei.sspger.logic;


public class AcademicBodyMember {
    private int id;
    private String idAcademicBody;
    private int idUserProfessor;
    private String role;
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setIdAcademicBody(String idAcademicBody){
        this.idAcademicBody = idAcademicBody;
    }
    
    public String getIdAcademicBody(){
        return idAcademicBody;
    }
    
    public void setIdUserProfessor(int idUserProfessor){
        this.idUserProfessor = idUserProfessor;
    }
    
    public int getIdUserProfessor(){
        return idUserProfessor;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getRole(){
        return role;
    }
    
    public boolean equals(Object object){
        if(object instanceof AcademicBodyMember){
            AcademicBodyMember academicBodyMember = (AcademicBodyMember)object;
            
            return (this.getId() == academicBodyMember.getId()) && (this.getIdAcademicBody().equals(academicBodyMember.getIdAcademicBody()))
                   && (this.getIdUserProfessor() == (academicBodyMember.getIdUserProfessor())) && (this.getRole().equals(academicBodyMember.getRole()));
        }else{
            return false;
        }
    }
}
