package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum EnrollmentDetailStatus implements BaseEnum {
    ACTIVE("1"),       // Cursando actualmente
    WITHDRAWN("0"),    // Se retiró del curso
    COMPLETED("2");    // Terminó el curso (con nota final ya sea aprobado o desaprobado)

    private final String code;

    EnrollmentDetailStatus(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static EnrollmentDetailStatus fromCode(String code) {
        return BaseEnum.fromCode(EnrollmentDetailStatus.class, code);
    }
}
