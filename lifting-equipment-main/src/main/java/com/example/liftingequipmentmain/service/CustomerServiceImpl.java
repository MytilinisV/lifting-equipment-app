package com.example.liftingequipmentmain.service;

import com.example.liftingequipmentmain.dto.CustomerInsertDTO;
import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.dto.CustomerUpdateDTO;
import com.example.liftingequipmentmain.mapper.ICustomerMapper;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.repository.CustomerRepository;
import com.example.liftingequipmentmain.exceptions.CustomerAlreadyExistsException;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final ICustomerMapper customerMapper;

    @Transactional
    @Override
    public Customer insertCustomer(CustomerInsertDTO dto) throws CustomerAlreadyExistsException {
        if (customerRepository.findCustomerByTin(dto.getTin()) != null) {
            throw new CustomerAlreadyExistsException(dto.getTin());
        }
        return customerRepository.save(convertInsertDtoToCustomer(dto));
    }

    @Transactional
    @Override
    public Customer updateCustomer(BigInteger tin, CustomerUpdateDTO dto) throws EntityNotFoundException, CustomerAlreadyExistsException {

        Customer customer = customerRepository.findCustomerByTin(tin);

        if (customer == null) {
            throw new EntityNotFoundException(Customer.class);
        }

        if (!tin.equals(dto.getTin()) && customerRepository.findCustomerByTin(dto.getTin()) != null) {
            throw new CustomerAlreadyExistsException(dto.getTin());
        }

        return customerRepository.save(convertUpdateDtoToCustomer(customer, dto));
    }

    @Transactional
    @Override
    public CustomerReadOnlyDTO deleteCustomer(BigInteger tin) throws EntityNotFoundException {
        Customer customer = customerRepository.findCustomerByTin(tin);
        CustomerReadOnlyDTO customerDTO = customerMapper.convertToReadOnlyDto(customer);

        if (customer == null) {
            throw new EntityNotFoundException(Customer.class);
        }
        customerRepository.deleteById(customer.getId());
        return customerDTO;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByCustomerName(String customerName) throws EntityNotFoundException {
        List<Customer> customers = customerRepository.findCustomerByCustomerName(customerName);
        if (customers.isEmpty()) {
            throw new EntityNotFoundException(Customer.class);
        }
        return customers;
    }

    @Override
    public Customer findCustomerByTIN(BigInteger tin) throws EntityNotFoundException {
        Customer customers = customerRepository.findCustomerByTin(tin);
        if (customers == null) {
            throw new EntityNotFoundException(Customer.class);
        }
        return customers;
    }

    @Override
    public List<Customer> findCustomerByPostCode(Integer postcode) throws EntityNotFoundException {
        List<Customer> customers = customerRepository.findCustomerByPostcode(postcode);
        if (customers.isEmpty()) {
            throw new EntityNotFoundException(Customer.class);
        }
        return customers;
    }

    @Override
    public Customer getCustomerById(UUID id) throws EntityNotFoundException {
        return null;
    }


    private Customer convertInsertDtoToCustomer(CustomerInsertDTO dto) {
        return Customer.builder().tin(dto.getTin()).customerName(dto.getCustomerName()).address(dto.getAddress()).email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber()).postcode(dto.getPostCode()).build();
    }

    private Customer convertUpdateDtoToCustomer(Customer oldCustomer, CustomerUpdateDTO dto) {
        oldCustomer.setCustomerName(dto.getCustomerName());
        oldCustomer.setAddress(dto.getAddress());
        oldCustomer.setId(dto.getId());
        oldCustomer.setPostcode(dto.getPostCode());
        oldCustomer.setTin(dto.getTin());
        oldCustomer.setPhoneNumber(dto.getPhoneNumber());
        oldCustomer.setEmail(dto.getEmail());

        return oldCustomer;
    }
}
