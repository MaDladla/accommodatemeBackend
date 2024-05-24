package com.codemasters.accommodateme.repository.repos;

import com.codemasters.accommodateme.entity.Issues;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssuesRepository extends JpaRepository<Issues,Long> {
    List<Issues> findByUsersId(Long user_id);
}
