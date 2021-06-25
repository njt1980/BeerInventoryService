package com.nimel.mymicroservices.inventoryservice.listeners;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import com.nimel.mymicroservices.common.dtos.AllocateOrderRequest;
import com.nimel.mymicroservices.common.events.AllocateOrderResult;
import com.nimel.mymicroservices.inventoryservice.config.JmsConfig;
import com.nimel.mymicroservices.inventoryservice.services.AllocationService;

public class AllocationRequestListener {
	
	private AllocationService allocationService;
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
	public void listenForAllocationRequests(AllocateOrderRequest request) {
		
		AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
		builder.beerOrderDto(request.getBeerOrderDto());
		
		try {
			Boolean allocationResult = allocationService.allocateOrder(request.getBeerOrderDto());
			if(allocationResult) {
				builder.pendingInventory(false);
			}else {
				builder.pendingInventory(true);
			}
		}catch(Exception e) {
			
			System.out.println("Allocation error");
			builder.allocationError(true);
			
		}
		
		jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,builder.build());
	}

}
