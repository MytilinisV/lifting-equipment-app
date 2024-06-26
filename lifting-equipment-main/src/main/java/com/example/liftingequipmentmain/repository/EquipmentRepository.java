package com.example.liftingequipmentmain.repository;

import com.example.liftingequipmentmain.model.LiftingEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<LiftingEquipment, UUID> {
//        List<LiftingEquipment> findBySerialNumberStartingWithAndManufacturerStartingWithAndModelStartingWith(String serialNumber, String manufacturer, String model);

    LiftingEquipment findLiftingEquipmentBySerialNumber(String serialNumber);

    List<LiftingEquipment> findLiftingEquipmentByManufacturer(String manufacturer);
}
