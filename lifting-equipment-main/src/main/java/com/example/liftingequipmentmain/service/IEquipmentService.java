package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.dto.LiftingEquipmentInsertDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentUpdateDTO;
import com.example.liftingequipmentmain.model.LiftingEquipment;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.exceptions.SerialNumberAlreadyExistsException;

import java.util.List;

public interface IEquipmentService {
    LiftingEquipment insertEquipment(LiftingEquipmentInsertDTO dto) throws SerialNumberAlreadyExistsException, EntityNotFoundException;
    LiftingEquipment updateEquipment(String serialNumber, LiftingEquipmentUpdateDTO dto) throws EntityNotFoundException, SerialNumberAlreadyExistsException;
    LiftingEquipment deleteEquipment(String serialNumber) throws EntityNotFoundException;
    List<LiftingEquipment> findAllEquipments();
    LiftingEquipment getEquipmentBySerialNumber(String serialNumber) throws EntityNotFoundException;
    List<LiftingEquipment> getEquipmentsByManufacturer(String manufacturer) throws EntityNotFoundException;
}
