package mx.uv.fei.sspger.logic;


public enum ProjectStatus {
    VALIDADO("VALIDADO"), 
    NO_VALIDADO("NO VALIDADO"),
    CONCLUIDO("CONCLUIDO"),
    ASIGNADO("ASIGNADO"),
    RECHAZADO("RECHAZADO");
    private String displayName;

    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}