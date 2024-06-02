package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddDoctorRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorServiceObj;

    @PostMapping("add")
    public ResponseEntity addDoctor( @RequestBody AddDoctorRequest addDoctorRequest){
       String response=doctorServiceObj.addDoctor(addDoctorRequest);
       return new ResponseEntity(response, HttpStatus.OK);
    }



}
