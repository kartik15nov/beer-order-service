package com.ub.beerOrderService.bootstrap;

import com.ub.beerOrderService.domain.Customer;
import com.ub.beerOrderService.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class BeerOrderBootStrap implements CommandLineRunner {

    public static final String TASTING_ROOM = "Tasting Room";
    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.findAllByCustomerNameLike(TASTING_ROOM).size() == 0) {
            Customer savedCustomer = customerRepository.saveAndFlush(Customer.customerBuilder()
                    .customerName(TASTING_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build());

            log.debug("Tasting Room Customer Id: {}", savedCustomer.getId().toString());
        }
    }
}
