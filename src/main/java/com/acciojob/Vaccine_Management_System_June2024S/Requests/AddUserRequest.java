package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    private String userName;
    private Integer userAge;
    private String userEmailId;
    private Gender gender;
    private String mobileNo;
}
