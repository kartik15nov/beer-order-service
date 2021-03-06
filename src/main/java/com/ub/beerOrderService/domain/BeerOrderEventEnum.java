package com.ub.beerOrderService.domain;

public enum BeerOrderEventEnum {
    VALIDATE_ORDER, VALIDATE_PASSED, VALIDATION_FAILED,
    ALLOCATE_ORDER, ALLOCATION_SUCCESS, ALLOCATION_NO_INVENTORY, ALLOCATION_FAILED,
    BEER_ORDER_PICKED_UP,
    CANCEL_ORDER
}
