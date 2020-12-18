package com.ub.beerOrderService.web.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.ub.beerOrderService.domain.BeerOrderStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDto extends BaseItem {


    private UUID customerId;
    private String customerRef;

    @JsonAlias("list of orders")
    private List<BeerOrderLineDto> beerOrderLines;
    private BeerOrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;

    @Builder
    public BeerOrderDto(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                        UUID customerId, String customerRef, List<BeerOrderLineDto> beerOrderLines, BeerOrderStatusEnum orderStatus, String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.customerRef = customerRef;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }
}
