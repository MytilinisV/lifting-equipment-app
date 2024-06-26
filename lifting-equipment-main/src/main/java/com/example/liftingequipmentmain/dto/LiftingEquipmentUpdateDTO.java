package com.example.liftingequipmentmain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LiftingEquipmentUpdateDTO extends BaseDTO{

    @NotBlank(message = "Field cannot be empty")
    private String serialNumber;

    private String model;
    private String manufacturer;
    private Integer dateManufactured;


    public LiftingEquipmentUpdateDTO(UUID id, String serialNumber, String model, String manufacturer, Integer dateManufactured) {
        this.setId(id);
        this.serialNumber = serialNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.dateManufactured = dateManufactured;
    }

}
