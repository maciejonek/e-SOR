package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.electronic_emergency_departament.emd_data.model.TriageColour;

@Repository
public interface TriageColourRepository extends JpaRepository<TriageColour, Long> {
}
