package com.prodigio.vehiclemiles.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.prodigio.vehiclemiles.common.Helper;
import com.prodigio.vehiclemiles.entity.VehiclesMiles;
import com.prodigio.vehiclemiles.service.VehicleMilesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
public class VehicleMilesController {

    private static final Logger logger = LogManager.getLogger(VehicleMilesController.class);

    @Autowired
    private VehicleMilesService vehicleMilesService;

    @Autowired
    private Helper helper;

    @GetMapping("/vehicle-miles")
    public ResponseEntity<?> getAll(){
        return helper.httpResponse(false, vehicleMilesService.getAllVehicles(), HttpStatus.OK);
    }

    @PostMapping("/vehicle-miles")
    public ResponseEntity<?> add(@Valid @RequestBody VehiclesMiles vehiclesMiles) {
        return helper.httpResponse(true, vehicleMilesService.addVehicle(vehiclesMiles), HttpStatus.CREATED);
    }

    @PutMapping("/vehicle-miles/{id}")
    public ResponseEntity<?> edit(@RequestBody VehiclesMiles vehiclesMiles, @PathVariable Long id) {
        return helper.httpResponse(true, vehicleMilesService.editVehicleById(vehiclesMiles, id), HttpStatus.OK);
    }

    @DeleteMapping("/vehicle-miles/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return helper.httpResponse(true, vehicleMilesService.deleteVehiclesMilesById(id), HttpStatus.OK);
    }

    @GetMapping("/vehicle-miles/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return helper.httpResponse(true, vehicleMilesService.getVehiclesMilesById(id), HttpStatus.OK);
    }

    

}
