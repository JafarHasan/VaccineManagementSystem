package com.acciojob.Vaccine_Management_System_June2024S.Models;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Timer;

@Entity
@Table(name="Appointment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date Date; //format-> "YYYY-MM-DD"

    private Time Time; //format-> "HH-MM-SS"

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AppointmentStatus appointmentStatus=AppointmentStatus.PENDING; //in the starting PENDING for all

    @JoinColumn
    @ManyToOne
    private Doctor doctor;  // for one DR many appointment can be appointed

    @JoinColumn
    @ManyToOne
    private User user;//one user can book many appointments

}
