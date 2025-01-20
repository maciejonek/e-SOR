package pl.electronic_emergency_departament.webapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("/check")
    public ResponseEntity<Void> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            System.out.println("Nieautoryzowany: " + authentication);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("Autoryzowany: " + authentication);
        return ResponseEntity.ok().build();
    }

}
