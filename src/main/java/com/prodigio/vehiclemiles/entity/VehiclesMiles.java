package com.prodigio.vehiclemiles.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class VehiclesMiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "county_fips")
    @NotEmpty(message = "county_fips is required")
    private Integer county_fips;

    @Column(name = "county_name")
    @NotEmpty(message = "county_name is required")
    private String county_name;

    @Column(name = "state_name")
    @NotEmpty(message = "state_name is required")
    private String state_name;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @NotEmpty(message = "date is required")
    private Date date;  //05-08-20 (MM-DD-YYYY), 4/30/20 (MM/DD/YYYY)

    @Column(name = "county_vmt")
    @NotEmpty(message = "county_vmt is required")
    private Integer county_vmt;

    @Column(name = "baseline_jan_vmt")
    @NotEmpty(message = "baseline_jan_vmt is required")
    private Integer baseline_jan_vmt;

    @Column(name = "percent_change_from_jan", scale = 2)
    @NotEmpty(message = "percent_change_from_jan is required")
    private Double percent_change_from_jan;

    @Column(name = "mean7_county_vmt", scale = 2)
    @NotEmpty(message = "mean7_county_vmtis required")
    private Double mean7_county_vmt;

    @Column(name = "mean7_percent_change_from_jan", scale = 2)
    @NotEmpty(message = "mean7_percent_change_from_jan required")
    private Double mean7_percent_change_from_jan;

    @Column(name = "date_at_low")
    @Temporal(TemporalType.DATE)
    @NotEmpty(message = "date_at_low is required")
    private Date date_at_low;  //05-08-20 (MM-DD-YYYY)

    @Column(name = "mean7_county_vmt_at_low", scale = 2)
    @NotEmpty(message = "mean7_county_vmt_at_low is required")
    private Double mean7_county_vmt_at_low;

    @Column(name = "percent_change_from_low", scale = 2)
    @NotEmpty(message = "percent_change_from_low is required")
    private Double percent_change_from_low;

}
