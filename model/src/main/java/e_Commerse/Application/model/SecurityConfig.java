package e_Commerse.Application.model;
import e_Commerse.Application.model.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(java.util.List.of("http://localhost:3000"));
                    corsConfig.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowedHeaders(java.util.List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",          // ✅ existing public endpoints
                                "/swagger-ui/**",        // ✅ allow Swagger UI
                                "/swagger-ui.html",      // ✅ allow main UI page
                                "/v3/api-docs/**",       // ✅ allow API docs JSON
                                "/v3/api-docs.yaml"      // ✅ allow YAML docs
                        ).permitAll()
                        .anyRequest().authenticated() // everything else needs auth
                )
                .addFilterBefore(jwtAuthFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

