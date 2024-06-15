package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    private DoseService doseServiceObj;

    @PostMapping("/give")
    public ResponseEntity<String> giveDose(@RequestParam("doseId")Integer doseId,
                                           @RequestParam("appointmentId")Integer appointmentId){
        try{
            String response=doseServiceObj.giveDose(doseId,appointmentId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/totalGivenDose")
    public ResponseEntity<String> totalGivenDose(){
        try{
                Integer result=doseServiceObj.countOfAllGivenDoses();
                return new ResponseEntity<>(result.toString(),HttpStatus.OK);
        }
        catch (Exception e){
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
