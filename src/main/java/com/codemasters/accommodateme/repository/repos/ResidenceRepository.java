package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence,Long> {

    List<Residence> findByStatus(String status);
    Optional<Residence> findByEmail(String email);
    List<Residence> findByUsersEmail(String email);

    @Query("SELECT a FROM Application a " +
            "INNER JOIN a.residence r " +
            "INNER JOIN r.location l " +
            "WHERE LOWER(r.name) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR " +
            "LOWER(l.area) LIKE CONCAT('%', LOWER(:searchTerm), '%')")
    List<Residence> searchByResidenceNameOrLocationArea(@Param("searchTerm") String search);

}
