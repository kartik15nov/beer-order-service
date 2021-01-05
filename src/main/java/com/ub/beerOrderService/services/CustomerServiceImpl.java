package com.ub.beerOrderService.services;

import com.ub.beerOrderService.domain.Customer;
import com.ub.beerOrderService.repositories.CustomerRepository;
import com.ub.beerOrderService.web.mappers.CustomerMapper;
import com.ub.brewery.model.CustomerPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerPagedList listCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return new CustomerPagedList(customerPage.stream().map(customerMapper::customerToDto).collect(Collectors.toList()),
                PageRequest.of(customerPage.getPageable().getPageNumber(), customerPage.getPageable().getPageSize()),
                customerPage.getTotalElements());
    }
}
