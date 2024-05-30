package com.codemasters.accommodateme.config;

import com.codemasters.accommodateme.exception.CustomAccessDeniedHandler;
import com.codemasters.accommodateme.exception.CustomAuthenticationEntryPoint;
import com.codemasters.accommodateme.service.authService.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codemasters.accommodateme.enumeration.RoleType.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private JWTAuthFilter jwtAuthFilter;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private static final String[] PUBLIC_URLS = {
            "/auth/**",
            "/public/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request.requestMatchers(PUBLIC_URLS).permitAll()

                        //APPLICATION
                        .requestMatchers(GET, "/application/getAll").hasAnyAuthority("READALL:APPLICATION")
                        .requestMatchers(GET, "/application/get").hasAnyAuthority("READ:APPLICATION")
                        .requestMatchers(POST, "/application/**").hasAnyAuthority("CREATE:APPLICATION")
                        .requestMatchers(DELETE, "/application/**").hasAnyAuthority("DELETE:APPLICATION")
                        .requestMatchers(PATCH, "/application/**").hasAnyAuthority("UPDATE:APPLICATION")

                        //ISSUES


                        //REVIEWS


                        //RESIDENCE
                        .requestMatchers(GET, "/residences/getAll").hasAnyAuthority("READALL:RESIDENCE")
                        .requestMatchers(GET, "/residences/get/**").hasAnyAuthority("READ:RESIDENCE")
                        .requestMatchers(POST, "/residences/**").hasAnyAuthority("CREATE:RESIDENCE")
                        .requestMatchers(DELETE, "/residences/**").hasAnyAuthority("DELETE:RESIDENCE")
                        .requestMatchers(PATCH, "/residences/**").hasAnyAuthority("UPDATE:RESIDENCE")


                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



                httpSecurity.authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
