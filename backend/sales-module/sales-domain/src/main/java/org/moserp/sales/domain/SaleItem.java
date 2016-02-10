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

package org.moserp.sales.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;
import org.moserp.product.domain.ProductInstance;

@Slf4j
@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class SaleItem extends DependentEntity {

    private ProductInstance productInstance;
    private Integer amount = 1;
    private Price price;

    public void setProductInstance(ProductInstance productInstance) {
        this.productInstance = productInstance;
        if (productInstance == null) {
            log.warn("ProductInstance is NULL!");
            return;
        }
        final Price retailPrice = productInstance.getRetailPrice();
        price = retailPrice.multiply(amount);
    }


}
