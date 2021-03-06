package com.ub.beerOrderService.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class BeerOrder extends BaseEntity {

    @ManyToOne
    private Customer customer;

    private String customerRef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "beerOrder")
    @Fetch(FetchMode.JOIN)
    private Set<BeerOrderLine> beerOrderLines;

    private BeerOrderStatusEnum orderStatus = BeerOrderStatusEnum.NEW;

    private String orderStatusCallbackUrl;

    @Builder
    public BeerOrder(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate,
                     String customerRef, Customer customer,
                     Set<BeerOrderLine> beerOrderLines,
                     BeerOrderStatusEnum orderStatus,
                     String orderStatusCallbackUrl) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerRef = customerRef;
        this.customer = customer;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
    }
}
