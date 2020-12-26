package com.ub.beerOrderService.services;

import com.ub.beerOrderService.domain.BeerOrder;
import com.ub.beerOrderService.domain.BeerOrderStatusEnum;
import com.ub.beerOrderService.domain.Customer;
import com.ub.beerOrderService.repositories.BeerOrderRepository;
import com.ub.beerOrderService.repositories.CustomerRepository;
import com.ub.beerOrderService.web.mappers.BeerOrderMapper;
import com.ub.brewery.model.BeerOrderDto;
import com.ub.brewery.model.BeerOrderPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final BeerOrderManager beerOrderManager;

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

            return new BeerOrderPagedList(beerOrderPage
                    .stream()
                    .map(beerOrderMapper::beerOrderToDto)
                    .collect(Collectors.toList()),
                    PageRequest.of(
                            beerOrderPage.getPageable().getPageNumber(),
                            beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        final BeerOrderDto[] beerOrderDtos = new BeerOrderDto[1];

        customerOptional.ifPresentOrElse(customer -> {
            BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrder.setId(null); //should not be set by outside client
            beerOrder.setCustomer(customer);
            beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
            beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));

            BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

            log.debug("Saved Beer Order: " + beerOrder.getId());
            beerOrderDtos[0] = beerOrderMapper.beerOrderToDto(savedBeerOrder);

        }, () -> log.error("Customer Not Found: {}", customerId));

        return beerOrderDtos[0];
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        beerOrderManager.beerOrderPickedUp(orderId);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        final BeerOrder[] beerOrder = new BeerOrder[1];

        customerOptional.ifPresentOrElse(customer -> {
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

            beerOrderOptional.ifPresentOrElse(beerOrder1 -> {
                if (beerOrder1.getCustomer().getId().equals(customerId))
                    beerOrder[0] = beerOrder1;
            }, () -> log.error("Beer Order Not Found: {}", customerId));

        }, () -> log.error("Customer Not Found: {}", customerId));

        return beerOrder[0];
    }
}
