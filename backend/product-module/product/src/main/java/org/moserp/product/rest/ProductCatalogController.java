package org.moserp.product.rest;

import org.moserp.product.domain.Product;
import org.moserp.product.domain.ProductCatalog;
import org.moserp.product.repository.ProductCatalogRepository;
import org.moserp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.support.DomainObjectMerger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productCatalogs")
public class ProductCatalogController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private DomainObjectMerger domainObjectMerger;

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, value = "/{catalogId}/products")
    public ResponseEntity<?> updateProducts(@PathVariable String catalogId, @RequestBody List<Product> products) {
        ProductCatalog productCatalog = productCatalogRepository.findOne(catalogId);
        if(productCatalog == null) {
            return ResponseEntity.notFound().build();
        }
        for (Product newProduct : products) {
            Product product = productRepository.findByExternalId(newProduct.getExternalId());
            if(product == null) {
                product = newProduct;
            } else {
                domainObjectMerger.merge(newProduct, product, DomainObjectMerger.NullHandlingPolicy.IGNORE_NULLS);
            }
            product.setCatalog(productCatalog);
            productRepository.save(product);
        }
        return ResponseEntity.ok().build();
    }


}
