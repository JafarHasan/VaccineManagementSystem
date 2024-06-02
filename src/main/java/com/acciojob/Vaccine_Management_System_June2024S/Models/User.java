package com.acciojob.Vaccine_Management_System_June2024S.Models;

import com.acciojob.Vaccine_Management_System_June2024S.Enum.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    private Integer userAge;

    @Column(unique = true)
    private String userEmailId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String mobileNo;

}
