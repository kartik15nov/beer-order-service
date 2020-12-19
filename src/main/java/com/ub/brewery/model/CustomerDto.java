package com.ub.brewery.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends BaseItem {

    private String name;

    @Builder
    public CustomerDto(UUID uuid, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate, String name) {
        super(uuid, version, createdDate, lastModifiedDate);
        this.name = name;
    }
}
