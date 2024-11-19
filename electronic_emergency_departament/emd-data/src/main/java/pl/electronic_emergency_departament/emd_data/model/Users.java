package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
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

    @OneToMany
    private List<MedicalHistory> medicalDocument;

}
