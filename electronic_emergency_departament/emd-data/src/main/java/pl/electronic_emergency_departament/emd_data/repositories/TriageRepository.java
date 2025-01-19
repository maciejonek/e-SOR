package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.electronic_emergency_departament.emd_data.model.Facilities;
import pl.electronic_emergency_departament.emd_data.model.TriageColour;
import pl.electronic_emergency_departament.emd_data.model.TriageReport;

@Repository
public interface TriageRepository extends JpaRepository<TriageColour, Long> {
}
