package com.example.liftingequipmentmain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
public class CustomerUpdateDTO extends BaseDTO {

    @NotBlank(message = "Field cannot be empty")
    private String customerName;
    private BigInteger tin;
    private Integer postCode;
    private String phoneNumber;
    private String address;
    private String email;

    public CustomerUpdateDTO(UUID id, String customerName, BigInteger tin, Integer postCode, String phoneNumber, String address, String email) {
        this.setId(id);
        this.customerName = customerName;
        this.tin = tin;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
}
