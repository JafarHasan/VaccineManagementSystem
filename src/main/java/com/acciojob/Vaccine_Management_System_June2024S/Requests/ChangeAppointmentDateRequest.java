package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import com.fasterxml.jackson.databind.DatabindException;
import lombok.*;

import java.util.Date;

@Data
public class ChangeAppointmentDateRequest {
    private Integer appointmentId;
    private Integer userId;
    private Date newDate;
}
