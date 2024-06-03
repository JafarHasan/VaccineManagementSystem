package com.acciojob.Vaccine_Management_System_June2024S.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="vaccinationCentre")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaccinationCentre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer centreId;

    private String openingTime;

    private String closingTime;

    private String address;

    private Integer doseQuantity;

    @OneToMany(mappedBy="vaccinationCentre",cascade=CascadeType.ALL)
    private List<Doctor> doctorList=new ArrayList<>(); //in one centre many DR can be available


}
