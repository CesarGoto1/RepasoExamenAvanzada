package ec.edu.espe.springlab.repository;

import ec.edu.espe.springlab.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //Buscar estudiante por email
    Optional<Student> findByEmail(String email);

    //Respuesta si existe al menos un registro
    boolean existsByEmail(String email);

    //Implementar paginación
    Page<Student> findByFullNameStartingWithIgnoreCase(String fullName, Pageable pageable);

    long countByActiveTrue();
}
