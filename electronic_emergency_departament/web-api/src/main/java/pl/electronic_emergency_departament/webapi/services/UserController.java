package pl.electronic_emergency_departament.webapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.electronic_emergency_departament.emd_data.model.UserInformation;
import pl.electronic_emergency_departament.emd_data.model.Users;
import pl.electronic_emergency_departament.emd_data.repositories.TriageModelRepository;
import pl.electronic_emergency_departament.emd_data.repositories.TriageReportRepository;
import pl.electronic_emergency_departament.emd_data.repositories.UserRepository;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenService;
import pl.electronic_emergency_departament.webapi.services.security.model.UserDetailsDto;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final TriageReportRepository triageReportRepository;
    private final UserRepository userRepository;

    @GetMapping("/myProfile")
    public ResponseEntity<UserInformation> getUserProfile(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        if (userDetailsDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = userDetailsDto.getUser_id();
        UserInformation userInformation = userService.myProfile(userId);
        return ResponseEntity.ok(userInformation);
    }


    @PostMapping("/update")
    public ResponseEntity<Users> updateUser(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @RequestBody Users user) {
        try {
            Long userId = userDetailsDto.getUser_id();
            Users updatedUser = userService.updateUser(user, userId);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/predictAndSave")
    public ResponseEntity<Map<String, Object>> predictAndSave(
            @RequestBody Map<String, Object> symptoms,
            @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        System.out.println("Dane otrzymane w żądaniu: " + symptoms);

        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Integer> prediction = userService.getPrediction(symptoms, userDetailsDto.getUser_id());
            System.out.println("Predykcja: " + prediction);

            response.put("status", "success");
            response.put("prediction", prediction.get("prediction"));
            response.put("message", "Prediction retrieved successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Logowanie pełnych szczegółów błędu
            response.put("status", "error");
            response.put("message", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/queue")
    public ResponseEntity<Map<String, Object>> getQueue(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        try {
            Map<String, Object> queueDetails = userService.getQueueDetails(userDetailsDto.getUser_id());
            System.out.println("Queue details controller: " + queueDetails);
            return ResponseEntity.ok(queueDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/queue/leave")
public ResponseEntity<Void> leaveQueue(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
    try {
        Long userId = userDetailsDto.getUser_id();

        // Pobieramy facilityId użytkownika
        int facilityId = triageReportRepository.findMostRecentFacilityIdByUserId(userId);

        // Usuwamy raporty użytkownika w danej placówce
        triageReportRepository.deleteByUserIdAndFacilityId(userId, facilityId); // Usuwamy raporty użytkownika

        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

}
