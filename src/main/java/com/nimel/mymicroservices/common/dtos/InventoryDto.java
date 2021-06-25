package com.nimel.mymicroservices.common.dtos;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {
	private UUID id;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;
	private UUID beerId;
    private String upc;
    private Integer quantityOnHand = 0;

}
