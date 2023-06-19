package mx.uv.fei.sspger.logic;


public enum AcademicBodyMemberRole {
    RESPONSIBLE("Responsable"),
    MEMBER("Miembro");
    private String displayName;
    
    AcademicBodyMemberRole(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
