package com.example.liftingequipmentmain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

// The Customer model. The customer entity is consisted of the following columns: id, tin (Tax Identification Number), customer name,
// post code, phone number, address and email. Each customer can have lifting equipment.

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CUSTOMERS")
public class Customer {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "TAXPAYER IDENTIFICATION NUMBER", unique = true)
    private BigInteger tin;

    @Column(name = "CUSTOMER NAME")
    private String customerName;

    @Column(name = "POSTCODE")
    private Integer postcode;

    @Column(name = "PHONE NUMBER")
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<LiftingEquipment> liftingEquipments;

}
