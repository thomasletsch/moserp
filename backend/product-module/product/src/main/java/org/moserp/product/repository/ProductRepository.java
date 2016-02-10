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

import org.moserp.common.repository.EntityRepository;
import org.moserp.product.domain.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends EntityRepository<Product, String> {

    String URL = "products";

    Product findByExternalId(@Param("id") String externalId);

    List<Product> findByName(@Param("name") String name);

    @Query("{$or: [{ name : {$regex: '?0', $options: 'i' }}, {ean : {$regex: '?0', $options: 'i' }}]}")
    List<Product> findByNameOrEan(@Param("query") String query);

}
