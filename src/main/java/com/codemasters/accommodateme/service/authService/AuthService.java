package com.codemasters.accommodateme.service.authService;

import com.codemasters.accommodateme.entity.HttpResponse;
import com.codemasters.accommodateme.entity.Role;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.entity.UserPrincipal;
import com.codemasters.accommodateme.form.LoginForm;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.repository.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.codemasters.accommodateme.enumeration.RoleType.*;
import static com.codemasters.accommodateme.enumeration.RoleType.ROLE_USER;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RoleService<Role> roleService;

    public AuthService() {
    }

    public HttpResponse register(User registrationRequest){
        User savedUser;
        UserPrincipal userPrincipal;
        User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        try {

             savedUser = ourUserRepo.save(user);

            roleService.addRoleToUser( savedUser.getId(),ROLE_USER.name());


            userPrincipal = new UserPrincipal(savedUser, savedUser.getRole().getPermission());

        }catch (Exception e){
           return  HttpResponse.builder()
                    .timeStamp(now().toString())
                    .message(e.getMessage())
                    .build();
        }

        return HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("User", userPrincipal))
                .message("User created successfully")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build();
    }



    public HttpResponse registerAdmin(User registrationRequest) {
        User savedUser;
        UserPrincipal userPrincipal;
        User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        try {

            savedUser = ourUserRepo.save(user);

            roleService.addRoleToUser( savedUser.getId(),ROLE_ADMIN.name());


            userPrincipal = new UserPrincipal(savedUser, savedUser.getRole().getPermission());

        } catch (Exception e) {
            return  HttpResponse.builder()
                    .timeStamp(now().toString())
                    .message(e.getMessage())
                    .build();
        }

        return HttpResponse.builder()
                .timeStamp(now().toString())
                .data(of("User", userPrincipal))
                .message("Admin created successfully")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build();
    }

    public HttpResponse login(LoginForm loginRequest){
        HttpResponse response;
        UserPrincipal userPrincipal;

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            var user = ourUserRepo.findByEmail(loginRequest.getEmail()).orElseThrow();

             userPrincipal = new UserPrincipal(user, user.getRole().getPermission());



            log.info("User : {}", userPrincipal);
            var jwt = jwtUtils.generateToken(userPrincipal);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), userPrincipal);

            response= HttpResponse.builder()
                    .timeStamp(now().toString())
                    .data(of("user", userPrincipal))
                    .message("User logged in")
                    .token(jwt)
                    .refreshToken(refreshToken)
                    .status(OK)
                    .statusCode(OK.value())
                    .build();


        }catch (Exception e){
            return  HttpResponse.builder()
                    .timeStamp(now().toString())
                    .message(e.getMessage())
                    .build();
        }
        return response;
    }



    public boolean doesUserExistByEmail(String email){
        return  ourUserRepo.existsByEmail(email);
    }

}
