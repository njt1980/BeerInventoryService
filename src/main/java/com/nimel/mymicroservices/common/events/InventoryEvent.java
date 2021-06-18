package com.nimel.mymicroservices.common.events;

import com.nimel.mymicroservices.inventoryservice.dto.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class InventoryEvent extends BeerEvent{
	
	public InventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
