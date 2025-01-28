package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class HospitalAddress {

    @Id
    @GeneratedValue
    private int address_id;

    private String street;

    private String city;

    private String state;

    private String zip_code;

    private String country;

    private String number;
}
