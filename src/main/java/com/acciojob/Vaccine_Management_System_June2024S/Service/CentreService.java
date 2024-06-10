package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Models.VaccinationCentre;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.VaccinationCentreRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddCentreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentreService {

    @Autowired
    private VaccinationCentreRepository vaccinationCentreRepositoryObj;

    //another way to create an OBJ from addReq. without using @Builder
    public String addCentre(AddCentreRequest addCentreRequest) throws Exception{

        //address is already Exists
        VaccinationCentre findCentre=vaccinationCentreRepositoryObj.findByAddress(addCentreRequest.getAddress());
        if(findCentre!=null){
            throw new Exception("Error! Centre is already exists at this Address!");
        }
        //Null Dose Quantity not accepted!

        Integer doseQuantity= addCentreRequest.getDoseQuantity();
       // System.out.println("Dose Quantity: " + doseQuantity); was printing null @data was not working in addReq .
        if (addCentreRequest.getDoseQuantity()==null || addCentreRequest.getDoseQuantity() <= 0) {
            throw new Exception("Error! Dose Quantity cant be 0!");
        }

        //from my request Entity i am creating the actual entity:bcz entity saves into the DB
        VaccinationCentre vaccinationCentre=new VaccinationCentre();

        vaccinationCentre.setCentreName(addCentreRequest.getName());
        vaccinationCentre.setAddress(addCentreRequest.getAddress());
        vaccinationCentre.setOpeningTime(addCentreRequest.getOpeningTime());
        vaccinationCentre.setClosingTime(addCentreRequest.getClosingTime());
        vaccinationCentre.setDoseQuantity(addCentreRequest.getDoseQuantity());

        vaccinationCentre=vaccinationCentreRepositoryObj.save(vaccinationCentre);
        return "Centre Added Successfully! Id is="+vaccinationCentre.getCentreId();
    }


}
