package pe.estebancoder.solutions.student.enrollment.system.enums;

import java.util.Arrays;

public interface BaseEnum {
    String getCode();

    // El metodo estático para todos los enums
    static <T extends Enum<T> & BaseEnum> T fromCode(Class<T> enumClass, String code) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid code: " + code + " for " + enumClass.getSimpleName()));
    }
}
