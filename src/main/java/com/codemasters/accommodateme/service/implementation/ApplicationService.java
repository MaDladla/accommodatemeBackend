package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.ApplicationNotFoundException;
import com.codemasters.accommodateme.repository.repos.ApplicationRepository;
import com.codemasters.accommodateme.service.authService.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Application acceptApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        application.setStatus("ACCEPTED");
        return applicationRepository.save(application);
    }

    public Application rejectApplication(Long applicationId, String reason) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with id " + applicationId));
        application.setStatus("REJECTED");
        application.setRejectionReason(reason);
        return applicationRepository.save(application);
    }

}
