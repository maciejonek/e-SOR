package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.electronic_emergency_departament.emd_data.model.TriageReport;

import java.util.List;

@Repository
public interface TriageReportRepository extends JpaRepository<TriageReport, Long> {
    @Query("SELECT tr.facility_id FROM TriageReport tr WHERE tr.user_id = :user_Id")
    int findFacility_IdByUser_Id(Long user_Id);

    @Query("SELECT tr FROM TriageReport tr WHERE tr.facility_id = :facility_id")
    List<TriageReport> findByFacilityId(int facility_id);
}