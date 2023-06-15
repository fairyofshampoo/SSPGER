package mx.uv.fei.sspger.logic;


public enum ReceptionalWorkStatus {
    CONCLUDED("CONCLUIDO"),
    ABANDONED("ABANDONADO"),
    ACTIVE("VIGENTE");
    private String displayName;

    ReceptionalWorkStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
