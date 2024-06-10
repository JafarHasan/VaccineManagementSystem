package com.acciojob.Vaccine_Management_System_June2024S.Repository;

import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {


    @Query(value = "select *from doctor where associate_centre=?1",nativeQuery = true)
    List<Doctor> findAllByCentreId(Integer centerId);
}
