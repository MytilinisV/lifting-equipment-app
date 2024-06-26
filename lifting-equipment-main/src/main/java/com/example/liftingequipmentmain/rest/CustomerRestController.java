package com.example.liftingequipmentmain.rest;

import com.example.liftingequipmentmain.dto.CustomerInsertDTO;
import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.dto.CustomerUpdateDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentReadOnlyDTO;
import com.example.liftingequipmentmain.exceptions.CustomerAlreadyExistsException;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.mapper.ICustomerMapper;
import com.example.liftingequipmentmain.model.Customer;
import com.example.liftingequipmentmain.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerRestController {

    private final ICustomerService customerService;
    private final ICustomerMapper customerMapper;

    @Operation(summary = "Get all Customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerReadOnlyDTO>> getAllCustomers() {
        List<Customer> customers;
        customers = customerService.findAllCustomers();
        List<CustomerReadOnlyDTO> customersDTO = new ArrayList<>();
        for (Customer customer : customers) {
            customersDTO.add(customerMapper.convertToReadOnlyDto(customer));
        }
        return new ResponseEntity<>(customersDTO, HttpStatus.OK);
    }

    @Operation(summary = "Find a Customer by Tax Identification Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid TIN",
                    content = @Content)})
    @RequestMapping(value = "/customers/tin/{tin}", method = RequestMethod.GET)
    // tin is the Tax Identification Number
    public ResponseEntity<List<CustomerReadOnlyDTO>> getCustomersByTIN(@PathVariable BigInteger tin) {
        Customer customer;
        try {
            customer = customerService.findCustomerByTIN(tin);
            CustomerReadOnlyDTO customerDTO = customerMapper.convertToReadOnlyDto(customer);
            return new ResponseEntity<>(List.of(customerDTO), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Customers by Customer Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Customer name",
                    content = @Content)})
    @RequestMapping(value = "/customers/customerName/{customerName}", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerReadOnlyDTO>> getCustomersByCustomerName(@PathVariable String customerName) {
        List<Customer> customers;
        try {
            customers = customerService.findCustomerByCustomerName(customerName);
            List<CustomerReadOnlyDTO> customersDTO = new ArrayList<>();
            for (Customer customer : customers) {
                customersDTO.add(customerMapper.convertToReadOnlyDto(customer));
            }
            return new ResponseEntity<>(customersDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get Customers by Postal Code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Postal Code",
                    content = @Content)})
    @RequestMapping(value = "/customers/postCode/{postCode}", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerReadOnlyDTO>> getCustomersByCustomerPostalCode(@PathVariable Integer postCode) {
        List<Customer> customers;
        try {
            customers = customerService.findCustomerByPostCode(postCode);
            List<CustomerReadOnlyDTO> customersDTO = new ArrayList<>();
            for (Customer customer : customers) {
                customersDTO.add(customerMapper.convertToReadOnlyDto(customer));
            }
            return new ResponseEntity<>(customersDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Customer created!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Service Unavailable",
                    content = @Content)})
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<CustomerReadOnlyDTO> addCustomer(@Valid @RequestBody CustomerInsertDTO dto) {
        Customer customer;
        customer = customerService.insertCustomer(dto);
        CustomerReadOnlyDTO customerDTO = customerMapper.convertToReadOnlyDto(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{tin}")
                .buildAndExpand(customerDTO.getTin())
                .toUri();
        return ResponseEntity.created(location).body(customerDTO);
    }

    @Operation(summary = "Delete Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)})
    @RequestMapping(value = "/customers/{tin}", method = RequestMethod.DELETE)
    public ResponseEntity<CustomerReadOnlyDTO> deleteCustomer(@PathVariable("tin") BigInteger tin) {
        try {
            return new ResponseEntity<>(customerService.deleteCustomer(tin), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)})
    @RequestMapping(value = "/customers/{tin}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerReadOnlyDTO> updateCustomer(@PathVariable("tin") BigInteger tin,
                                                              @Valid @RequestBody CustomerUpdateDTO dto) throws EntityNotFoundException, CustomerAlreadyExistsException {
        Customer customer = customerService.updateCustomer(tin, dto);
        CustomerReadOnlyDTO customerDTO = customerMapper.convertToReadOnlyDto(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

}
