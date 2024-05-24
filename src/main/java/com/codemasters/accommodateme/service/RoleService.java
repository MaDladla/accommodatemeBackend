package com.codemasters.accommodateme.repository.services;



import com.codemasters.accommodateme.entity.Role;

import java.util.Collection;


public interface RoleService< T extends Role> {


    /**
     * Creating a Role
     */

    public T create(T data);

    /**
     * Get a List of Role
     */

    public Collection<T> getAll();

    /**
     * Get Role by ID
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */
    public T getById(Integer id);


    /**
     * Get Role by ID
     * @param id must not be {@literal null}.
     */
    public void delete(Integer id);

    /**
     * Get Role by ID
     * @param id   must not be {@literal null}.
     * @param data   must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */

    public T  update(Integer id, T data);

    /**
     * Get Role by User ID
     * @param user_id   must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     */

    public T getRoleByUserId(Long user_id);

    /**
     * Add a role to user. Assign user roles
     * @param user_id   must not be {@literal null}.
     * @param roleName   must not be {@literal null}.
     */

    public void addRoleToUser(Long user_id, String roleName);


    /**
     * Get role  by username
     * @param username   must not be {@literal null}.
     */
    public T getRoleByUsername(String username);

    /**
     * Add a role to user. Assign user roles
     * @param user_id   must not be {@literal null}.
     * @param roleName   must not be {@literal null}.
     */
    public void updateUserRole(Long user_id, String roleName);
}
