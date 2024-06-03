package com.acciojob.Vaccine_Management_System_June2024S.Models;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    private Integer userAge;

    @Column(unique = true)
    private String userEmailId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String mobileNo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Dose dose; //bidirectional mapping with dose

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList = new ArrayList<>();
}
