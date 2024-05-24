package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private Integer roomNumber;
    private String roomType;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Application> applications;

    @ManyToOne
    @JoinColumn(name = "residence_id")
    private Residence residence;

}

