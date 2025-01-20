package pl.electronic_emergency_departament.webapi.services.security.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.electronic_emergency_departament.webapi.services.UserService;
import org.apache.tomcat.util.http.CookieProcessor;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())// Wyłączenie CSRF (dla testów)
                .cors(withDefaults())  // Włączenie obsługi CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/registration").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/predictAndSave").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("http://localhost:3000/templates/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("http://localhost:3000/templates/index.html", true)
                        .failureUrl("http://localhost:3000/templates/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("templates/logout")
                        .logoutSuccessUrl("http://localhost:3000/templates/login.html")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)  // Sesja zawsze tworzona przy logowaniu
                        .maximumSessions(1)
                        .expiredUrl("http://localhost:3000/templates/login.html?expired=true")
                );
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

//
//    @Bean
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(connector -> {
//            // Ustawienie odpowiednich właściwości
//            connector.setProperty("relaxedQueryChars", "|{}[]");
//        });
//        factory.addContextCustomizers(context -> {
//            context.setCookieProcessor(new CookieProcessor() {
//            }); // Modern cookie processor
//        });
//        return factory;
//    }



}
