package com.codemasters.accommodateme.controller;

import com.codemasters.accommodateme.dto.IssueDto;
import com.codemasters.accommodateme.entity.Issues;
import com.codemasters.accommodateme.service.implementation.IssuesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class IssuesController {

    private final IssuesService issuesService;


    @PostMapping("/addIssue/{id}")
    public ResponseEntity<String> saveIssue(@PathVariable Long id, @RequestBody @Valid IssueDto issueDto){
        try{
            issuesService.saveIssue(id,issueDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Issue saved successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save issue information");
        }
    }

    @PutMapping("/updateIssue/{id}")
    public ResponseEntity<?> updateIssue(@PathVariable Long id, @RequestBody IssueDto issueDto){

        issuesService.updateIssue(id,issueDto);

        return ResponseEntity.ok("Issue successfully updated");
    }

    @GetMapping("/getAllIssues/{id}")
    public List<Issues> getAllIssues(@PathVariable Long id){
        return issuesService.findAllIssues(id);
    }

    @GetMapping("/getIssue/{id}")
    public Issues getIssue(@PathVariable Long id){
        return issuesService.findIssue(id);
    }


}
