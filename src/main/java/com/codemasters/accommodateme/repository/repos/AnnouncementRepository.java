package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {
}
