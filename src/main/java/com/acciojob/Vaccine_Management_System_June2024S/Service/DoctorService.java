package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddDoctorRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepositoryobj;

    public String addDoctor(AddDoctorRequest addDoctorRequest){
        Doctor doctor=Doctor.builder().name(addDoctorRequest.getName())
                .age(addDoctorRequest.getAge())
                .emailId(addDoctorRequest.getEmail())
                .gender(addDoctorRequest.getGender())
                .build();
        doctor=doctorRepositoryobj.save(doctor);

        return "Doctor Added with Id ="+doctor.getDoctorId();
    }
}
