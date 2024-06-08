package com.acciojob.Vaccine_Management_System_June2024S.Controller;

import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddUserRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.UpdateUserEmail;
import com.acciojob.Vaccine_Management_System_June2024S.Service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UpdateUserEmail updateUserEmailObj;


    @PostMapping("add")
    public ResponseEntity addUser(@RequestBody AddUserRequest addUserRequest){
        try {
            String response = userService.addUser(addUserRequest);
            return new ResponseEntity(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("update-User-Email-by-Id")
    public String updateUserEmail(@RequestBody UpdateUserEmail updateUserEmailObj){
        try {
            return userService.updateUserEmailById(updateUserEmailObj);
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("getUser-ByEmail")
    public ResponseEntity<?> GetUserByEmail(@RequestParam("email") String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found for email: " + email);
        }
    }

}
