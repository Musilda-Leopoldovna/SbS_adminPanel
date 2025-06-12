package kata.springBootSecurity.adminPanel.rest.configs;

import kata.springBootSecurity.adminPanel.exceptionsHandlers.RestAccessDeniedHandler;
import kata.springBootSecurity.adminPanel.exceptionsHandlers.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ApiSecurityConfig {

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(baseAuth -> baseAuth
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(new RestAccessDeniedHandler()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }
}
