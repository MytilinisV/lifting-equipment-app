package com.example.liftingequipmentmain.rest;

import com.example.liftingequipmentmain.dto.CustomerReadOnlyDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentInsertDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentReadOnlyDTO;
import com.example.liftingequipmentmain.dto.LiftingEquipmentUpdateDTO;
import com.example.liftingequipmentmain.exceptions.EntityNotFoundException;
import com.example.liftingequipmentmain.exceptions.SerialNumberAlreadyExistsException;
import com.example.liftingequipmentmain.mapper.IEquipmentMapper;
import com.example.liftingequipmentmain.model.LiftingEquipment;
import com.example.liftingequipmentmain.service.IEquipmentService;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LiftingEquipmentRestController {

    private final IEquipmentService equipmentService;
    private final IEquipmentMapper equipmentMapper;

    @Operation(summary = "Get all Equipments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipments found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content)})
    @RequestMapping(value = "/equipments", method = RequestMethod.GET)
    public ResponseEntity<List<LiftingEquipmentReadOnlyDTO>> getAllEquipments() {
        List<LiftingEquipment> equipments;
        equipments = equipmentService.findAllEquipments();
        List<LiftingEquipmentReadOnlyDTO> equipmentsDTO = new ArrayList<>();
        for (LiftingEquipment equipment : equipments) {
            equipmentsDTO.add(equipmentMapper.convertToReadOnlyDto(equipment));
        }
        return new ResponseEntity<>(equipmentsDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get Equipments by Serial Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lifting Equipment Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Serial Number",
                    content = @Content)})
    @RequestMapping(value = "/equipments/serialNumber/{serialNumber}", method = RequestMethod.GET)
    public ResponseEntity<List<LiftingEquipmentReadOnlyDTO>> getEquipmentsBySerialNumber(@PathVariable String serialNumber) {
        LiftingEquipment liftingEquipment;
        try {
            liftingEquipment = equipmentService.getEquipmentBySerialNumber(serialNumber);
            LiftingEquipmentReadOnlyDTO liftingEquipmentDTO = equipmentMapper.convertToReadOnlyDto(liftingEquipment);
            return new ResponseEntity<>(List.of(liftingEquipmentDTO), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get Equipments by Manufacturer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lifting Equipment Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Manufacturer name",
                    content = @Content)})
    @RequestMapping(value = "/equipments/manufacturer/{manufacturer}", method = RequestMethod.GET)
    public ResponseEntity<List<LiftingEquipmentReadOnlyDTO>> getEquipmentsByManufacturer(@PathVariable String manufacturer) {
        List<LiftingEquipment> liftingEquipments;
        try {
            liftingEquipments = equipmentService.getEquipmentsByManufacturer(manufacturer);
            List<LiftingEquipmentReadOnlyDTO> liftingEquipmentsDTO = new ArrayList<>();
            for (LiftingEquipment liftingEquipment : liftingEquipments) {
                liftingEquipmentsDTO.add(equipmentMapper.convertToReadOnlyDto(liftingEquipment));
            }
            return new ResponseEntity<>(liftingEquipmentsDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new Equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Equipment created!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Service Unavailable",
                    content = @Content)})
    @RequestMapping(value = "/equipments", method = RequestMethod.POST)
    public ResponseEntity<LiftingEquipmentReadOnlyDTO> addEquipment(@Valid @RequestBody LiftingEquipmentInsertDTO dto) {
        LiftingEquipment liftingEquipment;
        try {
            liftingEquipment = equipmentService.insertEquipment(dto);
            LiftingEquipmentReadOnlyDTO liftingEquipmentDTO = equipmentMapper.convertToReadOnlyDto(liftingEquipment);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{serialNumber}")
                    .buildAndExpand(liftingEquipmentDTO.getSerialNumber())
                    .toUri();
            return ResponseEntity.created(location).body(liftingEquipmentDTO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment Deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Equipment not found",
                    content = @Content)})
    @RequestMapping(value = "/equipments/{serialNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<LiftingEquipmentReadOnlyDTO> deleteEquipment(@PathVariable("serialNumber") String serialNumber) {
        try {
            LiftingEquipment liftingEquipment = equipmentService.deleteEquipment(serialNumber);
            LiftingEquipmentReadOnlyDTO liftingEquipmentDTO = equipmentMapper.convertToReadOnlyDto(liftingEquipment);
            return new ResponseEntity<>(liftingEquipmentDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update Equipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LiftingEquipmentReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Equipment not found",
                    content = @Content)})
    @RequestMapping(value = "/equipments/{serialNumber}", method = RequestMethod.PUT)
    public ResponseEntity<LiftingEquipmentReadOnlyDTO> updateEquipment(@PathVariable("serialNumber") String serialNumber,
                                                                       @Valid @RequestBody LiftingEquipmentUpdateDTO dto) throws EntityNotFoundException, SerialNumberAlreadyExistsException {
        LiftingEquipment liftingEquipment = equipmentService.updateEquipment(serialNumber, dto);
        LiftingEquipmentReadOnlyDTO liftingEquipmentDTO = equipmentMapper.convertToReadOnlyDto(liftingEquipment);
        return new ResponseEntity<>(liftingEquipmentDTO, HttpStatus.OK);
    }
}
