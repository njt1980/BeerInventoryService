package com.nimel.mymicroservices.inventoryservice.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimel.mymicroservices.inventoryservice.dto.InventoryDto;
import com.nimel.mymicroservices.inventoryservice.services.InventoryService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/v1/beer/{beerId}")
@Slf4j
@RestController
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping("/inventory")
	public List<InventoryDto> listBeersById(@PathVariable UUID beerId){
		log.debug("Find list of beers for {}",beerId);
		return inventoryService.listBeersById(beerId);
	}

}
