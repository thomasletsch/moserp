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

package org.moserp.inventory.repository;

import org.moserp.common.domain.RestUri;
import org.moserp.common.repository.EntityRepository;
import org.moserp.inventory.domain.InventoryItem;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ExposesResourceFor(InventoryItem.class)
@RequestMapping("/inventoryItems")
@RepositoryRestResource
public interface InventoryItemRepository extends EntityRepository<InventoryItem, String>, InventoryItemRepositoryCustom {

    List<InventoryItem> findByProductInstanceProductAndFacility(@Param("product") RestUri product, @Param("facility") RestUri facility);

    List<InventoryItem> findByFacility(@Param("facility") RestUri facility);

    List<InventoryItem> findByProductInstanceProduct(@Param("product") RestUri product);


}
