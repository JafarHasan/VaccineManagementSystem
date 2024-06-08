package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddCentreRequest {

    private String name;
    private Time openingTime;
    private Time closingTime;
    private String address;
    private Integer doseQuantity;
}
