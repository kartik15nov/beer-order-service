package com.ub.beerOrderService.domain;

public enum BeerOrderStatusEnum {
    NEW,
    VALIDATED, VALIDATION_EXCEPTION,
    ALLOCATED, ALLOCATED_EXCEPTION,
    PENDING_INVENTORY,
    PICKED_UP,
    DELIVERED, DELIVERY_EXCEPTION
}
