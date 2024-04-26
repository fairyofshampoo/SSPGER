package mx.uv.fei.sspger.logic;

public enum Modality {
    THESIS("Tesis"),
    MONOGRAPH("Monografía"),
    PRACTICAL_TECHNICAL("Práctico-Técnico"),
    PRACTICAL_THEORETICAL("Práctico-Teórico"),
    SYSTEMATIC_REVIEW_OF_THE_LITEARTURE("Revisión sistemática de la literatura"),
    ESTADISTIC_CONTROL("Control estadístico de procesos"),
    MULTIVOICE_REVIEW("Revisión multivocal de la literatura");

    private final String displayName;

    Modality(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
