package com.nimel.mymicroservices.inventoryservice.Entity;

import java.util.UUID;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Inventory extends BaseEntity {
	private UUID beerId;
    private String upc;
    private Integer quantityOnHand = 0;
}
