package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.dto.CustomerInsertDTO;
import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.dto.CustomerUpdateDTO;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.exceptions.CustomerAlreadyExistsException;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;

import java.math.BigInteger;
import java.util.UUID;
import java.util.List;

public interface ICustomerService {
    Customer insertCustomer(CustomerInsertDTO dto) throws CustomerAlreadyExistsException;
    Customer updateCustomer(BigInteger tin, CustomerUpdateDTO dto) throws EntityNotFoundException, CustomerAlreadyExistsException;
    CustomerReadOnlyDTO deleteCustomer(BigInteger tin) throws EntityNotFoundException;
    List<Customer> findAllCustomers();
    List<Customer> findCustomerByCustomerName(String customerName) throws EntityNotFoundException;
    Customer findCustomerByTIN(BigInteger tin) throws EntityNotFoundException;
    List<Customer> findCustomerByPostCode(Integer postcode) throws EntityNotFoundException;
    Customer getCustomerById(UUID id) throws EntityNotFoundException;
}
