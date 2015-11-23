package org.moserp.billing.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;
import org.moserp.product.domain.ProductInstance;

import java.math.BigDecimal;

@Slf4j
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class BillItem extends DependentEntity {

    private ProductInstance productInstance;
    private Integer amount = 1;
    private Price price;
    private BigDecimal vat = new BigDecimal(0.19);  // TODO: Localize it!

    @JsonIgnore
    public Price getNetPrice() {
        return price.divide(getVat().add(BigDecimal.ONE));
    }

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
