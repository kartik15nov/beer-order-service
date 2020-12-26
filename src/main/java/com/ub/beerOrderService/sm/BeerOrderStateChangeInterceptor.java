package com.ub.beerOrderService.sm;

import com.ub.beerOrderService.domain.BeerOrder;
import com.ub.beerOrderService.domain.BeerOrderEventEnum;
import com.ub.beerOrderService.domain.BeerOrderStatusEnum;
import com.ub.beerOrderService.repositories.BeerOrderRepository;
import com.ub.beerOrderService.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state,
                               Message<BeerOrderEventEnum> message,
                               Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition,
                               StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {
        log.debug("Pre-State Change");

        Optional.of(message)
                .flatMap(eventMessage -> Optional.ofNullable(message.getHeaders().getOrDefault(BeerOrderManagerImpl.ORDER_ID_HEADER, " ")))
                .ifPresent(orderId -> {
                    log.debug("Saving state for the order id: {}, status: {}", orderId, state.getId());

                    Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(orderId.toString()));

                    beerOrderOptional.ifPresentOrElse(beerOrder -> {
                        beerOrder.setOrderStatus(state.getId());
                        beerOrderRepository.saveAndFlush(beerOrder);
                    }, () -> log.error("Order not found for pre-state change interceptor: {}", orderId));
                });
    }
}
