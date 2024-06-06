package com.codemasters.accommodateme.service;

import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Location addLocation(Location location, Long resId,Long adminId);

    List<LocationDto> getAllLocations();

    Optional<LocationDto> getLocationById(Long id);

    Location updateLocation(Location location, Long id, Long resId,Long adminId);

}
