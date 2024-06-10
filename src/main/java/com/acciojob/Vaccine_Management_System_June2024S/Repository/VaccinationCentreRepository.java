package com.acciojob.Vaccine_Management_System_June2024S.Repository;

import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import com.acciojob.Vaccine_Management_System_June2024S.Models.VaccinationCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface VaccinationCentreRepository extends JpaRepository<VaccinationCentre,Integer> {
    VaccinationCentre findByAddress(String address);

}
