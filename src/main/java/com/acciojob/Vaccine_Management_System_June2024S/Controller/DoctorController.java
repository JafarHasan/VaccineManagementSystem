package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddDoctorRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AssociateDoctorRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.UpdateDoctorName;
import com.acciojob.Vaccine_Management_System_June2024S.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("update-Doctor-Name-by-Id")
    public String updateDoctorNameById(@RequestBody UpdateDoctorName updateDoctorNameObj){
        try {
            return doctorServiceObj.updateDoctorNameById(updateDoctorNameObj);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @PutMapping("associateDoctorAndCentre")
    public ResponseEntity<String> associateDoctorAndCentre(@RequestBody AssociateDoctorRequest associateDoctorRequest){
        try{
            String response=doctorServiceObj.associateDoctorAndCentre(associateDoctorRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }



}
