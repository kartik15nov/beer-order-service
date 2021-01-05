package com.ub.beerOrderService.web.mappers;

import com.ub.beerOrderService.domain.Customer;
import com.ub.brewery.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
