package com.ub.beerOrderService.services.listeners;

import com.ub.beerOrderService.config.JMSConfig;
import com.ub.brewery.events.ValidateOrderRequest;
import com.ub.brewery.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JMSConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message<ValidateOrderRequest> message) {

        ValidateOrderRequest orderRequest = message.getPayload();

        System.out.println("######################## I RAN ########################");

        jmsTemplate.convertAndSend(JMSConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                ValidateOrderResult.builder()
                        .isValid(true)
                        .orderId(orderRequest.getBeerOrderDto().getId())
                        .build());
    }
}