package com.codemasters.accommodateme.dto;

import com.codemasters.accommodateme.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidenceDto {

    @NotNull(message = "Name of residence should not be null")
    @NotBlank(message = "Name of residence should not be blank")
    private String name;

    @NotNull(message = "slogan of residence should not be null")
    @NotBlank(message = "slogan of residence should not be blank")
    private String slogan;

    @NotNull(message = "email of residence should not be null")
    @NotBlank(message = "email of residence should not be blank")
    private String email;

    @NotNull(message = "registration number of residence should not be null")
    @NotBlank(message = "registration number of residence should not be blank")
    private String regNo;

    @NotNull(message = "room number of residence should not be 0")
    @NotBlank(message = "room number of residence should not be blank")
    private int totalNumberOfRooms;

    private int totalNumberOfSingleRooms;

    private int totalNumberOfDoubleRooms;

    private String status;

    private List<String> utility = new ArrayList<>();

    private String profileImage;

    private List<String> images = new ArrayList<>();

    private String nsfasDocument;

    private String User;

    private List<Issues> issues;

    private Location location;

    private Instant appliedAt;
}
