package org.moserp.inventory.domain;

import lombok.*;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.RestUri;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class ProductInstance extends DependentEntity {

    @NonNull
    @ResourceAssociation("products")
    private RestUri product;

    private List<ProductAttributeInstance> attributeInstances = new ArrayList<>();

    @Override
    public String getDisplayName() {
        return product + "(" + attributeInstances + ")";
    }
}
