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
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenService;
import pl.electronic_emergency_departament.webapi.services.security.model.UserDetailsDto;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/myProfile")
    public ResponseEntity<UserInformation> getUserProfile(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
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
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Integer> prediction = userService.getPrediction(symptoms, userDetailsDto);
            response.put("status", "success");
            response.put("prediction", prediction);
            response.put("message", "Report saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to save report: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
