package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.ResidenceDto;
import com.codemasters.accommodateme.entity.Residence;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.repository.repos.ResidenceRepository;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.service.ResidenceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidenceServiceImpl implements ResidenceService {

    private final ResidenceRepository residenceRepository;

    private final UserRepo userRepository;

    @Override
    public Residence addResidence(Residence residence, Long adminId) {
        Optional<User> adminOptional = userRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            User admin = adminOptional.get();

            if (residence.getTotalNumberOfRooms() < 0 || residence.getTotalNumberOfSingleRooms() < 0
                    || residence.getTotalNumberOfDoubleRooms() < 0) {
                throw new IllegalArgumentException("Room numbers cannot be negative");
            }

            Optional<Residence> existingResidenceOptional = residenceRepository
                    .findByEmail(residence.getEmail());
            if (existingResidenceOptional.isPresent()) {
                throw new IllegalArgumentException(
                        "Error saving residence: Residence with email " + residence.getEmail() + " already exists");
            } else if ((residence.getTotalNumberOfSingleRooms() + residence.getTotalNumberOfDoubleRooms()) != residence
                    .getTotalNumberOfRooms()) {
                throw new IllegalArgumentException(
                        "Error saving residence: Total number of single rooms and double rooms should equal total number of rooms");
            } else {
                residence.setUsers(admin);

                return residenceRepository.save(residence);
            }
        } else {
            throw new EntityNotFoundException("Admin with ID " + adminId + " does not exist");
        }
    }

    @Override
    public Residence updateResidence(ResidenceDto residenceDto, Long resId, Long adminId) {
        return residenceRepository.findById(resId).map(existingResidence -> {
            if (!existingResidence.getUsers().getId().equals(adminId)) {
                throw new EntityNotFoundException("Unauthorized to update this residence");
            }

            if (residenceDto.getName() != null) existingResidence.setName(residenceDto.getName());
            if (residenceDto.getSlogan() != null) existingResidence.setSlogan(residenceDto.getSlogan());
            if (residenceDto.getRegNo() != null) existingResidence.setRegNo(residenceDto.getRegNo());
            if (residenceDto.getProfileImage() != null) existingResidence.setProfileImage(residenceDto.getProfileImage());
            if (residenceDto.getTotalNumberOfRooms() != 0) existingResidence.setTotalNumberOfRooms(residenceDto.getTotalNumberOfRooms());
            if (residenceDto.getTotalNumberOfSingleRooms() != 0) existingResidence.setTotalNumberOfSingleRooms(residenceDto.getTotalNumberOfSingleRooms());
            if (residenceDto.getTotalNumberOfDoubleRooms() != 0) existingResidence.setTotalNumberOfDoubleRooms(residenceDto.getTotalNumberOfDoubleRooms());
            if (residenceDto.getNsfasDocument() != null) existingResidence.setNsfasDocument(residenceDto.getNsfasDocument());
            if (residenceDto.getImages() != null) existingResidence.setImages(residenceDto.getImages());
            if (residenceDto.getUtility() != null) existingResidence.setUtility(residenceDto.getUtility());

            return residenceRepository.save(existingResidence);
        }).orElseThrow(() -> new EntityNotFoundException("Residence with ID " + resId + " not found"));
    }

    @Override
    public List<Residence> findAllResidences() {
        return residenceRepository.findAll();
    }

    @Override
    public Residence findById(Long id) {
        return residenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found!"));
    }

    @Override
    public Optional<Residence> findByEmail(String resEmail) {
        return residenceRepository.findByEmail(resEmail);
    }

    @Override
    public List<Residence> findByUsersEmail(String adminEmail) {
        return residenceRepository.findByUsersEmail(adminEmail);
    }


//    @Override
//    public List<Residence> searchResidences(String keyword) {
//        return residenceRepository.findByNameContainingOrLocationAreaContainingOrEmailContaining(keyword, keyword, keyword);
//    }

    @Override
    public ResidenceDto acceptResidence(Long resId, String status) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        residence.setStatus("ACCEPTED");

        Residence updatedResidence = residenceRepository.save(residence);

        return convertToDTO(updatedResidence);
    }

    @Override
    public ResidenceDto pendingResidence(Long resId, String status) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        residence.setStatus("PENDING");

        Residence updatedResidence = residenceRepository.save(residence);

        return convertToDTO(updatedResidence);
    }

    @Override
    public ResidenceDto rejectResidence(Long resId, String status) {
        Residence residence = residenceRepository.findById(resId)
                .orElseThrow(() -> new EntityNotFoundException("Residence not found with ID: " + resId));

        residence.setStatus("REJECTED");

        Residence updatedResidence = residenceRepository.save(residence);

        return convertToDTO(updatedResidence);
    }


    @Override
    public List<ResidenceDto> acceptedResidence(String status) {
        if (!"ACCEPTED".equals(status)) {
            throw new RuntimeException("No accepted residence");
        }

        List<Residence> residences = residenceRepository.findByStatus(status);

        return residences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResidenceDto> pendingResidence(String status) {
        if (!"PENDING".equals(status)) {
            throw new EntityNotFoundException("No pending residence");
        }

        List<Residence> residences = residenceRepository.findByStatus(status);

        return residences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<ResidenceDto> rejectedResidence(String status) {
        if (!"REJECTED".equals(status)) {
            throw new EntityNotFoundException("No accepted residence");
        }

        List<Residence> residences = residenceRepository.findByStatus(status);

        return residences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ResidenceDto convertToDTO(Residence residence) {
        ResidenceDto residenceDto = new ResidenceDto();
        residenceDto.setName(residence.getName());
        residenceDto.setSlogan(residence.getSlogan());
        residenceDto.setEmail(residence.getEmail());
        residenceDto.setRegNo(residence.getRegNo());
        residenceDto.setTotalNumberOfRooms(residence.getTotalNumberOfRooms());
        residenceDto.setTotalNumberOfSingleRooms(residence.getTotalNumberOfSingleRooms());
        residenceDto.setTotalNumberOfDoubleRooms(residence.getTotalNumberOfDoubleRooms());
        residenceDto.setStatus(residence.getStatus());
        residenceDto.setUtility(residence.getUtility());
        residenceDto.setProfileImage(residence.getProfileImage());
        residenceDto.setImages(residence.getImages());
        residenceDto.setNsfasDocument(residence.getNsfasDocument());
        residenceDto.setUser(residence.getUsers().getEmail());
        residenceDto.setIssues(residence.getIssues());
        residenceDto.setLocation(residence.getLocation());
        residenceDto.setAppliedAt(residence.getAppliedAt());

        return residenceDto;
    }
}
