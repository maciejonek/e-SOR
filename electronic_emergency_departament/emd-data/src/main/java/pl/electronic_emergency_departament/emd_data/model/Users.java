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

    private String password;

    private String email;

    private String name;

    private String surname;

    private String pesel_number;

    private String phone_number;

    private Boolean locked = false;
    private Boolean enabled = false;

    private LocalDate date_of_birth;

    private String sex;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany
    private List<MedicalHistory> medicalDocument;

    public Users(String name, String surname, String email, String password, UserRole role, String pesel_number, String phone_number, LocalDate date_of_birth, String sex) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.pesel_number = pesel_number;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
    }
}