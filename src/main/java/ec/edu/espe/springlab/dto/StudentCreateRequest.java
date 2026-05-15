package ec.edu.espe.springlab.dto;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudentCreateRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 120, message = "El nombre debe ser real, contener entre 3 y 120 caracteres")
    private String fullName;

    @NotBlank(message = "El email no puede ser vacio")
    @Email (message = "Debe proporcionar un email válido")
    @Size(max=120)
    private String email;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public LocalDate getBirthDate() { return birthDate;}
    public void setFullName(String fullName) { this.fullName = fullName;}
    public void setEmail(String email) { this.email = email;}
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate;}
}


