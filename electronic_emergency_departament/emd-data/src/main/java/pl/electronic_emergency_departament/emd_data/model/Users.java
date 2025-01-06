package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private Long user_id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String surname;

    private LocalDate dob;

    private int phone;

    private Boolean locked = false;
    private Boolean enabled = false;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private List<MedicalHistory> medicalDocument;


    public Users(String name, String surname, String email, String password, UserRole role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
