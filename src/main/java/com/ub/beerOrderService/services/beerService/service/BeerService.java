package com.ub.beerOrderService.services.beerService.service;

import com.ub.beerOrderService.services.beerService.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}