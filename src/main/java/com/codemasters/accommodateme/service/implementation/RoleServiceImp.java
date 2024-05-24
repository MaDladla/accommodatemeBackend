package com.codemasters.accommodateme.repository.implementation;


import com.codemasters.accommodateme.entity.Role;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.repository.repos.RoleRepo;
import com.codemasters.accommodateme.repository.repos.UserRepo;
import com.codemasters.accommodateme.repository.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService<Role> {

    private final RoleRepo roleRepo;
    private  final UserRepo userRepo;

    @Override
    public Role create(Role data) {
        return roleRepo.save(data);
    }

    @Override
    public Collection<Role> getAll() {
        return roleRepo.findAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepo.findById(id).orElseThrow();
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Role update(Integer id, Role data) {
        return null;
    }

    @Override
    public Role getRoleByUserId(Long user_id) {

        return userRepo.findById(user_id).get().getRole();

    }

    @Override
    public void addRoleToUser(Long user_id, String roleName) {
        log.info("Adding a role {} to user id {}", roleName, user_id);

        User user = userRepo.findById(user_id).orElseThrow();

        Role role = roleRepo.findAll().stream()
                .filter(r-> r.getName().equals(roleName))
                .findFirst().orElseThrow();

        user.setRole(role);
        userRepo.save(user);


    }

    @Override
    public Role getRoleByUsername(String username) {

        User user = userRepo.findByEmail(username).orElseThrow();
        return user.getRole();
    }

    @Override
    public void updateUserRole(Long user_id, String roleName) {

    }
}
