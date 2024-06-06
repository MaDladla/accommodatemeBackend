package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.ApplicationDto;
import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.entity.User;


import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.service.authService.OurUserDetailsService;
import com.codemasters.accommodateme.service.implementation.ApplicationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("http://localhost:5173")
@RequestMapping("/auth/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final OurUserDetailsService ourUserDetailsService;


    @Autowired
    public ApplicationController(ApplicationService applicationService, OurUserDetailsService ourUserDetailsService) {
        this.applicationService = applicationService;
        this.ourUserDetailsService = ourUserDetailsService;
    }

    @PostMapping("/createApplication/{studentId}")
//    @RolesAllowed("ROLE_USER")
    public ResponseEntity<String> createApplication(@PathVariable("studentId") Long studentId, @RequestBody Application application) {
        applicationService.createApplication(studentId,application);
        return ResponseEntity.status(HttpStatus.CREATED).body("Application successfully sent!");
    }

    @GetMapping("/getApplications/{id}")
    public List<Application> getStudentApplications(@PathVariable Long id){

        return  applicationService.getApplicationsByStudentId(id);
    }


//    @GetMapping("/getStudentApplications/{studentId}")
//    @RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
//    public ResponseEntity<String> getStudentApplications(@PathVariable("studentId") Integer studentId) {
//
//        List<Application> applications = ourUserDetailsService.getApplicationsForStudent(studentId);
//
//        return ResponseEntity.ok("GET: getStudentApplications ");
//
//    }


    @GetMapping("/getAll")
//    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
//    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteApplicationById(@PathVariable("id") Long id) {
        applicationService.deleteApplicationById(id);
        return ResponseEntity.ok("Application successfully deleted");

    }

    @RolesAllowed("ROLE_ADMIN")

    @PatchMapping("/{applicationId}/accept")
    public ResponseEntity<ApplicationDto> acceptApplication(@PathVariable Long applicationId) {
        try {
            ApplicationDto application = applicationService.acceptApplication(applicationId);
            return ResponseEntity.ok(application);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @PatchMapping("/{applicationId}/reject")
    public ResponseEntity<ApplicationDto> rejectApplication(@PathVariable Long applicationId, @RequestParam String reason) {
        try {
            ApplicationDto application = applicationService.rejectApplication(applicationId, reason);
            return ResponseEntity.ok(application);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/tenants")
    public ResponseEntity<List<ApplicationDto>> getAcceptedUsersByResidence() {
        try {
            List<ApplicationDto> acceptedUsers = applicationService.getAcceptedUsersByResidence();
            return ResponseEntity.ok(acceptedUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/search")
    public ResponseEntity<List<ApplicationDto>> searchApplications(@RequestParam String search) {
        try {
            List<ApplicationDto> applications = applicationService.searchApplications(search);
            return ResponseEntity.ok(applications);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}