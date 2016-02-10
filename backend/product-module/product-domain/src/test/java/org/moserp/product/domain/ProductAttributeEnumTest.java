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

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ProductAttributeEnumTest {

    @Test
    public void testRestrictItemsTo() {
        ProductAttributeEnum attributeEnum = new ProductAttributeEnum();
        attributeEnum.addItem(new ProductAttributeEnumItem("value", "label"));
        attributeEnum.addItem(new ProductAttributeEnumItem("value2", "label2"));
        attributeEnum.restrictItemsTo(Arrays.asList("value"));
        assertEquals("Size", 1, attributeEnum.getItems().size());
        assertEquals("Size", "value", attributeEnum.getItems().get(0).getValue());
    }
}
