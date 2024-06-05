package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Announcement;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.LocationRepository;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
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

    @Override
    public Location addLocation(Location location, Long resId) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence with ID " + resId + " does not exist"));
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
    public List<LocationDto> getLocationByStreetName(String streetName) {

        List<Location> location = locationRepository.findByStreetName(streetName);
        return location.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDto> getLocationByArea(String area) {
        List<Location> location = locationRepository.findByStreetName(area);
        return location.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Location updateLocation(Location location, Long id, Long resId) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with ID: " + id));

        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        existingLocation.setResidence(residence);
        existingLocation.setArea(location.getArea());
        existingLocation.setCity(location.getCity());
        existingLocation.setProvince(location.getProvince());
        existingLocation.setStreetName(location.getStreetName());
        existingLocation.setZipCode(location.getZipCode());
        existingLocation.setStreetNumber(location.getStreetNumber());

        // Save the updated location entity
        return locationRepository.save(existingLocation);
    }


    @Override
    public List<LocationDto> getLocationByCity(String city) {

        List<Location> location = locationRepository.findByStreetName(city);
        return location.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
