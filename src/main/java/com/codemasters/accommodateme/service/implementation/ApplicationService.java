package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.ApplicationDto;
import com.codemasters.accommodateme.dto.LocationDto;
import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.entity.Location;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.ApplicationNotFoundException;
import com.codemasters.accommodateme.exception.EntityNotFoundException;
import com.codemasters.accommodateme.repository.repos.ApplicationRepository;
import com.codemasters.accommodateme.service.authService.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final OurUserDetailsService ourUserDetailsService;
    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository,  OurUserDetailsService ourUserDetailsService) {
        this.applicationRepository = applicationRepository;
        this.ourUserDetailsService = ourUserDetailsService;
    }


    public Application createApplication(Long userId, Application application) {

        User user = ourUserDetailsService.getUserById(userId);
        application.setUsers(user);
        application.setAppliedAt(new Date());
        return applicationRepository.save(application);

    }
    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public List<Application> getApplicationsByStudentId(Long studentId) {
        return applicationRepository.findByUsersId(studentId);
    }

    public String deleteApplicationById(Long id) {

        Application application = applicationRepository.findById(id).orElseThrow(()-> new ApplicationNotFoundException("User not found with id " + id));
        applicationRepository.deleteById(id);

        return "Application successfully deleted";
    }

    public ApplicationDto acceptApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id " + applicationId));
        application.setStatus("ACCEPTED");
        Application savedApplication = applicationRepository.save(application);
        return convertToDTO(savedApplication);
    }

    public ApplicationDto rejectApplication(Long applicationId, String reason) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id " + applicationId));
        application.setStatus("REJECTED");
        application.setRejectionReason(reason);
        Application savedApplication = applicationRepository.save(application);
        return convertToDTO(savedApplication);
    }

    public List<ApplicationDto> getAcceptedUsersByResidence() {
        List<Application> applications = applicationRepository.findByStatus("ACCEPTED");

        if(applications.isEmpty()){
            throw new EntityNotFoundException("No tenants found ");
        }
        else {
            return applications.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    public List<ApplicationDto> searchApplications(String search) {
        List<Application> applications = applicationRepository.searchByFirstNameOrLastNameOrIdNumberOrEmail(search);
        if (applications.isEmpty()) {
            throw new EntityNotFoundException("No applications found with search term: " + search);
        }
        return applications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    private ApplicationDto convertToDTO(Application application) {
        return new ApplicationDto(
                application.getId(),
               application.getFirstName(),
               application.getLastName(),
         application.getIdNumber(),
         application.getDateOfBirth(),
        application.getGender(),
         application.getEmail(),
         application.getAddress(),
       application.getContactDetails(),
         application.getGuardianFullName(),
        application.getGuardianSurname(),
        application.getRelationship(),
         application.getGuardianEmail(),
        application.getGuardianContacts(),
         application.getGuardianIdocument(),
         application.getInstitution(),
        application.getCampus(),
       application.getBursary(),
       application.getYearOfStudy(),
        application.getFaculty(),
         application.getIDocument(),
         application.getFundDocument(),
       application.getProofOfReg(),
         application.getTranscript(),
         application.getStudentStatus(),
              application.getResponseMessage(),
        application.getStatus(),
       application.getAppliedAt(),
       application.getRejectionReason(),
                application.getRoom() != null ? application.getRoom().getRoomNumber() : null,
                application.getUsers() != null ? application.getUsers().getEmail() : null,
                application.getResidence() != null ? application.getResidence().getName() : null

        );
    }

}
