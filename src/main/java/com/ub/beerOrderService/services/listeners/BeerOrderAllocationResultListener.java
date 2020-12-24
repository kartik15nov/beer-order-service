package com.ub.beerOrderService.services.listeners;

import com.ub.beerOrderService.config.JMSConfig;
import com.ub.beerOrderService.services.BeerOrderManager;
import com.ub.brewery.events.AllocateOrderResult;
import com.ub.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JMSConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResult result) {
        BeerOrderDto beerOrderDto = result.getBeerOrderDto();

        boolean allocationError = result.getAllocationError();
        boolean pendingInventory = result.getPendingInventory();

        if (!allocationError && !pendingInventory) {
            beerOrderManager.beerOrderAllocationPassed(beerOrderDto); //Allocate Normally
        } else if (!allocationError) {
            beerOrderManager.beerOrderAllocationPendingInventory(beerOrderDto); //Pending Inventory
        } else {
            beerOrderManager.beerOrderAllocationFailed(beerOrderDto); //Allocation Error
        }
    }
}
