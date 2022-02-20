package com.prodigio.vehiclemiles.service;

import com.prodigio.vehiclemiles.entity.VehiclesMiles;
import com.prodigio.vehiclemiles.repository.VehicleMilesRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VehicleMilesService {

    private static final Logger logger = LogManager.getLogger(VehicleMilesService.class);

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

    public Map<String, Object> uploadData(MultipartFile file) throws IOException {
        if (!file.getContentType().equals("text/csv"))
            throw new ValidationException("Solo se permiten archivos .csv");

        List<String> errors = new ArrayList<>();
        Integer lineCounter = 0;
        Integer columnCounter = 0;
        Integer inserted = 0;
        try
        {
            Scanner scanner = new Scanner(file.getInputStream());
            while (scanner.hasNextLine()) {
                lineCounter++;
                Integer currtenErrorSize = errors.size();

                //skip columnames
                if (lineCounter == 1){
                    scanner.nextLine();
                    continue;
                }

                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(",");
                VehiclesMiles vm = new VehiclesMiles();
                boolean skipline = false;

                while (lineScanner.hasNext()) {
                    columnCounter++;
                    String cell = lineScanner.next();
                    System.out.print(cell + "|");
                    if (skipline)
                        continue;
                    if (Strings.isNotBlank(cell)){
                        boolean correct_value = setValueVm(columnCounter, vm, cell, errors, lineCounter);
                        if (!correct_value){
                            skipline = true;
                            continue;
                        }
                    }else{
                        skipline = true;
                        String err = "Campo vacio. fila: " + lineCounter + ", columna_no: " + columnCounter;
                        logger.error(err);
                        errors.add(err);
                        continue;
                    }
                }
                columnCounter = 0;
                System.out.print("\n");

                if (currtenErrorSize < errors.size())
                    continue;

                //insertando el dato en bd
                try{
                    vehicleMilesRepository.save(vm);
                    inserted++;
                }
                catch (Exception ex){
                    String err = "Error insertando en bd la fila: " + lineCounter;
                    if (ex instanceof ConstraintViolationException){
                        List<String> list = new ArrayList<>();
                        for (ConstraintViolation<?> violation : ((ConstraintViolationException)ex).getConstraintViolations()) {
                            list.add(violation.getMessage());
                        }
                        err += " " + list.toString();
                    }

                    logger.error(err + "debug-error: " + ex.getMessage());
                    errors.add(err);
                }

                System.out.println("\n");
                lineScanner.close();
            }
            scanner.close();
            System.out.println("TOTAL LINES: " + lineCounter);

        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        Map<String, Object> response = new HashMap<>();
        String parcial = errors.size() > 0 ? " parcialmente" : " totalmente";
        response.put("message", "Datos importados" + parcial + ". Total insertados: " + inserted + " de " + (lineCounter - 1));
        response.put("errors", errors);

        return response;
    }

    private boolean setValueVm(Integer columnCounter, VehiclesMiles vm, String cell, List<String> errors, Integer lineCounter) {
        //column county_fips
        if (columnCounter == 1){
            try{
                vm.setCounty_fips(Integer.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'county_fips', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Integer";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 2){
            try{
                vm.setCounty_name(cell);
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'county_name', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 3){
            try{
                vm.setState_name(cell);
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'state_name', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 4){
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = formatter.parse(cell);
                vm.setDate(date);
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'date', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof ParseException)
                    err = err + ". Formato incorrecto. Corregir a dd-MM-yyyy";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 5){
            try{
                vm.setCounty_vmt(Integer.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'county_vmt', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Integer";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 6){
            try{
                vm.setBaseline_jan_vmt(Integer.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'baseline_jan_vmt', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Integer";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 7){
            try{
                vm.setPercent_change_from_jan(Double.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'percent_change_from_jan', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Double";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 8){
            try{
                vm.setMean7_county_vmt(Double.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'mean7_county_vmt', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Double";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 9){
            try{
                vm.setMean7_percent_change_from_jan(Double.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'mean7_percent_change_from_jan', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Double";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 10){
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = formatter.parse(cell);
                vm.setDate_at_low(date);
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'date_at_low', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof ParseException)
                    err = err + ". Formato incorrecto. Corregir a dd-MM-yyyy";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 11){
            try{
                vm.setMean7_county_vmt_at_low(Double.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'mean7_county_vmt_at_low', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Double";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }
        else if (columnCounter == 12){
            try{
                vm.setPercent_change_from_low(Double.valueOf(cell));
                return true;
            }catch (Exception ex){
                String err = "Error asignando 'percent_change_from_low', fila: " + lineCounter + ", columna_no: " + columnCounter + ", columna_valor: " + cell;
                if (ex instanceof NumberFormatException)
                    err = err + ". No se pudo convertir a Double";
                logger.error(err);
                errors.add(err);
                return false;
            }
        }else{
            return false;
        }
    }

}
