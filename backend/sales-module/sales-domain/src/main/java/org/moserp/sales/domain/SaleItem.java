package org.moserp.sales.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;
import org.moserp.product.domain.ProductInstance;

@Slf4j
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class SaleItem extends DependentEntity {

    private ProductInstance productInstance;
    private Integer amount = 1;
    private Price price;

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
        if (productInstance == null) {
            log.warn("ProductInstance is NULL!");
            return;
        }
        final Price retailPrice = productInstance.getRetailPrice();
        price = retailPrice.multiply(amount);
    }


}
