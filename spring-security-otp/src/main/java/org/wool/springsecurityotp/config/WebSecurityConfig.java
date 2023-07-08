package org.wool.springsecurityotp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> {
            httpSecurityCsrfConfigurer.disable();
        }).cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.disable();
        }).authorizeHttpRequests(auth -> {
            auth.anyRequest().permitAll();
        });

        return http.build();
    }
}
