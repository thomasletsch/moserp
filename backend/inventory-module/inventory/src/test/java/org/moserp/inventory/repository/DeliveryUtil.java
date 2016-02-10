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

import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.IncomingDelivery;
import org.moserp.inventory.domain.OutgoingDelivery;
import org.moserp.product.domain.ProductInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryUtil {

    @Autowired
    private IncomingDeliveryRepository incomingDeliveryRepository;

    @Autowired
    private OutgoingDeliveryRepository outgoingDeliveryRepository;

    public void setup() {
        incomingDeliveryRepository.deleteAll();
        outgoingDeliveryRepository.deleteAll();
    }

    public IncomingDelivery createIncomingDelivery(RestUri product, RestUri facility) {
        DeliveryItem item = new DeliveryItem(new ProductInstance(product), new Quantity(100));
        IncomingDelivery incomingDelivery = new IncomingDelivery(facility, item);
        return incomingDelivery;
    }

    public OutgoingDelivery createOutgoingDelivery(RestUri product, RestUri facility) {
        DeliveryItem item = new DeliveryItem(new ProductInstance(product), new Quantity(100));
        OutgoingDelivery outgoingDelivery = new OutgoingDelivery(facility, item);
        return outgoingDelivery;
    }

}
