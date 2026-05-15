package ec.edu.espe.springlab.domain;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, unique = true, length = 120)
    private String email;
    private LocalDate birthDate;
    private Boolean active = true;

    public Student(){
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getActive() {return active;}
    public void setActive(Boolean active) {
        this.active = active;
    }
}
