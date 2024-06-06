package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.AnnouncementDto;
import com.codemasters.accommodateme.entity.Announcement;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.service.AnnouncemenentService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/auth/announcement")
@AllArgsConstructor
public class AnnouncementController {

    private final AnnouncemenentService announcementsService;

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/save/{adminId}/{resId}")
    public String addAnnouncement(@RequestBody Announcement announcement, @PathVariable Long adminId, @PathVariable Long resId) {
        return announcementsService.addAnnouncement(announcement, resId, adminId);
    }


    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/get/all")
    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementsService.getAllAnnouncements();
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/update/{id}/{adminId}/{resId}")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(
            @RequestBody Announcement announcements,
            @PathVariable Long id,
            @PathVariable Long adminId,
            @PathVariable Long resId) {

        try {
            AnnouncementDto updatedAnnouncement = announcementsService.updateAnnouncement(announcements, id, resId, adminId);
            return ResponseEntity.ok(updatedAnnouncement);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException se) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/find/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable Long id) {
        try {
            AnnouncementDto announcement = announcementsService.getAnnouncementById(id);
            return ResponseEntity.ok(announcement);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByAdminId(@PathVariable Long adminId) {
        try {
            List<AnnouncementDto> announcementsDTOs = announcementsService.getAnnouncementsByAdminId(adminId);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/find/res/{resId}")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByResidenceId(@PathVariable Long resId) {
        try {
            List<AnnouncementDto> announcementsDTOs = announcementsService.getAnnouncementsByResidenceId(resId);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/find-by/resEmail")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByResEmail(@RequestParam String resEmail) {
        try {
            List<AnnouncementDto> announcementsDTOs = announcementsService.getAnnouncementsByResEmail(resEmail);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/find-by/adminEmail")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByAdminEmail(@RequestParam String adminEmail) {
        try {
            List<AnnouncementDto> announcementsDTOs = announcementsService.getAnnouncementsByAdminEmail(adminEmail);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/find/date")
    public ResponseEntity<List<AnnouncementDto>> getAnnouncementsByDatePosted(@RequestParam Instant postedDate) {
        try {
            List<AnnouncementDto> announcementsDTOs = announcementsService.getAnnouncementsByDatePosted(postedDate);
            if (announcementsDTOs.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(announcementsDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @RolesAllowed("ROLE_ADMIN")
//    @GetMapping("/search")
//    public List<Announcement> searchAnnouncements(
//            @RequestParam(required = false) String heading,
//            @RequestParam(required = false) String body,
//            @RequestParam(required = false) Instant createdAt) {
//        return announcementsService.searchAnnouncements(heading, body, createdAt);
//    }

}
