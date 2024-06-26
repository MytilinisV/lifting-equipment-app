package com.example.liftingequipmentmain.mapper;

import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.model.Customer;

public interface ICustomerMapper {
    CustomerReadOnlyDTO convertToReadOnlyDto(Customer customer);
}
