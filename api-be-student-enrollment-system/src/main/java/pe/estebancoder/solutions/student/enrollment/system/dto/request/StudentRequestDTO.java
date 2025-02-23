package pe.estebancoder.solutions.student.enrollment.system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequestDTO {
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    private String dni;

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String fullName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no debe exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El número de teléfono debe tener 9 dígitos")
    private String phoneNumber;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no debe exceder los 200 caracteres")
    private String address;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate birthDate;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "[MF]", message = "El género debe ser 'M' o 'F'")
    private String gender;

    @NotNull(message = "La fecha de admisión es obligatoria") // este lanza MethodArgumentNotValidException
    @PastOrPresent(message = "La fecha de admisión no puede ser futura")
    // @JsonFormat(pattern = "yyyy-MM-dd") // por defecto es el formato ISO, incluirlo si es para formato diferente al ISO (como "dd/MM/yyyy") En caso no corresponda con el formato, lanza InvalidFormatException
    // @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING) // shape: especificar cómo se debe serializar/deserializar (como string, como timestamp, etc.)
    // Configuración de ObjectMapper: para establecer un formato por defecto para TODAS las fechas de la aplicación (sin usar @JsonFormat)
    // @JsonFormat: Para formato específico en un campo particular
    // si se especifica ambos, @JsonFormat tiene prioridad sobre la configuración global del ObjectMapper
    private LocalDate admissionDate;

}
