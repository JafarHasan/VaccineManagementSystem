package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.UserRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepositoryObj;

    public String addUser(AddUserRequest addUserRequest){
        User user=User.builder().userName(addUserRequest.getUserName())
                .userAge(addUserRequest.getUserAge())
                .userEmailId(addUserRequest.getUserEmailId())
                .gender(addUserRequest.getGender())
                .mobileNo(addUserRequest.getMobileNo())
                .build();
        user=userRepositoryObj.save(user);
        return "User added with user ID="+user.getUserId();
    }
}
