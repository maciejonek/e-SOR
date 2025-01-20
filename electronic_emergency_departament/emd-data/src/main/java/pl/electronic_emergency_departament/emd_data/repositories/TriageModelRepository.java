package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronic_emergency_departament.emd_data.model.TriageModel;

public interface TriageModelRepository extends JpaRepository<TriageModel, Long> {
    TriageModel findBySymptom(String symptom);

}
