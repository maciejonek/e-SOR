package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TriageReport {

    @Id
    @GeneratedValue
    private int id;
    private int user_id;
    private LocalDateTime date;
    private String triage_colour;
}
