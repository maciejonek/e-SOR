package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TriageReport {

    @Id
    @GeneratedValue
    private Long id;
    private Long user_id;
    private LocalDateTime date;
    private int triage_colour;
    private int facility_id;

    @ManyToOne
    @JoinColumn(name = "triage_model_id")
    private TriageModel triageModel;

}
