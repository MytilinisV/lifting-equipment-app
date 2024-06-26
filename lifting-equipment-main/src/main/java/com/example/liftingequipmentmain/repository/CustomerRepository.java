package com.example.liftingequipmentmain.repository;

import com.example.liftingequipmentmain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findCustomerByCustomerName(String customerName);
    Customer findCustomerByTin(BigInteger tin);
    List<Customer> findCustomerByPostcode(Integer postcode);

}
