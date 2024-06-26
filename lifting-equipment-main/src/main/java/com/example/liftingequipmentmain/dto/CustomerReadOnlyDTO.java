package com.example.liftingequipmentmain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReadOnlyDTO extends BaseDTO {
    private String customerName;
    private BigInteger tin;
    private Integer postCode;
    private String phoneNumber;
    private String address;
    private String email;
    private List<SimplifiedLiftingEquipmentDTO> equipments = new ArrayList<>();
}
