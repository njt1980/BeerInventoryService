package com.nimel.mymicroservices.common.events;

import com.nimel.mymicroservices.inventoryservice.dto.*;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BeerEvent implements Serializable{
	
	private BeerDto beerDto;

}
