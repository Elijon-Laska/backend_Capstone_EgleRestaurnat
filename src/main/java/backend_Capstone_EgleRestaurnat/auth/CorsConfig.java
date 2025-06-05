package backend_Capstone_EgleRestaurnat.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") //ORIGINE ESATTA DEL FRONTEND
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true); //se si vuole usare Authorization header o cookie
            }
        };
    }
}
