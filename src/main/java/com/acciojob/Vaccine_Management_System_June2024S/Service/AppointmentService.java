package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.AppointmentStatus;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.AppointmentRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.DoctorRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.UserRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepositoryObj;

    @Autowired
    private DoctorRepository doctorRepositoryObj;

    @Autowired
    private UserRepository userRepositoryObj;

    @Autowired
    private JavaMailSender javaMailSenderObj;

    public String bookNewAppointment(AppointmentRequest appointmentRequest) throws Exception {

        Optional<Doctor> optionalDoctor=doctorRepositoryObj.findById(appointmentRequest.getDoctorId());
        if(optionalDoctor.isEmpty()) {
            throw new Exception("Dr Not found!");
        }

        Optional<User> optionalUser=userRepositoryObj.findById(appointmentRequest.getUserId());
        //if id is Invalid
        if(optionalUser.isEmpty()){
            throw new Exception("Invalid User Id,User not found!");
        }

        //find Dr Entity
        Doctor doctor=optionalDoctor.get();
        //get user Entity
        User user=optionalUser.get();

        if(doctor.getVaccinationCentre()==null){
            throw new Exception("Dr is not associated with any centre!");
        }

        /*The code is to prevent booking a new appointment if the user already has a pending appointment.
        If the last appointment in the user's list is pending (AppointmentStatus.PENDING), it indicates that the user already
        has an ongoing or pending appointment. In such a case, the code throws a PendingAppointment exception,
         suggesting that the user cannot book a new appointment until the current pending appointment is resolved or canceled.*/

        Appointment lastAppointment=null;
        if(user.getAppointmentList().size()>0){
            lastAppointment=user.getAppointmentList().get(user.getAppointmentList().size()-1);
            if(lastAppointment!=null && lastAppointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)){
                throw new Exception("your Appointment is already pending with ID :"+lastAppointment.getId());
            }
        }
        //if everything if fine then we can book an appointment
        //here is am using getter setter to build the bean of appointment i,e. without using @Builder annotaion
        Appointment newAppointment=new Appointment();
        newAppointment.setDoctor(doctor);
        newAppointment.setUser(user);
        newAppointment.setDate(appointmentRequest.getAppointmentDate());
        newAppointment.setTime(appointmentRequest.getAppointmentTime());

        //save to DB
        newAppointment=appointmentRepositoryObj.save(newAppointment);

        //hv to save this appointment with Dr and User also bcz both contains List<Appointment>
        doctor.getAppointmentList().add(newAppointment);
        user.getAppointmentList().add(newAppointment);
        doctorRepositoryObj.save(doctor);
        userRepositoryObj.save(user);

        //FOR SENDING CONFIRMATION THROUGH E-MAIL
        String body = "Dear"+user.getUserName()+",\n\n" +
                "This is confirmation email to you that your appointment has been successfully booked. \n" +
                "We are pleased to confirm that your preferred date and time have been secured.\n \n" +
                "Appointment Details:\n\n" +
                "Date: "+newAppointment.getDate()+"\n" +
                "Time: "+newAppointment.getTime()+"\n" +
                "Location: "+doctor.getVaccinationCentre().getAddress()+"\n\n"+
                "Dr Name: "+doctor.getName()+"\n\n"+
                "Thank You";
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("se.hasanjafar@gmail.com");
        mailMessage.setTo(user.getUserEmailId());
        mailMessage.setSubject("Appointment Confirmation!");
        mailMessage.setText(body);

        javaMailSenderObj.send(mailMessage);
        return "Appointment has been booked with id: "+newAppointment.getId();
    }
}