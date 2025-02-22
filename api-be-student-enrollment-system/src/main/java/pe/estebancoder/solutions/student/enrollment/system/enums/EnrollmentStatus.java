package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum EnrollmentStatus implements BaseEnum {
    ENROLLED("1"),    // Matrícula activa, con curso(s) en progreso
    WITHDRAWN("0"),   // Se retiró de todos los cursos
    COMPLETED("2");   // Terminó el semestre (con al menos un curso aprobado o desaprobado)

    private final String code;
    EnrollmentStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static EnrollmentStatus fromCode(String code) {
        return BaseEnum.fromCode(EnrollmentStatus.class, code);
    }
}
