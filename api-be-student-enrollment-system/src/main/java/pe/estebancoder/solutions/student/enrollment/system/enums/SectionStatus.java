package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum SectionStatus implements BaseEnum {
    OPEN("1"),         // Sección abierta para matrícula
    CLOSED("0"),       // Sección cerrada para matrícula (llena o fecha límite alcanzada)
    CANCELLED("2"),    // Sección cancelada (no alcanzó mínimo de alumnos u otros motivos)
    IN_PROGRESS("3"),  // Sección en curso (clases iniciadas)
    COMPLETED("4");    // Sección finalizada (clases terminadas)

    private final String code;
    SectionStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static SectionStatus fromCode(String code) {
        return BaseEnum.fromCode(SectionStatus.class, code);
    }
}
