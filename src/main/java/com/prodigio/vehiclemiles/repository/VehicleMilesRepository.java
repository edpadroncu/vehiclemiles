package com.prodigio.vehiclemiles.repository;

import com.prodigio.vehiclemiles.entity.VehiclesMiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleMilesRepository extends JpaRepository<VehiclesMiles, Long> {

}
