package com.ub.beerOrderService.services;

import com.ub.beerOrderService.domain.BeerOrder;

public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
