package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.impl.StudentServiceImpl;
import ec.edu.espe.springlab.web.advice.ConflictException;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.time.LocalDate;

import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.dto.StudentStatsResponse;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.PageRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({StudentServiceImpl.class})
public class StudentServiceTest {
    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicatedEmail(){
        // Prueba 1 — Evitar correos duplicados
        Student existing = new Student();
        existing.setFullName("Existing");
        existing.setEmail("test@example.com");
        existing.setBirthDate(LocalDate.of(2001,12,1));
        existing.setActive(true);
        repository.save(existing);

        StudentCreateRequest req = new StudentCreateRequest();
        req.setFullName("New User");
        req.setEmail("test@example.com");
        req.setBirthDate(LocalDate.of(2001,12,1));

        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ConflictException.class);
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenIdDoesNotExist() {
        // Prueba 2 — Excepción al consultar por ID inexistente
        assertThatThrownBy(() -> service.getById(9999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("No esta registrado");
    }

    @Test
    void shouldDeactivateStudent() {
        // Prueba 3 — Desactivar estudiante (PATCH)
        Student s = new Student();
        s.setFullName("Active Student");
        s.setEmail("active@example.com");
        s.setBirthDate(LocalDate.now());
        s.setActive(true);
        s = repository.save(s);

        service.deactivate(s.getId());

        Student updated = repository.findById(s.getId()).orElseThrow();
        assertThat(updated.getActive()).isFalse();
        assertThat(updated.getFullName()).isEqualTo("Active Student");
        assertThat(updated.getEmail()).isEqualTo("active@example.com");
    }

    @Test
    void shouldReturnCorrectStats() {
        // Prueba 4 — Estadísticas /stats
        repository.deleteAll();
        
        Student s1 = new Student(); s1.setFullName("S1"); s1.setEmail("s1@e.com"); s1.setActive(true); repository.save(s1);
        Student s2 = new Student(); s2.setFullName("S2"); s2.setEmail("s2@e.com"); s2.setActive(true); repository.save(s2);
        Student s3 = new Student(); s3.setFullName("S3"); s3.setEmail("s3@e.com"); s3.setActive(false); repository.save(s3);

        StudentStatsResponse stats = service.getStats();

        assertThat(stats.getTotal()).isEqualTo(3);
        assertThat(stats.getActive()).isEqualTo(2);
        assertThat(stats.getInactive()).isEqualTo(1);
    }

    @Test
    void shouldSearchByPartialName() {
        // Prueba 6 — Funcionalidad extra: búsqueda por nombre parcial
        repository.deleteAll();
        
        Student s1 = new Student(); s1.setFullName("Ana"); s1.setEmail("ana@e.com"); repository.save(s1);
        Student s2 = new Student(); s2.setFullName("Andrea"); s2.setEmail("andrea@e.com"); repository.save(s2);
        Student s3 = new Student(); s3.setFullName("Juan"); s3.setEmail("juan@e.com"); repository.save(s3);

        var result = service.list("an", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(StudentResponse::getFullName)
                .containsExactlyInAnyOrder("Ana", "Andrea");
    }
}
