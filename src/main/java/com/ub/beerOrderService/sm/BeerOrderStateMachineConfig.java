package com.ub.beerOrderService.sm;

import com.ub.beerOrderService.domain.BeerOrderEventEnum;
import com.ub.beerOrderService.domain.BeerOrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@RequiredArgsConstructor
@EnableStateMachineFactory
@Component
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validateOrderAction;
    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> allocateOrderAction;

    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(BeerOrderStatusEnum.NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(BeerOrderStatusEnum.PICKED_UP)
                .end(BeerOrderStatusEnum.DELIVERED)
                .end(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
                .end(BeerOrderStatusEnum.ALLOCATED_EXCEPTION)
                .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions.withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATION_PENDING)
                .event(BeerOrderEventEnum.VALIDATE_ORDER)
                .action(validateOrderAction)
                .and().withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATED)
                .event(BeerOrderEventEnum.VALIDATE_PASSED)
                .and().withExternal()
                .source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATION_EXCEPTION)
                .event(BeerOrderEventEnum.VALIDATION_FAILED)
                .and().withExternal()
                .source(BeerOrderStatusEnum.VALIDATED).target(BeerOrderStatusEnum.ALLOCATION_PENDING)
                .event(BeerOrderEventEnum.ALLOCATE_ORDER)
                .action(allocateOrderAction);
    }
}
