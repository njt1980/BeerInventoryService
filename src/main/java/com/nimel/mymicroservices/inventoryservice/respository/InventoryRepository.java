package com.nimel.mymicroservices.inventoryservice.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimel.mymicroservices.inventoryservice.Entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, UUID>{
	
	List<Inventory> findAllByBeerId(UUID beerId);
	

}
