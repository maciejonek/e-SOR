package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MedicalStaff {

    @Id
    @GeneratedValue
    private int staff_id;

    private int facility_id;

    private String name;

    private String surname;

    private String email;

    private Position position;

    private String phone;
}
