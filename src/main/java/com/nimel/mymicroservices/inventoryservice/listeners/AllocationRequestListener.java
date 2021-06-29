package com.nimel.mymicroservices.inventoryservice.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.common.dtos.AllocateOrderRequest;
import com.nimel.mymicroservices.common.dtos.AllocateOrderResult;
import com.nimel.mymicroservices.inventoryservice.config.JmsConfig;
import com.nimel.mymicroservices.inventoryservice.services.AllocationService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class AllocationRequestListener {
	
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
	public void listenForAllocationRequests(AllocateOrderRequest request) {
		
		System.out.println("In Inventory service - Listening to ALLOCATE_ORDER_QUEUE" + request.getBeerOrderDto());
		System.out.println("Request id in Allocate Order queue :" + request.getBeerOrderDto().getId());
		AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
		builder.beerOrderDto(request.getBeerOrderDto());
		builder.allocationError(false);
		builder.pendingInventory(false);
		
		try {
			System.out.println("Going to allocateOrder");
			Boolean allocationResult = allocationService.allocateOrder(request.getBeerOrderDto());
			System.out.println("Allocation result for " + request.getBeerOrderDto().getId() + "is " + allocationResult);
			if(allocationResult) {
				System.out.println("In Inventory service - Inventory Not Pending");
				builder.pendingInventory(false);
			}else {
				System.out.println("In Inventory service - Inventory Pending");
				builder.pendingInventory(true);
			}
		}catch(Exception e) {
			
			System.out.println("Allocation error" + e);
			builder.allocationError(true);
			
		}
		
		jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,builder.build());
	}

}
