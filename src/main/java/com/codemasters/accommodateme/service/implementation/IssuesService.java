package com.codemasters.accommodateme.service.implementation;

import com.codemasters.accommodateme.dto.IssueDto;
import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.entity.User;
import com.codemasters.accommodateme.exception.IssueNotFoundException;
import com.codemasters.accommodateme.repository.repos.IssuesRepository;
import com.codemasters.accommodateme.service.authService.OurUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssuesService {

    public final IssuesRepository issuesRepository;
    public final OurUserDetailsService ourUserDetailsService;


    public Issues saveIssue(Long id, IssueDto issueDto){

        User user = ourUserDetailsService.getUserById(id);

        Issues issues = new Issues();
        issues.setUsers(user);
        issues.setStatus("Submitted");
        issues.setReportedAt(new Date());
        return issuesRepository.save(issues);
    }
    public List<Issues> findAllIssues(Long user_id){

        return issuesRepository.findByUsersId(user_id);
    }

    public Issues findIssue(Long id){
        Optional<Issues> optionalIssue = issuesRepository.findById(id);
        return optionalIssue.orElse(null);
    }
    public Issues updateIssue(Long issueId, IssueDto issuesDto) {
        Optional<Issues> optionalIssue = issuesRepository.findById(issueId);

        if (optionalIssue.isPresent()) {
            Issues existingIssue = optionalIssue.get();
            existingIssue.setTitle(issuesDto.getTitle());
            existingIssue.setDescription(issuesDto.getDescription());

            return issuesRepository.save(existingIssue);
        } else {
            throw new IssueNotFoundException("Couldn't find issue with id: "+ issueId);
        }
    }


}
