package com.ub.beerOrderService.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDto extends BaseItem {

    private UUID beerId;
    private String upc;
    private String beerName;
    private Integer orderQuantity = 0;

    @Builder
    public BeerOrderLineDto(UUID uuid, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID beerId, String upc, String beerName, Integer orderQuantity) {
        super(uuid, version, createdDate, lastModifiedDate);
        this.beerId = beerId;
        this.upc = upc;
        this.beerName = beerName;
        this.orderQuantity = orderQuantity;
    }
}
