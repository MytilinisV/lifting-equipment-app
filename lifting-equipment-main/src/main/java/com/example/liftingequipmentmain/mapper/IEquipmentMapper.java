package com.example.liftingequipmentmain.mapper;

import com.example.liftingequipmentmain.dto.LiftingEquipmentReadOnlyDTO;
import com.example.liftingequipmentmain.model.LiftingEquipment;


public interface IEquipmentMapper {
    LiftingEquipmentReadOnlyDTO convertToReadOnlyDto(LiftingEquipment liftingEquipment);
}
