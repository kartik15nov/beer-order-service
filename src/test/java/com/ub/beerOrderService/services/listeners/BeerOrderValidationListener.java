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

        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest orderRequest = message.getPayload();

        String customerRef = orderRequest.getBeerOrderDto().getCustomerRef();

        if (customerRef != null) {
            if (customerRef.equals("fail-validation"))
                isValid = false;
            else if (customerRef.equals("dont-validate"))
                sendResponse = false;
        }

        if (sendResponse) {
            jmsTemplate.convertAndSend(JMSConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(isValid)
                            .orderId(orderRequest.getBeerOrderDto().getId())
                            .build());
        }
    }
}