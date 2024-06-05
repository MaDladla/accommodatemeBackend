package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {
    List<Location> findByArea(String area);

    List<Location> findByStreetName(String streetName);

    List<Location> findByCity(String city);
}
