package com.codemasters.accommodateme.repository.repos;
import com.codemasters.accommodateme.dto.ApplicationDto;
import com.codemasters.accommodateme.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findAllByUsersId(Long id);
    List<Application> findByUsersId(Long userId);
    List<Application> findByStatus(String status);

    @Query("SELECT a FROM Application a WHERE " +
            "LOWER(a.firstName) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR " +
            "LOWER(a.lastName) LIKE CONCAT('%', LOWER(:searchTerm), '%') OR " +
            "CAST(a.idNumber AS string) LIKE CONCAT('%', :searchTerm, '%') OR " +
            "LOWER(a.email) LIKE CONCAT('%', LOWER(:searchTerm), '%')")
    List<Application> searchByFirstNameOrLastNameOrIdNumberOrEmail(@Param("searchTerm") String search);

}