package com.example.shetuanlianmeng.config;

import com.example.shetuanlianmeng.security.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("adminpassword"))
            .roles("ADMIN")
            .build();

        UserDetails clubMember = User.withUsername("member")
            .password(passwordEncoder().encode("memberpassword"))
            .roles("CLUB_MEMBER")
            .build();

        UserDetails clubManager = User.withUsername("manager")
            .password(passwordEncoder().encode("managerpassword"))
            .roles("CLUB_MANAGER")
            .build();

        UserDetails clubLeader = User.withUsername("leader")
            .password(passwordEncoder().encode("leaderpassword"))
            .roles("CLUB_LEADER")
            .build();

        return new InMemoryUserDetailsManager(admin, clubMember, clubManager, clubLeader);
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/api/users/register", "/api/users/**", "/api/clubs/**", "/api/activities/**", "/api/applications/**", "/api/favorites/**", "/api/uploads/**", "/api/club-applications/**", "/api/user-info", "/api/statistics","/api/messages/**", "/uploads/**").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/dashboard/page-three", "/dashboard/members").hasRole("CLUB_LEADER")
            .anyRequest().authenticated()
        )
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .permitAll()
            .successHandler(customAuthenticationSuccessHandler)
        )
        .logout(logout -> logout.permitAll());

    return http.build();
}


@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.setExposedHeaders(Arrays.asList("X-Total-Count"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

}
