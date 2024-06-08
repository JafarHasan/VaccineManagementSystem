package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Models.User;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.UserRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddUserRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.UpdateUserEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepositoryObj;

    public String addUser(AddUserRequest addUserRequest) throws Exception{
        User findUser=userRepositoryObj.findByEmailId(addUserRequest.getUserEmailId());
        if(addUserRequest.getUserEmailId()==null){
            throw new Exception("Null Email found!,Email id required!");
        }
        if(addUserRequest.getMobileNo().length()<10){
            throw new Exception("Invalid Phone no!");
        }
        if(findUser!=null){
            throw new Exception("Email already Exists! Try with another email");
        }
        User user=User.builder().userName(addUserRequest.getUserName())
                .userAge(addUserRequest.getUserAge())
                .userEmailId(addUserRequest.getUserEmailId())
                .gender(addUserRequest.getGender())
                .mobileNo(addUserRequest.getMobileNo())
                .build();
        user=userRepositoryObj.save(user);
        return "User added with user ID="+user.getUserId();
    }

    public String updateUserEmailById(UpdateUserEmail updateUserEmailObj) throws Exception{
        Optional<User> optionalUser=userRepositoryObj.findById(updateUserEmailObj.getUserId());

        //if id is Invalid
        if(optionalUser.isEmpty()){
            throw new Exception("Invalid User Id,User not found!");
        }

        User user=optionalUser.get();

        //if Old email not matched!
        if(!user.getUserEmailId().equals(updateUserEmailObj.getOldEmail())){
            throw new Exception("Email id not Found!");
        }
        //updatation success!
        user.setUserEmailId(updateUserEmailObj.getNewEmail());

        //save to DB
        user=userRepositoryObj.save(user);
        return "Email Updated Successfully!";

    }

    public User getUserByEmail(String email) throws Exception{
        User findUser=userRepositoryObj.findByEmailId(email);
        if(findUser==null){
            throw new Exception("invalid Email,User Not Found!");
        }
        return findUser;

    }
}
