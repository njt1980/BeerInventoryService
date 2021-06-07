package com.nimel.mymicroservices.inventoryservice.services;

import java.util.List;
import java.util.UUID;

import com.nimel.mymicroservices.inventoryservice.dto.InventoryDto;

public interface InventoryService {
	
	List<InventoryDto> listBeersById(UUID beerId);

}
