package com.codemasters.accommodateme.controller;


import com.codemasters.accommodateme.dto.ResidenceDto;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.service.ResidenceService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/residence")
@AllArgsConstructor
public class ResidenceController {

    private final ResidenceService residenceService;

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/register/{adminId}")
    public ResponseEntity<Residence> addResidence(@RequestBody Residence residence, @PathVariable Long adminId) {
        try {
            Residence savedResidence = residenceService.addResidence(residence, adminId);
            return ResponseEntity.ok(savedResidence);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/update/{resId}/{adminId}")
    public ResponseEntity<Residence> updateResidence(@RequestBody ResidenceDto residenceDto,
                                                     @PathVariable Long resId,
                                                     @PathVariable Long adminId) {
        try {
            Residence updatedResidence = residenceService.updateResidence(residenceDto, resId, adminId);
            return ResponseEntity.ok(updatedResidence);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @GetMapping("/getAll")
    public ResponseEntity<List<Residence>> findAllResidences() {
        List<Residence> residences = residenceService.findAllResidences();
        return ResponseEntity.ok(residences);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<Residence> findById(@PathVariable Long id) {
        try {
            Residence residence = residenceService.findById(id);
            return ResponseEntity.ok(residence);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/email")
    public ResponseEntity<Residence> findByEmail(@RequestParam String resEmail) {
        Optional<Residence> residence = residenceService.findByEmail(resEmail);
        return residence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/user-email")
    public ResponseEntity<List<Residence>> findByUsersEmail(@RequestParam String adminEmail) {
        List<Residence> residences = residenceService.findByUsersEmail(adminEmail);
        return ResponseEntity.ok(residences);
    }


    @RolesAllowed("ROLE_SYSADMIN")
    @PatchMapping("/accept/{resId}")
    public ResponseEntity<ResidenceDto> acceptResidence(@PathVariable Long resId, @RequestParam String status) {
        try {
            ResidenceDto residenceDto = residenceService.acceptResidence(resId, status);
            return ResponseEntity.ok(residenceDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @PatchMapping("/pending/{resId}")
    public ResponseEntity<ResidenceDto> pendingResidence(@PathVariable Long resId, @RequestParam String status) {
        try {
            ResidenceDto residenceDto = residenceService.acceptResidence(resId, status);
            return ResponseEntity.ok(residenceDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @PatchMapping("/reject/{resId}")
    public ResponseEntity<ResidenceDto> rejectResidence(@PathVariable Long resId, @RequestParam String status) {
        try {
            ResidenceDto residenceDto = residenceService.rejectResidence(resId, status);
            return ResponseEntity.ok(residenceDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @GetMapping("/accepted")
    public ResponseEntity<List<ResidenceDto>> acceptedResidence(@RequestParam String status) {
        try {
            List<ResidenceDto> residences = residenceService.acceptedResidence(status);
            return ResponseEntity.ok(residences);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @GetMapping("/pending")
    public ResponseEntity<List<ResidenceDto>> pendingResidences(@RequestParam String status) {
        try {
            List<ResidenceDto> residences = residenceService.acceptedResidence(status);
            return ResponseEntity.ok(residences);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RolesAllowed("ROLE_SYSADMIN")
    @GetMapping("/rejected")
    public ResponseEntity<List<ResidenceDto>> rejectedResidence(@RequestParam String status) {
        try {
            List<ResidenceDto> residences = residenceService.rejectedResidence(status);
            return ResponseEntity.ok(residences);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
