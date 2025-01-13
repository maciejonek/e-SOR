package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    private LocalDate dob;

    private String phoneNumber;

    private Boolean locked = false;
    private Boolean enabled = false;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private List<MedicalHistory> medicalDocument;

    public Users(String name, String surname, String email, String password, UserRole role, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}