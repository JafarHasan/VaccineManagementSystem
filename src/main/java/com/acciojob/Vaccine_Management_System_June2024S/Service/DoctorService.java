package com.acciojob.Vaccine_Management_System_June2024S.Service;

import com.acciojob.Vaccine_Management_System_June2024S.Models.VaccinationCentre;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.VaccinationCentreRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AddDoctorRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Models.Doctor;
import com.acciojob.Vaccine_Management_System_June2024S.Repository.DoctorRepository;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.AssociateDoctorAndCentreRequest;
import com.acciojob.Vaccine_Management_System_June2024S.Requests.UpdateDoctorName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepositoryobj;

    @Autowired
    private VaccinationCentreRepository vaccinationCentreRepositoryObj;

    public String addDoctor(AddDoctorRequest addDoctorRequest){
        Doctor doctor=Doctor.builder().name(addDoctorRequest.getName())
                .age(addDoctorRequest.getAge())
                .emailId(addDoctorRequest.getEmail())
                .gender(addDoctorRequest.getGender())
                .build();
        doctor=doctorRepositoryobj.save(doctor);

        return "Doctor Added with Id ="+doctor.getDoctorId();
    }

    public String updateDoctorNameById(UpdateDoctorName updateDoctorName) throws Exception{
        Optional<Doctor> optionalDoctor=doctorRepositoryobj.findById(updateDoctorName.getDoctorId());
        if(optionalDoctor.isEmpty()){
            throw new Exception("Invalid Id ,Dr not found!");
        }

        Doctor doctor=optionalDoctor.get();
        if(!doctor.getName().equals(updateDoctorName.getOldName())){
            throw new Exception("Invalid name:"+updateDoctorName.getOldName());
        }

        doctor.setName(updateDoctorName.getNewName());
        doctor=doctorRepositoryobj.save(doctor);
        return "Name updated Successfully!";
    }

    public String associateDoctorAndCentre(AssociateDoctorAndCentreRequest associateDoctorRequest) throws Exception{
        //find Dr by doctorId
        Optional<Doctor> optionalDoctor=doctorRepositoryobj.findById(associateDoctorRequest.getDoctorId());
        if(optionalDoctor.isEmpty()) {
            throw new Exception("Dr Not found!");
        }

        //find Centre by CentreId
        Optional<VaccinationCentre> optionalVaccinationCentre=vaccinationCentreRepositoryObj.findById(associateDoctorRequest.getCentreId());
        if(optionalVaccinationCentre.isEmpty()){
            throw new Exception("Centre Not Found!");
        }
        //if dr found get that dr entity
        Doctor doctor=optionalDoctor.get();

        //if centre found get centre Enitity
        VaccinationCentre vaccinationCentre=optionalVaccinationCentre.get();

        if((doctor.getVaccinationCentre()!=null) && doctor.getVaccinationCentre().equals(vaccinationCentre)){
            throw new Exception("Already associated!");
        }

        //set centre with dr entity
        doctor.setVaccinationCentre(vaccinationCentre);
        doctorRepositoryobj.save(doctor); // Save doctor with updated association

        vaccinationCentre.getDoctorList().add(doctor);
        vaccinationCentreRepositoryObj.save(vaccinationCentre);

        return doctor.getName()+" associated with "+vaccinationCentre.getCentreName()+" Successfully!";
    }

    public List<String> listOfAllAssociatedDrById(Integer centreId) throws Exception{
        Optional<VaccinationCentre> optionalVaccinationCentre=vaccinationCentreRepositoryObj.findById(centreId);
        if(optionalVaccinationCentre.isEmpty()){
            throw new Exception("Invalid Centre id!");
        }

        VaccinationCentre centre=optionalVaccinationCentre.get();

        List<Doctor> doctorList=doctorRepositoryobj.findAllByCentreId(centreId);
        if(doctorList.isEmpty()){
            throw new Exception("No Dr associated with this centre!");
        }

        List<String> drName=new ArrayList<>();
        for(Doctor d:doctorList){
            drName.add(d.getName());
        }
        return drName;

    }
}
