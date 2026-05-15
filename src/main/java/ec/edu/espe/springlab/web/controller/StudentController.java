package ec.edu.espe.springlab.web.controller;

import ec.edu.espe.springlab.domain.Student;
import ec.edu.espe.springlab.dto.StudentCreateRequest;
import ec.edu.espe.springlab.dto.StudentResponse;
import ec.edu.espe.springlab.dto.StudentUpdateRequest;
import ec.edu.espe.springlab.service.StudentService;
import ec.edu.espe.springlab.web.advice.NotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    //Inyección de Dependencias
    private final StudentService service;

    public StudentController(StudentService service){
        this.service = service;
    }

    //Crear un estudiante
    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    //Obtener un estudiante por ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    //Obtener todos los estudiantes
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(
            @RequestParam(required = false) String name, Pageable pageable){
        return ResponseEntity.ok(service.list(name, pageable));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<StudentResponse> deactivateStudent(@PathVariable Long id){
        return ResponseEntity.ok(service.deactivate(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateRequest request
            ){
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/stats")
    public ResponseEntity<ec.edu.espe.springlab.dto.StudentStatsResponse> getStats(){
        return ResponseEntity.ok(service.getStats());
    }

}
