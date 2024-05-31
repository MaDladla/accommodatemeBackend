package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.AnnouncementDto;
import com.codemasters.accommodateme.entity.Announcement;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.AnnouncementRepository;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.service.AnnouncemenentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncemenentService {

    private final AnnouncementRepository announcementsRepository;

    private final UserRepo userRepository;

    private final ResidenceRepository residenceRepository;

    private final EntityManager entityManager;

    public AnnouncementServiceImpl(AnnouncementRepository announcementsRepository, UserRepo userRepository, ResidenceRepository residenceRepository, EntityManager entityManager) {
        this.announcementsRepository = announcementsRepository;
        this.userRepository = userRepository;
        this.residenceRepository = residenceRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String addAnnouncement(Announcement announcements, Long resId, Long adminId) {
        Optional<Residence> resFound = residenceRepository.findById(resId);
        Optional<User> adminFound = userRepository.findById(adminId);

        if (adminFound.isPresent() && resFound.isPresent()) {
            User user = adminFound.get();
            Residence residence = resFound.get();

            announcements.setUsers(user);
            announcements.setResidence(residence);

            announcementsRepository.save(announcements);

            return "Announcement successfully saved";
        } else {
            return "Error saving announcement: Admin with ID " + adminId + " or Residence with ID " + resId + " does not exist";
        }
    }


    @Override
    public AnnouncementDto updateAnnouncement(Announcement announcements, Long id, Long resId, Long adminId) {
        Optional<Announcement> foundAnnouncement = announcementsRepository.findById(id);

        if (foundAnnouncement.isEmpty()) {
            throw new EntityNotFoundException("Announcement with ID " + id + " not found");
        }

        Announcement existingAnnouncement = foundAnnouncement.get();

        if (!existingAnnouncement.getResidence().getResidenceId().equals(resId)) {
            throw new EntityNotFoundException("Residence ID does not match the original residence ID of the announcement");
        }

        if (!existingAnnouncement.getUsers().getId().equals(adminId)) {
            throw new SecurityException("Admin ID does not match the original admin ID of the announcement");
        }

        existingAnnouncement.setBody(announcements.getBody());
        existingAnnouncement.setHeading(announcements.getHeading());
        existingAnnouncement.setImageUrl(announcements.getImageUrl());

        Announcement updatedAnnouncement = announcementsRepository.save(existingAnnouncement);

        return convertToDTO(updatedAnnouncement);
    }

    @Override
    public AnnouncementDto getAnnouncementById(Long id) {
        Optional<Announcement> foundAnnouncement = announcementsRepository.findById(id);

        if (foundAnnouncement.isPresent()) {
            return convertToDTO(foundAnnouncement.get());
        } else {

            throw new EntityNotFoundException("Announcement with ID " + id + " not found");
        }
    }


    @Override
    public List<AnnouncementDto> getAllAnnouncements() {
        List<Announcement> announcements = announcementsRepository.findAll();
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AnnouncementDto convertToDTO(Announcement announcement) {
        return new AnnouncementDto(
                announcement.getAnnouncementId(),
                announcement.getHeading(),
                announcement.getBody(),
                (announcement.getUsers() != null) ? announcement.getUsers().getEmail() : null,
                (announcement.getResidence() != null) ? announcement.getResidence().getName() : null,
                announcement.getCreatedAt()
        );
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByAdminId(Long adminId) {
        if (adminId == null) {
            throw new RuntimeException("Admin ID cannot be null");
        }

        List<Announcement> announcements = announcementsRepository.findByUsersId(adminId);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByResidenceId(Long resId) {
        if (resId == null) {
            throw new RuntimeException("Residence ID cannot be null");
        }

        List<Announcement> announcements = announcementsRepository.findByResidenceResidenceId(resId);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByDatePosted(Instant createdAt) {
        if (createdAt == null) {
            throw new RuntimeException("Residence ID cannot be null");
        }

        List<Announcement> announcements = announcementsRepository.findByCreatedAt(createdAt);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByAdminEmail(String adminEmail) {
        if (adminEmail == null) {
            throw new EntityNotFoundException("Admin ID cannot be null");
        }

        List<Announcement> announcements = announcementsRepository.findByUsersEmail(adminEmail);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsByResEmail(String resEmail) {
        if (resEmail == null) {
            throw new EntityNotFoundException("Residence ID cannot be null");
        }

        List<Announcement> announcements = announcementsRepository.findByResidenceEmail(resEmail);
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Announcement> searchAnnouncements(String heading, String body, Instant createdAt) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Announcement> cq = cb.createQuery(Announcement.class);
//        Root<Announcement> root = cq.from(Announcement.class);
//
//        List<Predicate> predicates = new ArrayList<>();
//        if (heading != null && !heading.isEmpty()) {
//            predicates.add(cb.like(cb.lower(cb.trim(root.get("heading"))), "%" + heading.toLowerCase() + "%"));
//        }
//        if (body != null && !body.isEmpty()) {
//            predicates.add(cb.like(cb.lower(cb.trim(root.get("body"))), "%" + body.toLowerCase() + "%"));
//        }
//        if (createdAt != null) {
//            predicates.add(cb.equal(root.get("createdAt"), createdAt));
//        }
//
//        cq.where(predicates.toArray(new Predicate[0]));
//
//        return entityManager.createQuery(cq).getResultList();
//    }
}

