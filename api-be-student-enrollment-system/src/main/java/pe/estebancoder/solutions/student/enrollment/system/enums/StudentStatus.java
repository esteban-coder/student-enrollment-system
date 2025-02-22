package pe.estebancoder.solutions.student.enrollment.system.enums;

import java.util.stream.Stream;

public enum StudentStatus {
    ACTIVE("1"),      // Estudiante cursando normalmente
    INACTIVE("0"),    // Estudiante que ha dejado de estudiar temporalmente
    GRADUATED("2"),   // Estudiante que completó sus estudios
    SUSPENDED("3");   // Estudiante suspendido por razones académicas o disciplinarias

    private final String code;

    StudentStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static StudentStatus fromCode(String code) {
        return Stream.of(values())
                .filter(status -> status.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code + " for StudentStatus"));
    }
}
