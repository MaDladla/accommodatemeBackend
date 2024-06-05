package com.codemasters.accommodateme.dto;

import com.codemasters.accommodateme.entity.Residence;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDto {

    private Long locationId;
    private String city;
    private String province;
    private String area;
    private String streetName;
    private String residence;
    private int zipCode;
    private int streetNumber;
}
