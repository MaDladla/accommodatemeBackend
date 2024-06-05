package com.codemasters.accommodateme.service;

import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location addLocation(Location location, Long resId);

    List<LocationDto> getAllLocations();

    Optional<LocationDto> getLocationById(Long id);

    List<LocationDto> getLocationByStreetName(String streetName);

    List<LocationDto> getLocationByArea(String area);

    Location updateLocation(Location location, Long id, Long resId);

    List<LocationDto> getLocationByCity(String city);
}
