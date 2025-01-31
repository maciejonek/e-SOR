package pl.electronic_emergency_departament.emd_data.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.electronic_emergency_departament.emd_data.model.UserInformation;
import pl.electronic_emergency_departament.emd_data.model.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Users a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);

    @Query(value = "SELECT name, surname, date_of_birth, phone_number, email, pesel_number, sex FROM users WHERE user_id = ?", nativeQuery = true)
    UserInformation myProfile(Long userId);

    @Query(value = "SELECT name, surname, pesel_number, date_of_birth FROM users WHERE user_id = ?", nativeQuery = true)
    UserInformation getUserDetails(Long userId);

}
