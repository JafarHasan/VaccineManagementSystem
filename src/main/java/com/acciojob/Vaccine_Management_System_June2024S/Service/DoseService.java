package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.AppointmentStatus;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Dose;
import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.AppointmentRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.DoseRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class DoseService {
    @Autowired
    private DoseRepository doseRepositoryObj;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;
    public String giveDose(Integer doseId,Integer appointmentId) throws Exception{

        //if appointment not booked
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isEmpty()) {
            throw new Exception("No appointment booked for this ID");
        }
        //get appointment
        Appointment appointment=appointmentOpt.get();

        //if Date is not matched with appointment Date or time has not started yet
        //we can't proceed an appointment at 2PM of before 3PM (if Appointment time is 3PM)
        if(appointment.getDate().compareTo(Date.valueOf(LocalDate.now()))!=0 && appointment.getTime().compareTo(Time.valueOf(LocalTime.now())) > 0){
            throw new Exception("Error! Your appointment date is : "+appointment.getDate()+"\n or your time has not started yet");
        }
        //get User
        User user=appointment.getUser();


        //if user already vaccinated
        if(user.getDose()!=null){
            throw new Exception("User already Vaccinated!");
        }

        Dose  dose=new Dose();
        dose.setDoseNo(doseId.toString());
        dose.setUser(user);
        dose.setDoseDate(appointment.getDate());
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
        //save this appointment to DB
        appointmentRepository.save(appointment);

        user.getAppointmentList().get(user.getAppointmentList().size()-1).setAppointmentStatus(AppointmentStatus.COMPLETED);
        userRepository.save(user);

        doseRepositoryObj.save(dose);
        return "Successfully vaccinated to : "+user.getUserName();

    }
    public Integer countOfAllGivenDoses() throws Exception{
        Integer no=doseRepositoryObj.findAll().size();
        if(no==0){
            throw new Exception("Total Dose given = 0");
        }
        return no;
    }
}