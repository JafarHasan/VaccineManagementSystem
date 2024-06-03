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
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String emailId;

    private Integer age;

    @ManyToOne //many DR are available at one centre
    @JoinColumn(name = "AssociateCentre")
    private VaccinationCentre vaccinationCentre;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL) //one dcotor can be appointed to man
    private List<Appointment> appointmentList = new ArrayList<>();
}
