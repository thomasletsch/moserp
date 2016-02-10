/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
