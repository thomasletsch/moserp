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

package org.moserp.product.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.moserp.common.domain.DependentEntity;
import org.moserp.common.domain.Price;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ProductAttributeEnumItem extends DependentEntity {

    @NonNull
    private String value;
    @NonNull
    private String label;
    private Price salesPriceDelta = Price.ZERO;
    private Price retailPriceDelta = Price.ZERO;

    @Override
    public String getDisplayName() {
        return label;
    }
}
