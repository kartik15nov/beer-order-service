package com.ub.beerOrderService.services.listeners;

import com.ub.beerOrderService.config.JMSConfig;
import com.ub.brewery.events.AllocateOrderRequest;
import com.ub.brewery.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JMSConfig.ALLOCATE_ORDER_QUEUE)
    public void list(Message<AllocateOrderRequest> message) {
        AllocateOrderRequest request = message.getPayload();

        boolean pendingInventory = false;
        boolean allocationError = false;

        String customerRef = request.getBeerOrderDto().getCustomerRef();

        if (customerRef != null && customerRef.equals("fail-allocation")) {
            allocationError = true;
        }
        if (customerRef != null && customerRef.equals("partial-allocation")) {
            pendingInventory = true;
        }


        boolean finalPendingInventory = pendingInventory;
        request.getBeerOrderDto()
                .getBeerOrderLines()
                .forEach(beerOrderLineDto -> {
                    if (finalPendingInventory)
                        beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
                    else
                        beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
                });

        jmsTemplate.convertAndSend(JMSConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                        .beerOrderDto(request.getBeerOrderDto())
                        .pendingInventory(pendingInventory)
                        .allocationError(allocationError)
                        .build());
    }
}