package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum DepartmentStatus implements BaseEnum {
    ACTIVE("1"),        // Curso que se dicta regularmente
    INACTIVE("0");      // Curso temporalmente no disponible

    private final String code;
    DepartmentStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static DepartmentStatus fromCode(String code) {
        return BaseEnum.fromCode(DepartmentStatus.class, code);
    }
}
