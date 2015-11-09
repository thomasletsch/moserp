package org.moserp.product.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.DependentEntity;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, include=JsonTypeInfo.As.PROPERTY, property="@type")
public abstract class ProductAttribute extends DependentEntity {

    /**
     * Unique code of this attribute inside the productInstance
     */
    @ValueListKey("ProductAttributeCode")
    private String code;
    private String name;
    private String description;

    public abstract void mergeWith(ProductAttribute newAttribute);

    @Override
    public String getDisplayName() {
        return name;
    }
}
