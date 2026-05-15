package ec.edu.espe.springlab.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class StudentUpdateRequest {
    @Size(min = 3, max = 120, message = "El nombre debe tener mínimo 3 y máximo 120 caracteres")
    private String fullName;

    public @Size(min = 3, max = 120, message = "El nombre debe tener mínimo 3 y máximo 120 caracteres") String getFullName() {
        return fullName;
    }

    public void setFullName(@Size(min = 3, max = 120, message = "El nombre debe tener mínimo 3 y máximo 120 caracteres") String fullName) {
        this.fullName = fullName;
    }

    public @Email(message = "El formato no es valido en el email") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "El formato no es valido en el email") String email) {
        this.email = email;
    }

    @Email(message = "El formato no es valido en el email")
    private String email;

}
