package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
    List<Announcement> findByUsersId(Long userId);
    List<Announcement> findByResidenceResidenceId(Long residenceId);
    List<Announcement> findByResidenceEmail(String resEmail);
    List<Announcement> findByUsersEmail(String adminEmail);
    List<Announcement> findByCreatedAt(Instant createdAt);

//    @Query("SELECT a FROM Announcements a WHERE LOWER(a.heading) LIKE %:heading% " +
//            "AND LOWER(a.body) LIKE %:body% AND a.createdAt = :createdAt")
//    List<Announcement> searchAnnouncements(String heading, String body, Instant createdAt);
}
