package com.nimel.mymicroservices.inventoryservice.services;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.stereotype.Service;

import com.nimel.mymicroservices.common.dtos.BeerOrderDto;
import com.nimel.mymicroservices.common.dtos.BeerOrderLineDto;
import com.nimel.mymicroservices.inventoryservice.Entity.Inventory;
import com.nimel.mymicroservices.inventoryservice.respository.InventoryRepository;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class AllocationServiceImpl implements AllocationService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	@Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        log.debug("Allocating OrderId: " + beerOrderDto.getId());
        System.out.println("Inventory Repo: is " + inventoryRepository);
        System.out.println("OrderDto is : " + beerOrderDto);
        System.out.println("Allocating OrderId: " + beerOrderDto.getId());
        AtomicInteger totalOrdered = new AtomicInteger();
        AtomicInteger totalAllocated = new AtomicInteger();
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLine -> {
            if ((((beerOrderLine.getOrderQuantity() != null ? beerOrderLine.getOrderQuantity() : 0)
                    - (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0)) > 0)) {
            	System.out.println("Trying to allocate for beerorderlines!");
            	System.out.println("BeerOrderLine is " + beerOrderLine);
                allocateBeerOrderLine(beerOrderLine);
            }
            totalOrdered.set(totalOrdered.get() + beerOrderLine.getOrderQuantity());
            totalAllocated.set(totalAllocated.get() + (beerOrderLine.getQuantityAllocated() != null ? beerOrderLine.getQuantityAllocated() : 0));
        });

        log.debug("Total Ordered: " + totalOrdered.get() + " Total Allocated: " + totalAllocated.get());
        System.out.println("In allocate order for beer id " + beerOrderDto.getId() + "Total ordered " + totalOrdered.get());
        System.out.println("In allocate order for beer id " + beerOrderDto.getId() + "Total Allocated " + totalAllocated.get());
        return totalOrdered.get() == totalAllocated.get();
    }

    private void allocateBeerOrderLine(BeerOrderLineDto beerOrderLine) {
    	
    	System.out.println("Beer Line Upc : for finding Inventory" + beerOrderLine.getUpc());
//    	System.out.println("Inventory for Beer Line Upc : " + inventoryRepository.findByUpc(beerOrderLine.getUpc()));
    	System.out.println(inventoryRepository.findAllByUpc(beerOrderLine.getUpc()));
    	List<Inventory> beerInventoryList = inventoryRepository.findAllByUpc(beerOrderLine.getUpc());
    	System.out.println("Beer Inventory List for upc " + beerOrderLine.getUpc() + " is " + beerInventoryList);

        beerInventoryList.forEach(beerInventory -> {
            int inventory = (beerInventory.getQuantityOnHand() == null) ? 0 : beerInventory.getQuantityOnHand();
            System.out.println("**Quantiy on Hand for upc " + beerOrderLine.getUpc() + "is" + inventory);
            int orderQty = (beerOrderLine.getOrderQuantity() == null) ? 0 : beerOrderLine.getOrderQuantity();
            System.out.println("**Quantiy on Hand for upc " + beerOrderLine.getUpc() + "is" + orderQty);
            int allocatedQty = (beerOrderLine.getQuantityAllocated() == null) ? 0 : beerOrderLine.getQuantityAllocated();
            System.out.println("**Alloc Quantiy for upc " + beerOrderLine.getUpc() + "is" + allocatedQty);
            int qtyToAllocate = orderQty - allocatedQty;

            if (inventory >= qtyToAllocate) { // full allocation
                inventory = inventory - qtyToAllocate;
                beerOrderLine.setQuantityAllocated(orderQty);
                beerInventory.setQuantityOnHand(inventory);
                inventoryRepository.save(beerInventory);
            } else if (inventory > 0) { //partial allocation
                beerOrderLine.setQuantityAllocated(allocatedQty + inventory);
                beerInventory.setQuantityOnHand(0);
            }

            if (beerInventory.getQuantityOnHand() == 0) {
                inventoryRepository.delete(beerInventory);
            }
        });

    }

    @Override
    public void deallocateOrder(BeerOrderDto beerOrderDto) {
        beerOrderDto.getBeerOrderLines().forEach(beerOrderLineDto -> {
            Inventory inventory = Inventory.builder()
                    .beerId(beerOrderLineDto.getBeerId())
                    .upc(beerOrderLineDto.getUpc())
                    .quantityOnHand(beerOrderLineDto.getQuantityAllocated())
                    .build();

            Inventory savedInventory = inventoryRepository.save(inventory);

            log.debug("Saved Inventory for beer upc: " + savedInventory.getUpc() + " inventory id: " + savedInventory.getId());
        });
    }

}
