package org.moserp.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;
import org.moserp.product.domain.ProductInstance;

@Slf4j
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends DependentEntity {

    private Integer amount;
    private Price price;
    private ProductInstance productInstance;

    @ValueListKey("Currency")
    private String currency;

    @ValueListKey("OrderItemStatus")
    private String status = "CREATED";

}
