package com.codemasters.accommodateme.service;

import com.codemasters.accommodateme.dto.AnnouncementDto;
import com.codemasters.accommodateme.entity.Announcement;

import java.time.Instant;
import java.util.List;

public interface AnnouncemenentService {
    String addAnnouncement(Announcement announcements, Long resId, Long adminId);

    List<AnnouncementDto> getAllAnnouncements();

    AnnouncementDto updateAnnouncement(Announcement announcements, Long id, Long resId, Long adminId);

    AnnouncementDto getAnnouncementById(Long id);

    List<AnnouncementDto> getAnnouncementsByAdminId(Long adminId);

    List<AnnouncementDto> getAnnouncementsByResidenceId(Long residenceId);

    List<AnnouncementDto> getAnnouncementsByDatePosted(Instant createdAt);
    List<AnnouncementDto>getAnnouncementsByAdminEmail(String adminEmail);
    List<AnnouncementDto>getAnnouncementsByResEmail(String resEmail);

//    List<Announcement> searchAnnouncements(String heading, String body, Instant createdAt);
}
