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
import lombok.EqualsAndHashCode;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.Price;
import org.moserp.common.domain.RestUri;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductAttributeValue extends ProductAttribute {
    public static final String REL_UNIT = "unit";

    private BigDecimal value;
    private String format;
    @ResourceAssociation("unitOfMeasurements")
    private RestUri unit;
    private Price salesPriceDelta = Price.ZERO;
    private Price retailPriceDelta = Price.ZERO;

    @Override
    public void mergeWith(ProductAttribute newAttribute) {

    }
}
