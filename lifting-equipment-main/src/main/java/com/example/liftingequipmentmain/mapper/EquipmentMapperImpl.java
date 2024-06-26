package com.example.liftingequipmentmain.mapper;

import com.example.liftingequipmentmain.dto.LiftingEquipmentReadOnlyDTO;
import com.example.liftingequipmentmain.dto.SimplifiedCustomerDTO;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.model.LiftingEquipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentMapperImpl implements IEquipmentMapper {

    @Override
    public LiftingEquipmentReadOnlyDTO convertToReadOnlyDto(LiftingEquipment liftingEquipment) {
        LiftingEquipmentReadOnlyDTO liftingEquipmentDTO = new LiftingEquipmentReadOnlyDTO();
        liftingEquipmentDTO.setId(liftingEquipment.getId());
        liftingEquipmentDTO.setSerialNumber(liftingEquipment.getSerialNumber());
        liftingEquipmentDTO.setManufacturer(liftingEquipment.getManufacturer());
        liftingEquipmentDTO.setModel(liftingEquipment.getModel());
        liftingEquipmentDTO.setDateManufactured(liftingEquipment.getDateManufactured());
        liftingEquipmentDTO.setCustomer(convertToSimplifiedDto(liftingEquipment.getCustomer()));

        return liftingEquipmentDTO;
    }

    private SimplifiedCustomerDTO convertToSimplifiedDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        SimplifiedCustomerDTO customerDTO = new SimplifiedCustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setTin(customer.getTin());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPostCode(customer.getPostcode());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        return customerDTO;
    }
}
