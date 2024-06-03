package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import lombok.Data;

@Data
public class UpdateUserEmail {

    private Integer userId;
    private String oldEmail;
    private String newEmail;
}
