package com.acciojob.Vaccine_Management_System_June2024S.Repository;

import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{

    @Query(value = "SELECT doctor_doctor_id FROM appointment WHERE id=?1",nativeQuery = true)
    Integer findDoctorIdByAppointmentId(Integer appointmentId);
}
