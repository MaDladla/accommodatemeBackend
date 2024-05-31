package com.codemasters.accommodateme.dto;

import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AnnouncementDto {
    private Long announcementId;
    private String heading;
    private String body;
    private String user;
    private String residence;
    private Instant createdAt;
}
