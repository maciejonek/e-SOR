package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.electronic_emergency_departament.emd_data.model.Facilities;

public interface FacilitiesRepository extends JpaRepository<Facilities, Long> {

}
