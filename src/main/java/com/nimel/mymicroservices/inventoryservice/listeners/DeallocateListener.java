package com.nimel.mymicroservices.inventoryservice.listeners;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.nimel.mymicroservices.common.dtos.DeallocateOrderRequest;
import com.nimel.mymicroservices.inventoryservice.config.JmsConfig;
import com.nimel.mymicroservices.inventoryservice.services.AllocationService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class DeallocateListener {
	
	private AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest request){
        allocationService.deallocateOrder(request.getBeerOrderDto());
    }
	
	

}
