package com.prodigio.vehiclemiles.service;

import com.prodigio.vehiclemiles.entity.VehiclesMiles;
import com.prodigio.vehiclemiles.repository.VehicleMilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleMilesService {

    @Autowired
    private VehicleMilesRepository vehicleMilesRepository;

    public VehiclesMiles addVehicle(VehiclesMiles vehiclesMiles){
        return vehicleMilesRepository.save(vehiclesMiles);
    }

    public List<VehiclesMiles> getAllVehicles(){
        return vehicleMilesRepository.findAll();
    }

    public VehiclesMiles editVehicleById(VehiclesMiles vehiclesMiles, Long id){
        VehiclesMiles vm = getVehiclesMilesById(id);
        vm.setCounty_fips(vehiclesMiles.getCounty_fips());
        vm.setCounty_name(vehiclesMiles.getCounty_name());
        vm.setState_name(vehiclesMiles.getState_name());
        vm.setDate(vehiclesMiles.getDate());
        vm.setCounty_vmt(vehiclesMiles.getCounty_vmt());
        vm.setBaseline_jan_vmt(vehiclesMiles.getBaseline_jan_vmt());
        vm.setPercent_change_from_jan(vehiclesMiles.getPercent_change_from_jan());
        vm.setMean7_county_vmt(vehiclesMiles.getMean7_county_vmt());
        vm.setMean7_percent_change_from_jan(vehiclesMiles.getMean7_percent_change_from_jan());
        vm.setDate_at_low(vehiclesMiles.getDate_at_low());
        vm.setMean7_county_vmt_at_low(vehiclesMiles.getMean7_county_vmt_at_low());
        vm.setPercent_change_from_low(vehiclesMiles.getPercent_change_from_low());
        return vehicleMilesRepository.save(vm);
    }

    public String deleteVehiclesMilesById(Long id){
        try {
            vehicleMilesRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex){
            throw new ValidationException("No se encontró el id " + id);
        }
        return "Eliminado";
    }

    public VehiclesMiles getVehiclesMilesById(Long id){
        Optional<VehiclesMiles> medication = vehicleMilesRepository.findById(id);
        if (!medication.isPresent())
            throw new ValidationException("No se encontró el id " + id);
        return medication.get();
    }



}
