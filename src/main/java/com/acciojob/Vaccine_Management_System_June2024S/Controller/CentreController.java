package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddCentreRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Service.CentreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/centre")
public class CentreController {

    @Autowired
    private CentreService centreServiceObj;

    @PostMapping("/add")
    public ResponseEntity addCentre(@RequestBody AddCentreRequest addCentreRequest){
        try{
            String response=centreServiceObj.addCentre(addCentreRequest);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
