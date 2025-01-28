package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Facilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nazwaJednostki;
    private String wojewodztwo;
    private String miejscowosc;
    private String kodPocztowy;
    private String adres;
}
