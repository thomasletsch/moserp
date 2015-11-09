package org.moserp.product.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCatalogWithProducts extends ProductCatalog {

    private List<Product> products;

}
