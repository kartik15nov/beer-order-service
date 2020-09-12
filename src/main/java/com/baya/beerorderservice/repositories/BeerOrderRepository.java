package com.baya.beerorderservice.repositories;

import com.baya.beerorderservice.domain.BeerOrder;
import com.baya.beerorderservice.domain.Customer;
import com.baya.beerorderservice.domain.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatusEnum(OrderStatusEnum orderStatusEnum);

    BeerOrder findOneById(UUID uuid);
}
