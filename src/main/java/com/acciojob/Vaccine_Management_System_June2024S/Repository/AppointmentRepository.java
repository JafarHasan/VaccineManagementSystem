package com.acciojob.Vaccine_Management_System_June2024S.Repository;

import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{

}
