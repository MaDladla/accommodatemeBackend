package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.service.LocationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping("/save/{resId}/{adminId}")
    public ResponseEntity<Location> addLocation(@RequestBody Location location, @PathVariable Long resId,@PathVariable Long adminId) {
        Location newLocation = locationService.addLocation(location, resId, adminId);
        return ResponseEntity.ok(newLocation);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/findById/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/update/{id}/resId/{resId}/{adminId}")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location, @PathVariable Long id, @PathVariable Long resId,@PathVariable Long adminId) {
        try {
            Location updatedLocation = locationService.updateLocation(location, id, resId,adminId);
            return ResponseEntity.ok(updatedLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
