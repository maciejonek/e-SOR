package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Facilities {

    @Id
    @GeneratedValue
    private Long facility_id;

    private String facility_name;

    private String phone_number;

    @OneToOne
    private HospitalAddress hospital_address;
}
