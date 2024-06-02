package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddUserRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("add")
    public ResponseEntity addUser(@RequestBody AddUserRequest addUserRequest){
        String response= userService.addUser(addUserRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
