package com.codemasters.accommodateme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {

    @NotNull(message = "Name of student should not be null")
    private String fullName;
    @NotNull(message = "Room number of student should not be blank")
    private String roomNo;
    @NotNull(message = "Title of issue should not be null")
    private String title;
    @NotNull(message = "Description should not be null")
    private String description;

}
