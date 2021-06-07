package com.nimel.mymicroservices.inventoryservice.mappers;

import org.mapstruct.Mapper;

import com.nimel.mymicroservices.inventoryservice.Entity.Inventory;
import com.nimel.mymicroservices.inventoryservice.dto.InventoryDto;

@Mapper(uses = {DateMapper.class})
public interface InventoryMapper {
	
	InventoryDto toInventoryDto(Inventory inventory);
	Inventory toInventory(InventoryDto inventoryDto);

}
