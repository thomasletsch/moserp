package org.moserp.inventory.domain;

import lombok.*;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Quantity;
import org.moserp.product.domain.ProductInstance;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeliveryItem extends DependentEntity {

    private ProductInstance productInstance;

    private Quantity quantity;

}
