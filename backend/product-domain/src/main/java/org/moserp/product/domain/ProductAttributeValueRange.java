package org.moserp.product.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.RestUri;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductAttributeValueRange extends ProductAttribute {

    private BigDecimal min = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal step = BigDecimal.ONE;
    private Boolean includeZero = Boolean.FALSE;
    private String format;
    @ResourceAssociation("unitOfMeasurements")
    private RestUri unit;
    private Price salesPriceDelta = Price.ZERO;
    private Price retailPriceDelta = Price.ZERO;

    @Override
    public void mergeWith(ProductAttribute newAttribute) {

    }
}
