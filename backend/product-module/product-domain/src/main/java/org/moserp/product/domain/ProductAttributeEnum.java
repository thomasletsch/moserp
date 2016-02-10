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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductAttributeEnum extends ProductAttribute {

    private List<ProductAttributeEnumItem> items = new ArrayList<>();

    public void addItem(ProductAttributeEnumItem item) {
        items.add(item);
    }

    @Override
    public void mergeWith(ProductAttribute newAttribute) {
        if(newAttribute instanceof ProductAttributeEnum) {
            items.addAll(((ProductAttributeEnum) newAttribute).items);
        }
    }

    @Override
    public String getName() {
        if(super.getName() == null) {
            return getCode();
        }
        return super.getName();
    }

    public void restrictItemsTo(List<String> enumValues) {
        items = items.stream().filter(item -> enumValues.contains(item.getValue())).collect(Collectors.toList());
    }
}
