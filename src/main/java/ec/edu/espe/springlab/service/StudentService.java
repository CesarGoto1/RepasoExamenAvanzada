package ec.edu.espe.springlab.service;

import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.dto.StudentUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

public interface StudentService {
    //Crear un estudiante
    StudentResponse create(StudentCreateRequest request);
    //Buscar estudiante por ID
    StudentResponse getById(Long id);
    //Listar todos los estudiantes y agregar paginación para buscar
    Page<StudentResponse> list(String name, Pageable pageable);
    //Cambiar el estado
    StudentResponse deactivate(Long id);
    //Actualizar estudiante
    StudentResponse update(Long id, StudentUpdateRequest request);
    //Obtener estadísticas
    ec.edu.espe.springlab.dto.StudentStatsResponse getStats();
}
