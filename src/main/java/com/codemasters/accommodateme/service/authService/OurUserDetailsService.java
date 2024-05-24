package com.codemasters.accommodateme.service.authService;

import com.codemasters.accommodateme.entity.Application;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.entity.UserPrincipal;
import com.codemasters.accommodateme.exception.StudentNotFoundException;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.codemasters.accommodateme.enumeration.RoleType.ROLE_ADMIN;
import static com.codemasters.accommodateme.enumeration.RoleType.ROLE_USER;

@Service
public class OurUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo ourUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  ourUserRepo.findByEmail(username).orElseThrow();

        if(user.getRole() != null){

            return new UserPrincipal(user, user.getRole().getPermission());
        }


        return new UserPrincipal(user, ROLE_ADMIN.name());
    }

    public User getUserById(Long user_id) {
        Optional<User> userOptional = ourUserRepo.findById(user_id);

        User user = userOptional.orElseThrow(() -> new StudentNotFoundException("User not found with id " + user_id));

        return user;
    }

    public Optional<User> findById(Long id) {
        return ourUserRepo.findById(id);
    }

    public List<User> findAll() {

        return ourUserRepo.findAll();
    }

    public List<Application> getApplicationsForStudent(Long studentId) {
        // Retrieve the student entity from the repository
        User users = ourUserRepo.findById(studentId).orElse(null);

        if (users != null) {
            // Return the list of applications associated with the student
            return users.getApplications();
        } else {
            // Handle the case where the student with the given ID is not found
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
    }

}
