package pe.estebancoder.solutions.student.enrollment.system.enums;

//  los enums ya implícitamente extienden de la clase Enum
public enum InstructorStatus implements BaseEnum {
    ACTIVE("1"),      // Profesor dictando cursos normalmente
    INACTIVE("0"),    // Profesor que ya no dicta en la institución
    ON_LEAVE("2");    // Profesor en licencia temporal (sabático, enfermedad, etc.)

    private final String code;

    InstructorStatus(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public static InstructorStatus fromCode(String code) {
        return BaseEnum.fromCode(InstructorStatus.class, code);
    }
}
