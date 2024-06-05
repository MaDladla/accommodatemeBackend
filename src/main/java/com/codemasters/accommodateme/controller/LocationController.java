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
    @PostMapping("/save/{resId}")
    public ResponseEntity<Location> addLocation(@RequestBody Location location, @PathVariable Long resId) {
        Location newLocation = locationService.addLocation(location, resId);
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

//    @RolesAllowed("ROLE_ADMIN")
//    @GetMapping("/findByArea")
//    public ResponseEntity<List<LocationDto>> findLocationByArea(@RequestParam String area) {
//        List<LocationDto> locations = locationService.getLocationByArea(area);
//        if (locations.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(locations);
//    }

//    @RolesAllowed("ROLE_ADMIN")
//    @GetMapping("/findByStreetName")
//    public ResponseEntity<List<LocationDto>> findLocationByStreetName(@RequestParam String streetName) {
//        List<LocationDto> locations = locationService.getLocationByStreetName(streetName);
//        if (locations.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(locations);
//    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/update/{id}/resId/{resId}")
    public ResponseEntity<Location> updateLocation(@RequestBody Location location, @PathVariable Long id, @PathVariable Long resId) {
        try {
            Location updatedLocation = locationService.updateLocation(location, id, resId);
            return ResponseEntity.ok(updatedLocation);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

//    @RolesAllowed("ROLE_ADMIN")
//    @GetMapping("/findbyCity")
//    public ResponseEntity<List<LocationDto>> findLocationByCity(@RequestParam String city) {
//        List<LocationDto> locations = locationService.getLocationByCity(city);
//        if (locations.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(locations);
//    }
}
