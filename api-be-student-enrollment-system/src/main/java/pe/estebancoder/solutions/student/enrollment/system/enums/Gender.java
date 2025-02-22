package pe.estebancoder.solutions.student.enrollment.system.enums;

public enum Gender implements BaseEnum {
    MALE("M"),
    FEMALE("F");

    private final String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public static Gender fromCode(String code) {
        return BaseEnum.fromCode(Gender.class, code);
    }
}
