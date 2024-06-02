package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDoctorRequest {
    private String name;
    private Integer age;
    private String email;
    private Gender gender;
}
