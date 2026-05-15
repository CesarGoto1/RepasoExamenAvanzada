package ec.edu.espe.springlab.service.impl;
import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.dto.StudentUpdateRequest;
import ec.edu.espe.springlab.repository.StudentRepository;
import ec.edu.espe.springlab.service.StudentService;
import ec.edu.espe.springlab.web.advice.ConflictException;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
@Service
public class StudentServiceImpl implements StudentService {
    //Inyección de Dependencias
    private final StudentRepository repo;
    public StudentServiceImpl(StudentRepository repo){
        this.repo = repo;
    }

    @Override
    public StudentResponse create(StudentCreateRequest request) {
        if(repo.existsByEmail(request.getEmail())){
            throw new ConflictException("El email ya esta registrado");
        }
        Student s = new Student();
        s.setFullName(request.getFullName());
        s.setEmail(request.getEmail());
        s.setBirthDate(request.getBirthDate());
        s.setActive(true);
        Student saved = repo.save(s);
        return toResponse(saved);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new
                NotFoundException("No esta registrado"));
        return toResponse(s);
    }

    @Override
    public Page<StudentResponse> list(String name, Pageable pageable) {
        String filter = (name != null) ? name : "";
        return repo.findByFullNameStartingWithIgnoreCase(filter, pageable)
            .map(this::toResponse);
    }

    @Override
    public StudentResponse deactivate(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new
                NotFoundException("Estudiante no asoma"));
        s.setActive(false);
        return toResponse(repo.save(s));
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        //Manejo de excepcion para verificar si existe el estudiante
        Student student = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe ese estudiante para actualizarlo"));
        //Verificar si el email no pertenece a otro alumno
        if(request.getEmail() != null && !request.getEmail().equals(student.getEmail())){
            if(repo.existsByEmail(request.getEmail())){
                throw new ConflictException("El nuevo email ya esta registrado");
            }
            student.setEmail(request.getEmail());
        }

        if(request.getFullName() != null){
            student.setFullName(request.getFullName());
        }

        return toResponse(repo.save(student));
    }

    @Override
    public ec.edu.espe.springlab.dto.StudentStatsResponse getStats() {
        long total = repo.count();
        long active = repo.countByActiveTrue();
        long inactive = total - active;
        return new ec.edu.espe.springlab.dto.StudentStatsResponse(total, active, inactive);
    }

    //Mapeo interno Entidad -> DTO de salida
    private StudentResponse toResponse(Student s){
        StudentResponse r = new StudentResponse();
        r.setId(s.getId());
        r.setFullName(s.getFullName());
        r.setEmail(s.getEmail());
        r.setBirthDate(s.getBirthDate());
        r.setActive(s.getActive());

        return r;
    }
}

