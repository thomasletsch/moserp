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

package org.moserp.product.repository;

import org.moserp.common.domain.RestUri;
import org.moserp.common.rest.TestEnvironment;
import org.moserp.product.domain.Product;
import org.moserp.product.domain.ProductAttributeValue;
import org.moserp.product.domain.ProductCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductUtil {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private TestEnvironment testEnvironment;

    public void setup() {
        productRepository.deleteAll();
    }

    public Product setupOneProduct() {
        productCatalogRepository.deleteAll();
        ProductCatalog productCatalog = productCatalogRepository.save(createProductCatalog());
        productRepository.deleteAll();
        return productRepository.save(createProduct(productCatalog));
    }

    public Product createProduct(ProductCatalog productCatalog) {
        Product product = createProduct();
        product.setCatalog(productCatalog);
        return product;
    }

    public ProductCatalog createProductCatalog() {
        return new ProductCatalog("Catalog", "cat12345", "1.0.0", "EUR");
    }

    public Product createProduct() {
        Product product = new Product("My Product");
        product.setEan("0123456789012");
        product.setExternalId("prod1");
        product.setId("1");
        return product;
    }

    public Product createAlternativeProduct() {
        Product product = new Product("Another Product");
        product.setEan("0123456789012");
        product.setExternalId("prod2");
        product.setId("2");
        return product;
    }

    public void addAttributes(Product product) {
        ProductAttributeValue attributeValue = new ProductAttributeValue();
        attributeValue.setCode("TEST_ATTRIBUTE_VALUE");
        attributeValue.setName("Test Attr. Value");
        attributeValue.setValue(BigDecimal.ONE);
        attributeValue.setUnit(new RestUri("unit link"));
        product.addAttribute(attributeValue);
    }

    public Product getById(String productId) {
        return productRepository.findOne(productId);
    }

    public Product getByExternalId(String externalId) {
        return productRepository.findByExternalId(externalId);
    }

    public Product getByUri(String productUri) {
        return productRepository.findOne(getProductIdFromUri(productUri));
    }

    public String getProductIdFromUri(String productURI) {
        UriTemplate uriTemplate = new UriTemplate("/products/{productId}");
        Map<String, String> variables = uriTemplate.match(productURI);
        return variables.get("productId");
    }

    public String getUri(Product product) {
        return testEnvironment.createRestUri(ProductRepository.URL + "/" + product.getId());
    }

    public Link getLink(Product product, String rel) {
        return new Link(getUri(product), rel);
    }

}
