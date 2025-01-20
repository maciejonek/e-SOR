package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class TriageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private LocalDateTime date;
    private int triage_colour;
    private int facility_id;

    @ManyToMany
    @JoinTable(
            name = "triage_report_symptoms",
            joinColumns = @JoinColumn(name = "triage_report_id"),
            inverseJoinColumns = @JoinColumn(name = "triage_model_id")
    )
    private List<TriageModel> symptoms;

    public TriageReport(Long user_id, LocalDateTime date, int triage_colour, int facility_id, List<TriageModel> symptoms) {
        this.user_id = user_id;
        this.date = date;
        this.triage_colour = triage_colour;
        this.facility_id = facility_id;
        this.symptoms = symptoms;
    }
}