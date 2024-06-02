package com.acciojob.Vaccine_Management_System_June2024S.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("home")
    public String getHomePage(){
        return "Hi everyone!";
    }
}
