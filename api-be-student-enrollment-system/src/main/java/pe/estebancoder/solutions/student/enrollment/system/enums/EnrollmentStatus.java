package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum EnrollmentStatus implements BaseEnum {
    ENROLLED("1"),    // Matrícula activa
    WITHDRAWN("0"),   // Estudiante se retiró del curso
    COMPLETED("2");   // Estudiante completó el curso

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
