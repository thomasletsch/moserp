package org.moserp.product.domain;

import lombok.*;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithHistory;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Product extends EntityWithHistory {

    public static final String REL_DEFAULT_INVENTORY_UNIT = "defaultInventoryUnit";
    public static final String REL_QUANTIY_UNIT = "quantityUnit";

    @NonNull
    private String name;
    private String ean;
    private String externalId;

    @Size(max = 4096)
    private String description;
    @ValueListKey("ProductType")
    private String type;

    private Price salesPrice;
    private Price retailPrice;

    @ValueListKey("Currency")
    private String currency;

    /**
     * Minimal order quantity
     */
    private Quantity minQuantity;

    /**
     * Maximal order quantity
     */
    private Quantity maxQuantity;

    /**
     * Step size for order quantity
     */
    private Quantity quantityStep;

    @ResourceAssociation("unitOfMeasurements")
    private RestUri quantityUnit;

    @ResourceAssociation("unitOfMeasurements")
    private RestUri defaultInventoryUnit;

    @DBRef
    private ProductCatalog catalog;

    private List<ProductAttribute> attributes = new ArrayList<>();

     @Override
    public String getDisplayName() {
        return name;
    }

    public void addAttribute(ProductAttribute attribute) {
        attributes.add(attribute);
    }
}
