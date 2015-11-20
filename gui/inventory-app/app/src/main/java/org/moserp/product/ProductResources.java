package org.moserp.product;

import org.moserp.common.rest.HateoasResources;

public class ProductResources extends HateoasResources<Product> {

    public static final String RELATIVE = "products";
    public static final String RELATIVE_SEARCH = "search";
    public static final String FIND_BY_NAME_OR_EAN = "findByNameOrEan";
    public static final String FIND_BY_NAME_OR_EAN_PARAM = "query";

    public ProductResources() {
        super();
    }


}
