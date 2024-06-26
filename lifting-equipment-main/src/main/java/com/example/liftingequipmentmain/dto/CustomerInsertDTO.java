package com.example.liftingequipmentmain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

import java.math.BigInteger;

@Getter
@Setter
public class CustomerInsertDTO {

    @NotBlank(message = "Field cannot be empty")
    private String customerName;

    @NotNull(message = "Tax Identification Number is a required field")
    private BigInteger tin;

    private Integer postCode;
    private String phoneNumber;
    private String address;

    @Email
    private String email;

    public CustomerInsertDTO(String customerName, BigInteger tin, Integer postCode, String phoneNumber, String address, String email) {
        this.customerName = customerName;
        this.tin = tin;
        this.postCode = postCode;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

}
