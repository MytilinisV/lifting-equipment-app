package com.example.liftingequipmentmain.mapper;

import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.dto.SimplifiedLiftingEquipmentDTO;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.model.LiftingEquipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerMapperImpl implements ICustomerMapper {

    @Override
    public CustomerReadOnlyDTO convertToReadOnlyDto(Customer customer) {
        CustomerReadOnlyDTO customerDTO = new CustomerReadOnlyDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setTin(customer.getTin());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPostCode(customer.getPostcode());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setEquipments(Optional.ofNullable(customer.getLiftingEquipments()).orElse(Collections.emptyList()).stream()
                .map(equipment -> convertToSimplifiedDto(equipment))
                .collect(Collectors.toList()));
        return customerDTO;
    }

    private SimplifiedLiftingEquipmentDTO convertToSimplifiedDto(LiftingEquipment liftingEquipment) {
        SimplifiedLiftingEquipmentDTO liftingEquipmentDTO = new SimplifiedLiftingEquipmentDTO();
        liftingEquipmentDTO.setId(liftingEquipment.getId());
        liftingEquipmentDTO.setSerialNumber(liftingEquipment.getSerialNumber());
        liftingEquipmentDTO.setManufacturer(liftingEquipment.getManufacturer());
        liftingEquipmentDTO.setModel(liftingEquipment.getModel());
        liftingEquipmentDTO.setDateManufactured(liftingEquipment.getDateManufactured());

        return liftingEquipmentDTO;
    }
}
