package com.codemasters.accommodateme.repository.repos;
import com.codemasters.accommodateme.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findAllByUsersId(Long id);
    List<Application> findByUsersId(Long userId);
    List<Application> findByStatus(String status);
}