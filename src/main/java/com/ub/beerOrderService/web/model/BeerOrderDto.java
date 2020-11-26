package com.ub.beerOrderService.web.model;

import com.ub.beerOrderService.domain.OrderStatusEnum;
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
    private List<BeerOrderLineDto> beerOrderLines;
    private OrderStatusEnum orderStatus;
    private String orderStatusCallbackUrl;

    @Builder
    public BeerOrderDto(UUID uuid, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, UUID customerId, String customerRef, List<BeerOrderLineDto> beerOrderLines, OrderStatusEnum orderStatus, String orderStatusCallbackUrl) {
        super(uuid, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.customerRef = customerRef;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }
}
