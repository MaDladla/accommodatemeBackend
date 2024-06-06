package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.exception.UnauthorizedException;
import com.codemasters.accommodateme.repository.repos.LocationRepository;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final ResidenceRepository residenceRepository;

    private final UserRepo userRepo;

    @Override
    public Location addLocation(Location location, Long resId,Long adminId) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence with ID " + resId + " does not exist"));

        User admin = userRepo.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin with ID " + adminId + " does not exist"));

        location.setResidence(residence);
        return locationRepository.save(location);
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> location = locationRepository.findAll();
        return location.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LocationDto> getLocationById(Long id) {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isPresent()) {
            return Optional.of(convertToDTO(foundLocation.get()));
        } else {

            throw new EntityNotFoundException("Location with ID " + id + " not found");
        }
    }

    @Override
    public Location updateLocation(Location location, Long id, Long resId,Long adminId) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));

        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        User user = userRepo.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with ID: " + adminId));

        if (!existingLocation.getResidence().getUsers().getId().equals(adminId)) {
            throw new UnauthorizedException("Admin ID does not match the existing admin ID.");
        }

        if (!existingLocation.getResidence().getResidenceId().equals(resId)) {
            throw new EntityNotFoundException("Residence ID does not match the existing residence ID.");
        }

        existingLocation.setResidence(residence);
        existingLocation.setArea(location.getArea());
        existingLocation.setCity(location.getCity());
        existingLocation.setProvince(location.getProvince());
        existingLocation.setStreetName(location.getStreetName());
        existingLocation.setZipCode(location.getZipCode());
        existingLocation.setStreetNumber(location.getStreetNumber());

        return locationRepository.save(existingLocation);
    }

    private LocationDto convertToDTO(Location location) {
        return new LocationDto(
               location.getLocationId(),
                location.getCity(),
                location.getProvince(),
                location.getArea(),
                location.getStreetName(),
                location.getResidence().getName(),
                location.getZipCode(),
                location.getStreetNumber()

        );
    }
}
