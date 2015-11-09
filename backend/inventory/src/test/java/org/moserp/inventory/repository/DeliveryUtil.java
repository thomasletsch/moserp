package org.moserp.inventory.repository;

import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.DeliveryItem;
import org.moserp.inventory.domain.IncomingDelivery;
import org.moserp.inventory.domain.OutgoingDelivery;
import org.moserp.inventory.domain.ProductInstance;
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
