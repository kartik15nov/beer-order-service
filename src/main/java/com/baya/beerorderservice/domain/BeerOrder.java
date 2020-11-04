package com.baya.beerorderservice.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Setter
@Getter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class BeerOrder extends BaseEntity {

    @ManyToOne
    private Customer customer;

    private String customerRef;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "beerOrder")
    @Fetch(FetchMode.JOIN)
    private Set<BeerOrderLine> beerOrderLines;

    private OrderStatusEnum orderStatusEnum = OrderStatusEnum.NEW;

    private String OrderStatusCallbackUrl;
}
