package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum GradeStatus implements BaseEnum {
    PENDING("P"),      // Aún no tiene nota final
    APPROVED("A"),     // Curso aprobado
    DISAPPROVED("D"),  // Curso desaprobado
    WITHDRAWN("W");    // Se retiró del curso (no aplica nota)

    private final String code;

    GradeStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static GradeStatus fromCode(String code) {
        return BaseEnum.fromCode(GradeStatus.class, code);
    }
}
