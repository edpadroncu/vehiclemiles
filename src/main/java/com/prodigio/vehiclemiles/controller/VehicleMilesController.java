package com.prodigio.vehiclemiles.controller;

import com.prodigio.vehiclemiles.common.Helper;
import com.prodigio.vehiclemiles.entity.VehiclesMiles;
import com.prodigio.vehiclemiles.service.VehicleMilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController()
public class VehicleMilesController {

    @Autowired
    private VehicleMilesService vehicleMilesService;

    @Autowired
    private Helper helper;

    @GetMapping("/vehicle-miles")
    public ResponseEntity<?> getAll(){
        return helper.httpResponse(true, vehicleMilesService.getAllVehicles(), HttpStatus.OK);
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

    @PostMapping("/vehicle-miles/upload-data")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return helper.httpResponse(true, vehicleMilesService.uploadData(file), HttpStatus.OK);
    }

}
