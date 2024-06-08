package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Repository.AppointmentRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepositoryObj;

    public String bookNewAppointment(AppointmentRequest appointmentRequest) throws Exception {

        return "Appointment has been booked with id=";
    }
}