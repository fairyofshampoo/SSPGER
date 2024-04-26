package mx.uv.fei.sspger.logic;

public enum HonorificTitles {
    MTRO("Mtro."),
    DOCTOR("Dr."),
    LICENCIADO("Lic."),
    INGENIERO("Ing");

    private final String displayName;

    HonorificTitles(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
