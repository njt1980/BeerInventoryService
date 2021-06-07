package com.nimel.mymicroservices.inventoryservice.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nimel.mymicroservices.inventoryservice.dto.InventoryDto;
import com.nimel.mymicroservices.inventoryservice.mappers.InventoryMapper;
import com.nimel.mymicroservices.inventoryservice.respository.InventoryRepository;

public class InventoryServiceImpl implements InventoryService {

	private InventoryRepository inventoryRepository;
	private InventoryMapper inventoryMapper;
	@Override
	public List<InventoryDto> listBeersById(UUID beerId) {
		return inventoryRepository
				.findAllByBeerId(beerId).stream().map(inventoryMapper::toInventoryDto)
				.collect(Collectors.toList());
	}

}
