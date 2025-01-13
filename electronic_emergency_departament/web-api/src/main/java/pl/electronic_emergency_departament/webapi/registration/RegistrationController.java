package pl.electronic_emergency_departament.webapi.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

//    @PostMapping
//    public String register(@RequestBody RegistrationRequest request) {
//        return registrationService.register(request);
//    }
//
//    @GetMapping(path = "confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return registrationService.confirmToken(token);
//    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            String token = registrationService.register(request);
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        try {
            String confirmationMessage = registrationService.confirmToken(token);
            return ResponseEntity.ok().body(Map.of("message", confirmationMessage));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
