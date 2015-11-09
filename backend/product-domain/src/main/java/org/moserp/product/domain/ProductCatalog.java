package org.moserp.product.domain;

import lombok.*;
import org.moserp.common.annotations.ValueListKey;
import org.moserp.common.domain.EntityWithHistory;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductCatalog extends EntityWithHistory {

    private String name;
    private String externalId;
    private String externalVersion;
    @ValueListKey("Currency")
    private String currency;

    @Override
    public String getDisplayName() {
        return name;
    }

    /**
     * Shallow copy constructor
     *
     * @param source
     */
    public ProductCatalog(ProductCatalog source) {
        name = source.name;
        externalId = source.externalId;
        externalVersion = source.externalVersion;
        currency = source.currency;
    }
}
