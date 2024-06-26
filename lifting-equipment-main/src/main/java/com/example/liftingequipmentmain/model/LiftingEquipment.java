package com.example.liftingequipmentmain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// The lifting equipment entity. It contains the following columns: id, customer, serial number
// model, manufacturer, date manufactured,date added in the database and the customer which has
// the lifting equipment.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "LIFTING_EQUIPMENT")
public class LiftingEquipment {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "SERIAL_NUMBER", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "MANUFACTURER")
    private String manufacturer;

    @Column(name = "DATE_MANUFACTURED")
    private Integer dateManufactured;

    @Column(name = "DATE_ADDED")
    private Date dateAdded;

}
