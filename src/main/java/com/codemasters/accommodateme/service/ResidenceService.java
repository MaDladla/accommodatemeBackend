package com.codemasters.accommodateme.service;

import com.codemasters.accommodateme.dto.ResidenceDto;
import com.codemasters.accommodateme.entity.Residence;

import java.util.List;
import java.util.Optional;

public interface ResidenceService {

    Residence addResidence(Residence residence, Long adminId);

    Residence updateResidence(ResidenceDto residenceDto, Long resId, Long adminId);

    List<Residence> findAllResidences();

    Residence findById(Long id);

    Optional<Residence> findByEmail(String resEmail);

    List<Residence> findByUsersEmail(String adminEmail);

//    List<Residence> searchResidences(String keyword);

    ResidenceDto acceptResidence(Long resId,String status);

    ResidenceDto pendingResidence(Long resId,String status);

    ResidenceDto rejectResidence(Long resId,String status);

    List<ResidenceDto> acceptedResidence(String status);

    List<ResidenceDto> pendingResidences(String status);

    List<ResidenceDto> rejectedResidence(String status);

    List<ResidenceDto> searchResidences(String search);
}
