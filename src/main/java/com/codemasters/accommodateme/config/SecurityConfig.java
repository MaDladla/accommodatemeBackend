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
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
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

                        .requestMatchers(GET, "/issues/getAll").hasAnyAuthority("READALL:ISSUES")
                        .requestMatchers(GET, "/issues/get/**").hasAnyAuthority("READ:ISSUES")
                        .requestMatchers(POST, "/issues/**").hasAnyAuthority("CREATE:ISSUES")
                        .requestMatchers(DELETE, "/issues/**").hasAnyAuthority("DELETE:ISSUES")
                        .requestMatchers(PATCH, "/issues/**").hasAnyAuthority("UPDATE:ISSUES")

                        //REVIEWS

                        .requestMatchers(GET, "/review/getAll").hasAnyAuthority("READALL:REVIEW")
                        .requestMatchers(GET, "/review/get/**").hasAnyAuthority("READ:REVIEW")
                        .requestMatchers(POST, "/review/**").hasAnyAuthority("CREATE:REVIEW")
                        .requestMatchers(DELETE, "/review/**").hasAnyAuthority("DELETE:REVIEW")

                        //RESIDENCE
                        .requestMatchers(GET, "/residence/getAll").hasAnyAuthority("READALL:RESIDENCE")
                        .requestMatchers(GET, "/residence/get/**").hasAnyAuthority("READ:RESIDENCE")
                        .requestMatchers(POST, "/residence/**").hasAnyAuthority("CREATE:RESIDENCE")
                        .requestMatchers(PUT, "/residence/update/**").hasAnyAuthority("UPDATE:RESIDENCE")
                        .requestMatchers(DELETE, "/residence/**").hasAnyAuthority("DELETE:RESIDENCE")
                        .requestMatchers(PATCH, "/residence/**").hasAnyAuthority("UPDATE:RESIDENCE")

                        //ROOM
                        .requestMatchers(GET, "/room/getAll").hasAnyAuthority("READALL:ROOM")
                        .requestMatchers(GET, "/room/get/**").hasAnyAuthority("READ:ROOM")
                        .requestMatchers(POST, "/room/**").hasAnyAuthority("CREATE:ROOM")
                        .requestMatchers(PUT, "/room/update/**").hasAnyAuthority("UPDATE:ROOM")
                        .requestMatchers(DELETE, "/room/**").hasAnyAuthority("DELETE:ROOM")
                        .requestMatchers(PATCH, "/room/**").hasAnyAuthority("UPDATE:ROOM")

                        //LOCATION
                        .requestMatchers(GET, "/location/getAll").hasAnyAuthority("READALL:LOCATION")
                        .requestMatchers(GET, "/location/get/**").hasAnyAuthority("READ:LOCATION")
                        .requestMatchers(POST, "/location/**").hasAnyAuthority("CREATE:LOCATION")
                        .requestMatchers(PUT, "/location/update/**").hasAnyAuthority("UPDATE:LOCATION")
                        .requestMatchers(DELETE, "/location/**").hasAnyAuthority("DELETE:LOCATION")
                        .requestMatchers(PATCH, "/location/**").hasAnyAuthority("UPDATE:LOCATION")

                        //ANNOUNCEMENT
                        .requestMatchers(GET, "/announcement/getAll").hasAnyAuthority("READALL:ANNOUNCEMENT")
                        .requestMatchers(GET, "/announcement/get/**").hasAnyAuthority("READ:ANNOUNCEMENT")
                        .requestMatchers(POST, "/announcement/**").hasAnyAuthority("CREATE:ANNOUNCEMENT")
                        .requestMatchers(PUT, "/announcement/update/**").hasAnyAuthority("UPDATE:ANNOUNCEMENT")
                        .requestMatchers(DELETE, "/announcement/**").hasAnyAuthority("DELETE:ANNOUNCEMENT")
                        .requestMatchers(PATCH, "/announcement/**").hasAnyAuthority("UPDATE:ANNOUNCEMENT")


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
