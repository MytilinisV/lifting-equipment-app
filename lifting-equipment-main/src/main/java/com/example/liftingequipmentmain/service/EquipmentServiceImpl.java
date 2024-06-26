package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.dto.LiftingEquipmentInsertDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentUpdateDTO;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.model.LiftingEquipment;
import com.example.liftingequipmentmain.repository.CustomerRepository;
import com.example.liftingequipmentmain.repository.EquipmentRepository;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.exceptions.SerialNumberAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EquipmentServiceImpl implements IEquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public LiftingEquipment insertEquipment(LiftingEquipmentInsertDTO dto) throws SerialNumberAlreadyExistsException, EntityNotFoundException {
        if (equipmentRepository.findLiftingEquipmentBySerialNumber(dto.getSerialNumber()) != null) {
            throw new SerialNumberAlreadyExistsException(dto.getSerialNumber());
        }
        Customer customer = customerRepository.findCustomerByTin(dto.getTin());
        if (customer == null) {
            throw new EntityNotFoundException(Customer.class);
        }
        LiftingEquipment liftingEquipment = convertInsertDtoToEquipment(dto);
        liftingEquipment.setCustomer(customer);
        return equipmentRepository.save(liftingEquipment);
    }

    @Transactional
    @Override
    public LiftingEquipment updateEquipment(String serialNumber, LiftingEquipmentUpdateDTO dto) throws EntityNotFoundException, SerialNumberAlreadyExistsException {
        LiftingEquipment equipment = equipmentRepository.findLiftingEquipmentBySerialNumber(serialNumber);

        if (equipment == null) {
            throw new EntityNotFoundException(LiftingEquipment.class);
        }

        if (!serialNumber.equals(dto.getSerialNumber()) && equipmentRepository.findLiftingEquipmentBySerialNumber(dto.getSerialNumber()) != null) {
            throw new SerialNumberAlreadyExistsException(dto.getSerialNumber());
        }

        return equipmentRepository.save(convertUpdateDtoToEquipment(equipment, dto));
    }

    @Transactional
    @Override
    public LiftingEquipment deleteEquipment(String serialNumber) throws EntityNotFoundException {
        LiftingEquipment liftingEquipment = equipmentRepository.findLiftingEquipmentBySerialNumber(serialNumber);
        if (liftingEquipment == null) {
            throw new EntityNotFoundException(LiftingEquipment.class);
        }

        equipmentRepository.deleteById(liftingEquipment.getId());
        return liftingEquipment;
    }

    @Override
    public List<LiftingEquipment> findAllEquipments() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<LiftingEquipment> getEquipmentsByManufacturer(String manufacturer) throws EntityNotFoundException {
        List<LiftingEquipment> liftingEquipment = equipmentRepository.findLiftingEquipmentByManufacturer(manufacturer);

        if (liftingEquipment.isEmpty()) {
            throw new EntityNotFoundException(LiftingEquipment.class);
        }
        return liftingEquipment;
    }

    @Override
    public LiftingEquipment getEquipmentBySerialNumber(String serialNumber) throws EntityNotFoundException {
        LiftingEquipment liftingEquipment = equipmentRepository.findLiftingEquipmentBySerialNumber(serialNumber);

        if (liftingEquipment == null) {
            throw new EntityNotFoundException(LiftingEquipment.class);
        }
        return liftingEquipment;
    }


    private LiftingEquipment convertInsertDtoToEquipment(LiftingEquipmentInsertDTO dto) {
        return LiftingEquipment.builder().serialNumber(dto.getSerialNumber()).manufacturer(dto.getManufacturer()).model(dto.getModel())
                .dateManufactured(dto.getDateManufactured()).dateAdded(new Date()).build();
    }

    private LiftingEquipment convertUpdateDtoToEquipment(LiftingEquipment oldLiftingEquipment, LiftingEquipmentUpdateDTO dto) {
        oldLiftingEquipment.setSerialNumber(dto.getSerialNumber());
        oldLiftingEquipment.setManufacturer(dto.getManufacturer());
        oldLiftingEquipment.setModel(dto.getModel());
        oldLiftingEquipment.setDateManufactured(dto.getDateManufactured());

        return oldLiftingEquipment;
    }

}
