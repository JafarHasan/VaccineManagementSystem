package com.acciojob.Vaccine_Management_System_June2024S.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDoctorName {
    private Integer doctorId;
    private String oldName;
    private String newName;

}
