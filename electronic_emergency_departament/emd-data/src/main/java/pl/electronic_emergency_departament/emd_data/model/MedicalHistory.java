package pl.electronic_emergency_departament.emd_data.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MedicalHistory {

    @Id
    @GeneratedValue
    private Long document_id;

    @ManyToOne
    private Users user_id;

    private String document_path;

    private DocumentType document_type;
}
