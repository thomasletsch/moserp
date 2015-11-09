package org.moserp.inventory.domain;

import lombok.Data;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;

@Data
public class ProductAttributeInstance extends DependentEntity {

    private String code;
    private String name;
    private String value;
    private Price salesPriceDelta = Price.ZERO;
    private Price retailPriceDelta = Price.ZERO;

    @Override
    public String getDisplayName() {
        return name + ": " + value;
    }
}
