package com.cts.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                /* ✅ Public pages */
                .requestMatchers("/", "/login", "/register", "/user/register",
                                 "/css/**", "/js/**", "/images/**").permitAll()

                /* ✅ Admin-only pages */
                .requestMatchers("/admin/**").hasRole("ADMIN")

                /* ✅ Customer (USER) pages */
                .requestMatchers("/products/**", "/cart/**", "/orders/**").hasAnyRole("USER", "ADMIN")

                /* ✅ Any other request requires authentication */
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                // After login, redirect based on role
                .defaultSuccessUrl("/products", true)   // ✅ customers go here
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
