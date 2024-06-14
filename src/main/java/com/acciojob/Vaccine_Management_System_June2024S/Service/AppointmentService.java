package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.AppointmentStatus;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Appointment;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.AppointmentRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.DoctorRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.UserRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AppointmentRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.ChangeAppointmentDateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        //if everything is fine then we can book an appointment
        //here i am using getter setter to build the bean of appointment i,e. without using @Builder annotaion
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
        String body = "Dear "+user.getUserName()+",\n\n" +
                "This is confirmation email to you that your appointment has been successfully booked. \n" +
                "We are pleased to confirm that your preferred date and time have been secured.\n \n" +
                "Appointment Details:\n\n" +
                "Date: "+newAppointment.getDate()+"\n" +
                "Time: "+newAppointment.getTime()+"\n" +
                "Location: "+doctor.getVaccinationCentre().getAddress()+"\n\n"+
                "Dr Name: "+doctor.getName()+"\n\n"+
                "Thank You";
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(user.getUserEmailId());
        mailMessage.setFrom("se.hasanjafar@gmail.com");
        mailMessage.setSubject("Appointment Confirmation!");
        mailMessage.setText(body);

        javaMailSenderObj.send(mailMessage);
        return "Appointment has been booked with id: "+newAppointment.getId();
    }
    public String changeDateByAppointmentId(ChangeAppointmentDateRequest changeAppointmentDateRequestObj)throws Exception{

        // Check if any of the required fields is null
        if (changeAppointmentDateRequestObj.getUserId() == null ||
                changeAppointmentDateRequestObj.getAppointmentId() == null ||
                changeAppointmentDateRequestObj.getNewDate() == null) {
            throw new Exception("One or more required fields are null. Please provide values for all fields.");
        }

        Integer userId=changeAppointmentDateRequestObj.getUserId();
        Integer appointmentId=changeAppointmentDateRequestObj.getAppointmentId();
        Date newDate=changeAppointmentDateRequestObj.getNewDate();

        Optional<User> optionalUser=userRepositoryObj.findById(userId);
        //if id is Invalid
        if(optionalUser.isEmpty()){
            throw new Exception("Invalid User Id,You can't change the Date!");
        }

        Optional<Appointment> optionalAppointment=appointmentRepositoryObj.findById(appointmentId);
        if(optionalAppointment.isEmpty()){
            throw new Exception("Invalid Appointment ID,No Appointment has been booked with this id before!");
        }

        User user=optionalUser.get();
        Appointment appointment=optionalAppointment.get();

        //if user input newDate before today's date then it will be invalid
        //but it will work on today date 2024-06-13 newDate 2024-06-13 but not before this date
        // Get today's date
        LocalDate currentDate = LocalDate.now();
        //converting LocalDate to Date
        Date todatDate=Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if(newDate.before(todatDate)){
            throw new Exception("The new date can not be before:"+todatDate);
        }

        if(!appointment.getUser().equals(user)){
            throw new Exception("you cant change the Date as you are not Authorized with this Appointment!");
        }
        if(user.getAppointmentList().get(user.getAppointmentList().size()-1).equals(AppointmentStatus.COMPLETED)){
            throw new Exception("This Appointment has been Completed Already!");
        }
        appointment.setDate(newDate);
        appointment=appointmentRepositoryObj.save(appointment);
        return "Date has changed Successfully!\n New Date="+appointment.getDate();
    }

    //Here i am not handling all Exceptions as above API
    public  String cancelAppointmentById(Integer appointmentId,Integer userId) throws Exception{
        //get appointment from appointment id;
        Appointment appointment=appointmentRepositoryObj.findById(appointmentId).get();

        //if already Cancelled;
        if(appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELLED)){
            throw new Exception("Its Already Cancelled before!");
        }

        //get user from user id
        User user=userRepositoryObj.findById(userId).get();

        //get dr.ID from appointment id
        Integer doctorId=appointmentRepositoryObj.findDoctorIdByAppointmentId(appointmentId);
        //from dr Id find Dr
        Doctor doctor=doctorRepositoryObj.findById(doctorId).get();

        //cancel this appointment from dr AppointmentList
        for(Appointment app:doctor.getAppointmentList()){
            if(app.getId().equals(appointmentId)){
                app.setAppointmentStatus(AppointmentStatus.CANCELLED);
                break;
            }
        }

        //cancel from user appointmentList

        for(Appointment app:user.getAppointmentList()) {
            if (app.getId().equals(appointmentId)) {
                app.setAppointmentStatus(AppointmentStatus.CANCELLED);
                break;
            }
        }
        //cancel this appointment from Db
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

        //save updated info of all three in DB
        appointmentRepositoryObj.save(appointment);
        doctorRepositoryObj.save(doctor);
        userRepositoryObj.save(user);

        return "Appointment has cancelled!";

        //wrong Methods
        //doctor.getAppointmentList().get(appointmentId).setAppointmentStatus(AppointmentStatus.CANCELLED);
        //user.getAppointmentList().get(appointmentId).setAppointmentStatus(AppointmentStatus.CANCELLED);
    }


}