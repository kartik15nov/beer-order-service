package com.ub.brewery.events;

import com.ub.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AllocateOrderResult {
    private BeerOrderDto beerOrderDto;
    private Boolean allocationError;
    private Boolean pendingInventory;
}
