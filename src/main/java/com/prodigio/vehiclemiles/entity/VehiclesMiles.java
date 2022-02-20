package com.prodigio.vehiclemiles.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class VehiclesMiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "county_fips")
    @NotNull(message = "county_fips es requerido")
    private Integer county_fips;

    @Column(name = "county_name")
    @NotEmpty(message = "county_name es requerido")
    private String county_name;

    @Column(name = "state_name")
    @NotEmpty(message = "state_name es requerido")
    private String state_name;

    @Column(name = "date")
    @JsonFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "date es requerido")
    private Date date;  //05-08-20 (DD-MM-YYYY)

    @Column(name = "county_vmt")
    @NotNull(message = "county_vmt es requerido")
    private Integer county_vmt;

    @Column(name = "baseline_jan_vmt")
    @NotNull(message = "baseline_jan_vmt es requerido")
    private Integer baseline_jan_vmt;

    @Column(name = "percent_change_from_jan", scale = 2)
    @NotNull(message = "percent_change_from_jan es requerido")
    private Double percent_change_from_jan;

    @Column(name = "mean7_county_vmt", scale = 2)
    @NotNull(message = "mean7_county_vmt es requerido")
    private Double mean7_county_vmt;

    @Column(name = "mean7_percent_change_from_jan", scale = 2)
    @NotNull(message = "mean7_percent_change_from_jan required")
    private Double mean7_percent_change_from_jan;

    @Column(name = "date_at_low")
    @JsonFormat(pattern="dd-MM-yyyy")
    @NotNull(message = "date_at_low es requerido")
    private Date date_at_low;  //05-08-20 (DD-MM-YYYY)

    @Column(name = "mean7_county_vmt_at_low", scale = 2)
    @NotNull(message = "mean7_county_vmt_at_low es requerido")
    private Double mean7_county_vmt_at_low;

    @Column(name = "percent_change_from_low", scale = 2)
    @NotNull(message = "percent_change_from_low es requerido")
    private Double percent_change_from_low;

}
