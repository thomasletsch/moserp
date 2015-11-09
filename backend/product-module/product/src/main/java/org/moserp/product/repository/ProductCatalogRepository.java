package org.moserp.product.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.product.domain.ProductCatalog;
import org.springframework.data.repository.query.Param;

public interface ProductCatalogRepository extends EntityRepository<ProductCatalog, String> {

    public static final String URL = "productCatalogs";

    ProductCatalog findByExternalId(@Param("externalId") String externalId);
}
