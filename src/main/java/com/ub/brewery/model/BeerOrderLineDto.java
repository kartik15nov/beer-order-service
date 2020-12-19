package com.ub.brewery.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderLineDto extends BaseItem {

    private UUID beerId;
    private String upc;
    private String beerName;
    private String beerStyle;
    private Integer orderQuantity = 0;
    private BigDecimal price;

    @Builder
    public BeerOrderLineDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                            UUID beerId, String upc, String beerName, String beerStyle, Integer orderQuantity, BigDecimal price) {
        super(id, version, createdDate, lastModifiedDate);
        this.beerId = beerId;
        this.upc = upc;
        this.beerName = beerName;
        this.beerStyle = beerStyle;
        this.orderQuantity = orderQuantity;
        this.price = price;
    }
}
