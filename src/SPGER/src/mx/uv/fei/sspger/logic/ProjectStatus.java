package mx.uv.fei.sspger.logic;

public enum ProjectStatus {
    VALIDATED("VALIDADO"),
    PROPOSED("PROPUESTO"),
    ASSIGNED("ASIGNADO"),
    REJECTED("RECHAZADO"),
    CONCLUDED("CONCLUIDO");
    private String displayName;

    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
