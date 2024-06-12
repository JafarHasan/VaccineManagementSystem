package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AppointmentRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.ChangeAppointmentDateRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentServiceObj;
    @PostMapping("newBooking")
    private ResponseEntity<String> bookNewAppointment(@RequestBody AppointmentRequest appointmentRequest){
        try {
            String response = appointmentServiceObj.bookNewAppointment(appointmentRequest);
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeDate")
    public ResponseEntity<String> changeDateByAppointmentId(@RequestBody ChangeAppointmentDateRequest changeAppointmentDateRequestObj){
        try{
            String response=appointmentServiceObj.changeDateByAppointmentId(changeAppointmentDateRequestObj);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
