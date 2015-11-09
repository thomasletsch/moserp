package org.moserp.product.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductAttributeEnumItem extends DependentEntity {

    @NonNull
    private String value;
    @NonNull
    private String label;
    private Price salesPriceDelta = Price.ZERO;
    private Price retailPriceDelta = Price.ZERO;

    @Override
    public String getDisplayName() {
        return label;
    }
}
