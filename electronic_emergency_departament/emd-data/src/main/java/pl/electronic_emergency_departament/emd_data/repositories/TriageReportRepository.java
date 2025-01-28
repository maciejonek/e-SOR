package pl.electronic_emergency_departament.emd_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.electronic_emergency_departament.emd_data.model.TriageReport;

import java.util.List;

@Repository
public interface TriageReportRepository extends JpaRepository<TriageReport, Long> {
    @Query("SELECT tr.facility_id FROM TriageReport tr WHERE tr.user_id = :user_Id")
    int findFacility_IdByUser_Id(Long user_Id);

    @Query("SELECT tr FROM TriageReport tr WHERE tr.facility_id = :facility_id")
    List<TriageReport> findByFacilityId(int facility_id);

    @Query("SELECT t.facility_id FROM TriageReport t WHERE t.user_id = :user_id ORDER BY t.date DESC LIMIT 1")
    int findMostRecentFacilityIdByUserId(Long user_id);

    @Transactional
    @Modifying
    @Query("delete FROM TriageReport t WHERE t.user_id = :user_id AND t.facility_id = :facility_id")
    void deleteByUserIdAndFacilityId(Long user_id, int facility_id);

}