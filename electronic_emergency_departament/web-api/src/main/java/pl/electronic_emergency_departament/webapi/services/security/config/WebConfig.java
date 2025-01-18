package pl.electronic_emergency_departament.webapi.services.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS configuration applied");
        registry.addMapping("/**")  // Allowing all endpoints
                .allowedOrigins("http://localhost:3000")  // Frontend's origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  // Allowed methods
                .allowedHeaders("*")  // Allowing all headers
                .allowCredentials(true);  // Allow credentials (cookies, authorization headers)
    }
}
