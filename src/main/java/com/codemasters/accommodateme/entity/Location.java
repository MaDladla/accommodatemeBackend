package com.codemasters.accommodateme.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    private String city;
    private String province;
    private String area;
    private String streetName;
    private int zipCode;
    private int streetNumber;

    @OneToOne
    @JoinColumn(name = "residence_id", referencedColumnName = "residence_id", nullable = false)
    private Residence residence;

}
