package com.acciojob.Vaccine_Management_System_June2024S.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer DoseId;

    @Column(unique = true)//unique key
    private String doseNo;

    private Date doseDate;

    @JoinColumn(name="userId")
    @OneToOne //one dose taken by -> one user
    private User user; //mapping with user

}
