package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    private Integer dcotorId;
    private Integer userId;
    private Date appointmentDate;
    private Time appointmentTime;

}
