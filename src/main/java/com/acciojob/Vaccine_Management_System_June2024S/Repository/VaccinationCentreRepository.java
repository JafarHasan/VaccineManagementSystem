package com.acciojob.Vaccine_Management_System_June2024S.Repository;

import com.acciojob.Vaccine_Management_System_June2024S.Models.VaccinationCentre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationCentreRepository extends JpaRepository<VaccinationCentre,Integer> {
    VaccinationCentre findByAddress(String address);
}
