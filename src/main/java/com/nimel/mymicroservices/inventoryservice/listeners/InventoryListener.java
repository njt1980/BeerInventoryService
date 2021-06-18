package com.nimel.mymicroservices.inventoryservice.listeners;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.nimel.mymicroservices.inventoryservice.Entity.Inventory;
import com.nimel.mymicroservices.inventoryservice.dto.BeerDto;
//import com.nimel.mymicroservices.inventoryservice.events.InventoryEvent;
import com.nimel.mymicroservices.common.events.*;
import com.nimel.mymicroservices.inventoryservice.respository.InventoryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class InventoryListener {
	
	private InventoryRepository inventoryRepository;
	
//	@Transactional
	@JmsListener(destination = "inventory-request")
	public void listenInventory(InventoryEvent inventoryEvent) {
//		System.out.println(inventoryEvent.getBeerDto().getId());
		System.out.println("Updating inventory for " + inventoryEvent.getBeerDto().getId());
//		System.out.println(inventoryRepository.findAll());
		inventoryRepository.save(Inventory.builder()
				.beerId(inventoryEvent.getBeerDto().getId())
				.upc(inventoryEvent.getBeerDto().getUpc())
				.quantityOnHand(inventoryEvent.getBeerDto().getQuantityOnHand())
				.build());
		System.out.println("Inventory Updated!" + inventoryEvent.getBeerDto().getId());
		
//		System.out.println("Updating Inventory");
//		BeerDto beerDto = inventoryEvent.getBeerDto();
//		System.out.println(beerDto);
//		Inventory inventory = inventoryRepository.findByUpc(beerDto.getUpc());
//		Optional<Inventory> inventory1 = inventoryRepository.findById(inventory.getId());
//		System.out.println(inventory);
//		System.out.println(beerDto.getMinOnHand());
//		Inventory inventory2 = inventory1.get();
//		inventory2.setQuantityOnHand(beerDto.getQuantityOnHand());
//		System.out.println(inventory.getQuantityOnHand());
//		inventoryRepository.save(inventory2);
		
	}

}
