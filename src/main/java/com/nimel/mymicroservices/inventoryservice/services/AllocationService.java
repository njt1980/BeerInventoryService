package com.nimel.mymicroservices.inventoryservice.services;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;

public interface AllocationService {
	
	Boolean allocateOrder(BeerOrderDto beerOrderDto);
	
	void deallocateOrder(BeerOrderDto beerOrderDto);

}
