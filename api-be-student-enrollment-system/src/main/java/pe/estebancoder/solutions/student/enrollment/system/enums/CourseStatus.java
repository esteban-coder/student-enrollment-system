package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum CourseStatus implements BaseEnum {
    ACTIVE("1"),        // Curso que se dicta regularmente
    INACTIVE("0"),      // Curso temporalmente no disponible
    DISCONTINUED("2");  // Curso que ya no se dictará más (plan de estudios antiguo)

    private final String code;
    CourseStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static CourseStatus fromCode(String code) {
        return BaseEnum.fromCode(CourseStatus.class, code);
    }
}
