package com.example.liftingequipmentmain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimplifiedLiftingEquipmentDTO extends BaseDTO {
    private String serialNumber;
    private String model;
    private String manufacturer;
    private Integer dateManufactured;
}
