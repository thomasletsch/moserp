package org.moserp.inventory.domain;

import lombok.*;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Quantity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DeliveryItem extends DependentEntity {

    private ProductInstance productInstance;

    private Quantity quantity;

}
