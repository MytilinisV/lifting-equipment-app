package com.example.liftingequipmentmain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class LiftingEquipmentInsertDTO {

    @NotBlank(message = "Field cannot be empty")
    private String serialNumber;

    private String model;
    private String manufacturer;

    private Integer dateManufactured;
    private BigInteger tin;
}
