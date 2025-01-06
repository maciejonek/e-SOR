package pl.electronic_emergency_departament.webapi.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.electronic_emergency_departament.emd_data.model.UserRole;
import pl.electronic_emergency_departament.emd_data.model.Users;
import pl.electronic_emergency_departament.emd_data.repositories.UserRepository;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationToken;
import pl.electronic_emergency_departament.webapi.registration.token.ConfirmationTokenService;
import pl.electronic_emergency_departament.webapi.services.security.model.UserDetailsDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;





    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user =  userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));

        return new UserDetailsDto(
                user.getEmail(),
                user.getPassword(),
                true,
                false,
                UserRole.USER
        );
    }



    public String signUpUser(Users user){
        boolean userExist = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if(userExist){
            throw new IllegalStateException("email already taken");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        String rawPassword = user.getPassword();
        if (rawPassword == null) {
            throw new IllegalArgumentException("Raw password is null");
        }

        String encodePassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodePassword);


        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);



        return token;

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }


}
