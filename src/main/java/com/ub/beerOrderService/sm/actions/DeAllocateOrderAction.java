package com.ub.beerOrderService.sm.actions;

import com.ub.beerOrderService.config.JMSConfig;
import com.ub.beerOrderService.domain.BeerOrder;
import com.ub.beerOrderService.domain.BeerOrderEventEnum;
import com.ub.beerOrderService.domain.BeerOrderStatusEnum;
import com.ub.beerOrderService.repositories.BeerOrderRepository;
import com.ub.beerOrderService.services.BeerOrderManagerImpl;
import com.ub.beerOrderService.web.mappers.BeerOrderMapper;
import com.ub.brewery.events.DeAllocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeAllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = context.getMessageHeader(BeerOrderManagerImpl.ORDER_ID_HEADER).toString();
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JMSConfig.DEALLOCATE_ORDER_QUEUE,
                    DeAllocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent De-allocation Request for order id: {}", beerOrderId);
        }, () -> log.error("Beer Order Not Found for AllocateOrderAction!"));
    }
}
