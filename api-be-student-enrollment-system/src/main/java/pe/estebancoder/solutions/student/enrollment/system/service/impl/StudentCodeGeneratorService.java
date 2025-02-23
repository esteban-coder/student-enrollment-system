package pe.estebancoder.solutions.student.enrollment.system.service.impl;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.estebancoder.solutions.student.enrollment.system.entity.StudentCodeSequenceEntity;
import pe.estebancoder.solutions.student.enrollment.system.repository.StudentCodeSequenceRepository;

@Service
//@Transactional
public class StudentCodeGeneratorService {
    private final StudentCodeSequenceRepository sequenceRepository;

    public StudentCodeGeneratorService(StudentCodeSequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED
    )
    public String generateStudentCode(int year) {
        // Obtener o crear la secuencia para el año con reintentos en caso de error de concurrencia
        StudentCodeSequenceEntity sequence = null;
        int maxRetries = 3;
        int attempt = 0;

        while (attempt < maxRetries) {
            try {
                sequence = sequenceRepository.findById(year)
                        .orElseGet(() -> createAndSaveNewYearSequence(year));

                // Incrementar la secuencia
                sequence.setCurrentSequence(sequence.getCurrentSequence() + 1);

                // Validar que no exceda el límite
                if (sequence.getCurrentSequence() > 9999) {
                    throw new RuntimeException("Se excedió el límite de códigos para el año " + year);
                }

                // Persistir el cambio
                sequence = sequenceRepository.save(sequence);
                break;

            } catch (OptimisticLockingFailureException e) {
                attempt++;
                if (attempt == maxRetries) {
                    throw new RuntimeException("No se pudo generar el código después de " + maxRetries + " intentos");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupción durante el reintento", ex);
                }
            }
        }

        // Generar el código base
        String baseCode = String.format("%d%04d", year, sequence.getCurrentSequence());

        // Calcular el dígito verificador
        char checkDigit = calculateCheckDigit(sequence.getCurrentSequence());

        return baseCode + checkDigit;
    }

    private StudentCodeSequenceEntity createAndSaveNewYearSequence(int year) {
        StudentCodeSequenceEntity sequence = new StudentCodeSequenceEntity();
        sequence.setYear(year);
        sequence.setCurrentSequence(0);
        return sequenceRepository.save(sequence);
    }

    private char calculateCheckDigit(int sequentialNumber) {
        // Mapeo simple para los primeros 26 números (puedes extenderlo si necesitas más)
        final char[] VERIFICATION_LETTERS = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        // Usar el módulo para obtener un índice válido
        return VERIFICATION_LETTERS[sequentialNumber % 26];
    }
}
