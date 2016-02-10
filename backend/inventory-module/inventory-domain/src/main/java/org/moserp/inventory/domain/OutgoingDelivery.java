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

package org.moserp.inventory.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.moserp.common.annotations.ResourceAssociation;
import org.moserp.common.domain.RestUri;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OutgoingDelivery extends Delivery {

    @ResourceAssociation("facilities")
    private RestUri fromFacility;

    public OutgoingDelivery(RestUri fromFacility, DeliveryItem... items) {
        this.fromFacility = fromFacility;
        this.getItems().addAll(Arrays.asList(items));
    }

}
