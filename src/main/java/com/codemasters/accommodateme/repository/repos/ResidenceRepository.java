package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence,Long> {

    List<Residence> findByStatus(String status);
    Optional<Residence> findByEmail(String email);
    List<Residence> findByUsersEmail(String email);
//    List<Residence> findByNameContainingOrLocationAreaContainingOrEmailContaining(String name, String area, String email);
}
