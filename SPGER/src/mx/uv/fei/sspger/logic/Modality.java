package mx.uv.fei.sspger.logic;

public enum Modality {
    TESIS("Tesis"),
    MONOGRAFIA("Monografía"),
    PRACTICO_TECNICO("Práctico-Teórico"),
    REVISION_SISTEMATICA_LITERATURA("Revisión sistemática de la literatura"),
    CONTROL_ESTADISTICO("Control estadístico de procesos"),
    REVISION_MULTIVOCAL("Revisión multivocal de la literatura");
    
    private final String displayName;
    
    Modality(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
